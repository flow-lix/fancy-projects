package org.fancy.remoting.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.fancy.remoting.protocol.Protocol;
import org.fancy.remoting.protocol.ProtocolCode;
import org.fancy.remoting.protocol.ProtocolManager;

import java.util.List;

public class ProtocolCodeBasedDecoder extends ByteToMessageDecoder {

    private ProtocolCode protocolCode;

    public ProtocolCodeBasedDecoder(ProtocolCode protocolCode) {
        super();
        this.protocolCode = protocolCode;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Protocol protocol = ProtocolManager.getProtocol(protocolCode);
        if (protocol != null) {
            in.resetReaderIndex();
            protocol.getDecoder().decode(ctx, in, out);
        } else {
            throw new NullPointerException("ProtocolCodeBasedDecoder decode protocol is null!");
        }
    }

}
