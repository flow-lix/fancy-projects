package org.fancy.remoting;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.fancy.remoting.protocol.ProtocolCode;

public class Connection {

    private Channel channel;
    private final Url url;

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
}
