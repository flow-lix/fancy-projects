package org.fancy.netty.util;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ThreadFactory;

public class EventLoopGroupUtil {

    private static final boolean useEpoll = true;

    public static EventLoopGroup newEventLoopGroup(int threadNum, ThreadFactory tFactory) {
        return useEpoll ? new EpollEventLoopGroup(threadNum, tFactory) :
                new NioEventLoopGroup(threadNum, tFactory);
    }

    public static Class<? extends ServerSocketChannel> getServerSocketChannelClass() {
        return useEpoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }
}
