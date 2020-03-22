package org.fancy.netty.command;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;

public interface CommandEncoder {

    void encode(ChannelHandlerContext ctx, RemotingCommand msg, ByteBuf out);
}
