package org.fancy.remoting.command;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public interface CommandDecoder {

    void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out);
}
