package org.fancy.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.fancy.netty.codec.CodecFactory;
import org.fancy.netty.codec.RpcCodecFactory;
import org.fancy.netty.config.ConfigManager;
import org.fancy.netty.handler.ServerBizHandler;
import org.fancy.netty.handler.ServerIdleHandler;
import org.fancy.netty.util.EventLoopGroupUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Netty 远程服务端
 */
public class NettyRemotingServer extends AbstractNettyRemoting {

    private ServerBootstrap bootstrap;

    private final EventLoopGroup bossEventLoopGroup = EventLoopGroupUtil.newEventLoopGroup(1,
            new DefaultThreadFactory("Netty-Remoting-Server-Boss", false));

    private final EventLoopGroup workerEventLoopGroup = EventLoopGroupUtil.newEventLoopGroup(
            Runtime.getRuntime().availableProcessors() * 2,
            new DefaultThreadFactory("Netty-Remoting-Server-Worker", true));

    private CodecFactory codec = new RpcCodecFactory();

    private ChannelFuture channelFuture;

    private final String ip;
    private final int port;

    public NettyRemotingServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    void doInit() {
        this.bootstrap = new ServerBootstrap()
                .group(bossEventLoopGroup, workerEventLoopGroup)
                .channel(EventLoopGroupUtil.getServerSocketChannelClass())
                .option(ChannelOption.SO_BACKLOG, ConfigManager.getTcpBacklog())
                .option(ChannelOption.SO_REUSEADDR, ConfigManager.isTcpReuseAddr())
                .childOption(ChannelOption.TCP_NODELAY, ConfigManager.isTcpNoDelay())
                .childOption(ChannelOption.SO_KEEPALIVE, ConfigManager.isTcpKeepAlive());

        initWriteBufferWaterMask();
        if (ConfigManager.isBufferPooled()) {
            this.bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        } else {
            this.bootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
        }

        final boolean useTcpIdleCheck = ConfigManager.usTcpIdleCheck();
        final int tcpIdleTime = ConfigManager.getTcpIdleTime();
        final ChannelHandler serverIdleHandler = new ServerIdleHandler();
        final ServerBizHandler bizHandler = new ServerBizHandler();
        this.bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast("decoder", codec.newDecoder())
                        .addLast("encoder",codec.newEncoder());
                if (useTcpIdleCheck) {
                    pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0,
                            tcpIdleTime, TimeUnit.MILLISECONDS))
                            .addLast("serverIdleHandler", serverIdleHandler);
                }
                pipeline.addLast("serverHandler", bizHandler);
            }
        });
    }

    private void initWriteBufferWaterMask() {
        // toDo
    }

    @Override
    boolean doStart() throws InterruptedException {
        this.channelFuture = this.bootstrap.bind(new InetSocketAddress(ip, port)).sync();
        return this.channelFuture.isSuccess();
    }

    public String ip() {
        return ip;
    }

    public int port() {
        return port;
    }
}
