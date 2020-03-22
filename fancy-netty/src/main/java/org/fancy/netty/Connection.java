package org.fancy.netty;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.fancy.netty.protocol.ProtocolCode;

public class Connection {

    private Channel channel;

    public static final AttributeKey<ProtocolCode> PROTOCOL = AttributeKey.valueOf("protocol");

}
