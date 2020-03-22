package org.fancy.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import org.fancy.netty.command.RemotingCommand;
import org.fancy.netty.protocol.Protocol;
import org.fancy.netty.protocol.ProtocolCode;
import org.fancy.netty.protocol.ProtocolManager;

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
