package org.fancy.remoting.rpc;

import com.sun.jndi.toolkit.url.Uri;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.AbstractNettyConfigRemoting;
import org.fancy.remoting.AbstractNettyRemoting;
import org.fancy.remoting.AbstractRemotingServer;
import org.fancy.remoting.Connection;
import org.fancy.remoting.ConnectionEventListener;
import org.fancy.remoting.ConnectionEventType;
import org.fancy.remoting.DefaultConnectionManager;
import org.fancy.remoting.Url;
import org.fancy.remoting.channel.ConnectionSelectStrategy;
import org.fancy.remoting.channel.RandomSelectStrategy;
import org.fancy.remoting.codec.CodecFactory;
import org.fancy.remoting.codec.RpcCodecFactory;
import org.fancy.remoting.config.configs.ConfigManager;
import org.fancy.remoting.config.switches.GlobalSwitch;
import org.fancy.remoting.handler.ConnectionEventHandler;
import org.fancy.remoting.handler.RpcHandler;
import org.fancy.remoting.handler.ServerBizHandler;
import org.fancy.remoting.handler.ServerIdleHandler;
import org.fancy.remoting.protocol.UserProcessor;
import org.fancy.remoting.util.EventLoopGroupUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Netty 远程服务端
 */
@Slf4j
public class NettyRemotingServer extends AbstractRemotingServer {

    private ServerBootstrap bootstrap;

    private final EventLoopGroup bossEventLoopGroup = EventLoopGroupUtil.newEventLoopGroup(1,
            new DefaultThreadFactory("Netty-Remoting-Server-Boss", false));

    private final EventLoopGroup workerEventLoopGroup = EventLoopGroupUtil.newEventLoopGroup(
            Runtime.getRuntime().availableProcessors() * 2,
            new DefaultThreadFactory("Netty-Remoting-Server-Worker", true));

    private CodecFactory codec = new RpcCodecFactory();

    private ChannelFuture channelFuture;

    private DefaultConnectionManager connectionManager;

    private ConnectionEventListener connEventListener = new ConnectionEventListener();
    private ConnectionEventHandler connEventHandler;

    private final ConcurrentMap<String, UserProcessor<?>> userProcessors = new ConcurrentHashMap<>(4);

    private RpcRemoting rpcRemoting;

    public NettyRemotingServer(String ip, int port) {
        super(ip, port);
    }

    @Override
    protected void doInit() {
        // toDo 1 地址解析器

        // 连接管理器
        if (this.getGlobalSwitch().isOn(GlobalSwitch.SERVER_CONNECTION_MANAGE_SWITCH)) {
            ConnectionSelectStrategy selectStrategy = new RandomSelectStrategy();
            this.connectionManager = new DefaultConnectionManager(selectStrategy);
            this.connectionManager.startup();

            this.connEventHandler = new RpcConnectionEventHandler();
            this.connEventHandler.setConnectionManger(connectionManager);
            this.connEventHandler.setConnectionEventListener(connEventListener);
        }

        // 初始化Remoting
        this.rpcRemoting = new RpcServerRemoting(new RpcCommandFactory(), this.connectionManager);

        this.bootstrap = new ServerBootstrap()
                .group(bossEventLoopGroup, workerEventLoopGroup)
                .channel(EventLoopGroupUtil.getServerSocketChannelClass())
                .option(ChannelOption.SO_BACKLOG, ConfigManager.getTcpBacklog())
                .option(ChannelOption.SO_REUSEADDR, ConfigManager.isTcpReuseAddr())
                .childOption(ChannelOption.TCP_NODELAY, ConfigManager.isTcpNoDelay())
                .childOption(ChannelOption.SO_KEEPALIVE, ConfigManager.isTcpKeepAlive());
 // toDo 2 TCP so snd  buf

        initWriteBufferWaterMask();

        if (ConfigManager.isBufferPooled()) {
            this.bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        } else {
            this.bootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
        }
// toDo 3 trigger mode


        final boolean useTcpIdleCheck = ConfigManager.usTcpIdleCheck();
        final int tcpIdleTime = ConfigManager.getTcpIdleTime();
        final ChannelHandler serverIdleHandler = new ServerIdleHandler();
        final RpcHandler rpcHandler = new RpcHandler(userProcessors);
        this.bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                // TODO 4 SSL

                pipeline.addLast("decoder", codec.newDecoder())
                        .addLast("encoder",codec.newEncoder());
                if (useTcpIdleCheck) {
                    pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0,
                            tcpIdleTime, TimeUnit.MILLISECONDS))
                            .addLast("serverIdleHandler", serverIdleHandler);
                }
                pipeline.addLast("connectionEventHandler", connEventHandler);
                pipeline.addLast("serverHandler", rpcHandler);

                Url url = new Url();
                if (getGlobalSwitch().isOn(GlobalSwitch.CONN_RECONNECT_SWITCH)) {
                    connectionManager.add(new Connection(channel, url), url.getUniqueKey());
                } else {
                    new Connection(channel, url);
                }
                channel.pipeline().fireUserEventTriggered(ConnectionEventType.CONNECTED);
            }
        });
    }

    private void initWriteBufferWaterMask() {
        // toDo
    }

    @Override
    protected boolean doStart() throws InterruptedException {
        for (UserProcessor<?> userProcessor : userProcessors.values()) {
            if (!userProcessor.isStarted()) {
                userProcessor.startup();
            }
        }
        this.channelFuture = this.bootstrap.bind(new InetSocketAddress(getIp(), getPort())).sync();
        // 随机端口
        return this.channelFuture.isSuccess();
    }

    @Override
    protected boolean doStop() {
        if (this.channelFuture != null) {
            this.channelFuture.channel().close();
        }
        if (getGlobalSwitch().isOn(GlobalSwitch.SERVER_SYNC_STOP)) {
            this.bossEventLoopGroup.shutdownGracefully().awaitUninterruptibly();
        } else {
            this.bossEventLoopGroup.shutdownGracefully();
        }
        if (this.getGlobalSwitch().isOn(GlobalSwitch.SERVER_CONNECTION_MANAGE_SWITCH)
                && this.connectionManager != null) {
            this.connectionManager.shutdown();
            log.warn("Close all connection from server side!");
        }
        for (UserProcessor<?> processor : userProcessors.values()) {
            if (processor.isStarted()) {
                processor.shutdown();
            }
        }
        log.warn("Rpc Server stopped!");
        return true;
    }

}
