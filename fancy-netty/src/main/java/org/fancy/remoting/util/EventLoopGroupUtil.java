package org.fancy.remoting.util;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ThreadFactory;

public class EventLoopGroupUtil {

    private static final boolean useEpoll = Epoll.isAvailable();

    public static EventLoopGroup newEventLoopGroup(int threadNum, ThreadFactory tFactory) {
        return useEpoll ? new EpollEventLoopGroup(threadNum, tFactory) :
                new NioEventLoopGroup(threadNum, tFactory);
    }

    public static Class<? extends ServerSocketChannel> getServerSocketChannelClass() {
        return useEpoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }

    public static Class<? extends SocketChannel> getClientSocketChannelClass() {
        return useEpoll ? EpollSocketChannel.class : NioSocketChannel.class;
    }
}
