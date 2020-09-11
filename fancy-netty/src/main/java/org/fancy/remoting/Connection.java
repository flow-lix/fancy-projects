package org.fancy.remoting;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.fancy.remoting.protocol.ProtocolCode;
import org.fancy.remoting.util.RemotingUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Connection {

    private Channel channel;
    private final Url url;

    private final ConcurrentMap<Integer, InvokeFuture> INVOKE_FUTURE_MAP = new ConcurrentHashMap<>();

    private AtomicInteger referenceCount = new AtomicInteger(0);

    public Connection(Channel channel, Url url) {
        this.channel = channel;
        this.url = url;
    }

    public static final AttributeKey<ProtocolCode> PROTOCOL = AttributeKey.valueOf("protocol");

    public Channel getChannel() {
        return channel;
    }

    public Url getUrl() {
        return url;
    }

    public boolean isFine() {
        return this.channel != null && this.channel.isActive();
    }

    public void increaseRef() {
        referenceCount.incrementAndGet();
    }

    public Object getRemoteIp() {
        return RemotingUtil.parseRemoteIP(channel);
    }

    public Object getRemotePort() {
        return RemotingUtil.parseRemotePort(channel);
    }

    public void close() {

    }

    public void addInvokeFuture(InvokeFuture future) {
        INVOKE_FUTURE_MAP.putIfAbsent(future.invokeId(), future);
    }

    public InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress) channel.remoteAddress();
    }

    public void removeInvokeFuture(int requestId) {
        INVOKE_FUTURE_MAP.remove(requestId);
    }
}
