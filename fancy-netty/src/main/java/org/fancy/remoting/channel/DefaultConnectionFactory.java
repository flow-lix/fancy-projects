package org.fancy.remoting.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.ConfigurableRemoting;
import org.fancy.remoting.Connection;
import org.fancy.remoting.ConnectionEventType;
import org.fancy.remoting.Url;
import org.fancy.remoting.codec.CodecFactory;
import org.fancy.remoting.exception.RemotingException;

import java.net.InetSocketAddress;
import java.util.Objects;

@Slf4j
public class DefaultConnectionFactory implements ConnectionFactory {

    private final ConfigurableRemoting remoting;
    private final CodecFactory codec;
    private final ChannelHandler heartbeatHandler;
    private final ChannelHandler handler;

    protected Bootstrap bootstrap;

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
        ChannelFuture future = this.bootstrap.bind(new InetSocketAddress(ip, port));
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
