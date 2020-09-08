package org.fancy.remoting.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.fancy.remoting.protocol.Protocol;
import org.fancy.remoting.protocol.ProtocolManager;

import java.util.List;

public class ProtocolCodeBasedDecoder extends ByteToMessageDecoder {

    public ProtocolCodeBasedDecoder() {
        super();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Protocol protocol = ProtocolManager.getProtocol();
        if (protocol != null) {
            in.resetReaderIndex();
            protocol.getDecoder().decode(ctx, in, out);
        } else {
            throw new NullPointerException("ProtocolCodeBasedDecoder decode protocol is null!");
        }
    }

}
