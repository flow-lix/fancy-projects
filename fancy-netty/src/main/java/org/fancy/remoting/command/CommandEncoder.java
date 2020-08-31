package org.fancy.remoting.command;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface CommandEncoder {

    void encode(ChannelHandlerContext ctx, RemotingCommand msg, ByteBuf out);
}
