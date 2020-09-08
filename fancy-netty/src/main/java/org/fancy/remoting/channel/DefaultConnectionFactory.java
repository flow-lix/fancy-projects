package org.fancy.remoting.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.ConfigurableRemoting;
import org.fancy.remoting.Connection;
import org.fancy.remoting.ConnectionEventType;
import org.fancy.remoting.Url;
import org.fancy.remoting.codec.CodecFactory;
import org.fancy.remoting.config.configs.ConfigManager;
import org.fancy.remoting.exception.RemotingException;
import org.fancy.remoting.handler.ConnectionEventHandler;
import org.fancy.remoting.util.EventLoopGroupUtil;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultConnectionFactory implements ConnectionFactory {

    private final ConfigurableRemoting remoting;
    private final CodecFactory codec;
    private final ChannelHandler heartbeatHandler;
    private final ChannelHandler handler;

    protected Bootstrap bootstrap;

    private static final EventLoopGroup workerGroup = EventLoopGroupUtil.newEventLoopGroup(Runtime.getRuntime().availableProcessors() + 1,
            new DefaultThreadFactory("netty-client-worker", true));

    public DefaultConnectionFactory(ConfigurableRemoting remoting, CodecFactory codec,
                                    ChannelHandler heartbeatHandler, ChannelHandler handler) {
        Objects.requireNonNull(codec);
        Objects.requireNonNull(handler);
        this.remoting = remoting;
        this.codec = codec;
        this.heartbeatHandler = heartbeatHandler;
        this.handler = handler;
    }

    @Override
    public void init(final ConnectionEventHandler connectionEventHandler) {
        this.bootstrap = new Bootstrap();
        this.bootstrap.group(workerGroup)
                .channel(EventLoopGroupUtil.getClientSocketChannelClass())
                .option(ChannelOption.TCP_NODELAY, ConfigManager.isTcpNoDelay())
                .option(ChannelOption.SO_REUSEADDR, ConfigManager.isTcpReuseAddr());
        // init netty write buffer water water
        if (ConfigManager.isBufferPooled()) {
            this.bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        } else {
            this.bootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
        }
        this.bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                // toDo 1 ssl
                ChannelPipeline pipe = channel.pipeline();
                pipe.addLast("decoder", codec.newDecoder());
                pipe.addLast("encoder", codec.newEncoder());
                if (ConfigManager.usTcpIdleCheck()) {
                    pipe.addLast("idleStateHandler", new IdleStateHandler(ConfigManager.getClientIdleTime(),
                            ConfigManager.getClientIdleTime(), 0, TimeUnit.MILLISECONDS));
                    pipe.addLast("heartbeatHandler", heartbeatHandler);
                }
                pipe.addLast("connectionEventHandler", connectionEventHandler)
                        .addLast("handler", handler);
            }
        });
    }

    @Override
    public Connection createConnection(Url url) throws RemotingException {
        Channel channel = doCreateConnection(url.getIp(), url.getPort(), url.getConnectTimeout());
        Connection conn = new Connection(channel, url);
        if (channel.isActive()) {
            channel.pipeline().fireUserEventTriggered(ConnectionEventType.CONNECTED);
        } else {
            channel.pipeline().fireUserEventTriggered(ConnectionEventType.CONNECT_FAIL);
        }
        return conn;
    }

    private Channel doCreateConnection(String ip, int port, int connectTimeout) throws RemotingException {
        connectTimeout = Math.max(connectTimeout, 1000);
        this.bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout);
        ChannelFuture future = this.bootstrap.connect(new InetSocketAddress(ip, port));
        future.awaitUninterruptibly();
        String errMsg = null;
        if (!future.isDone()) {
            errMsg = "创建连接超时!";
        } else if (future.isCancelled()) {
            errMsg = "创建连接超时!";
        } else if (!future.isSuccess()) {
            errMsg = "创建连接失败!";
        }
        if (errMsg != null) {
            log.warn(errMsg);
            throw new RemotingException(errMsg);
        }
        return future.channel();
    }
}
