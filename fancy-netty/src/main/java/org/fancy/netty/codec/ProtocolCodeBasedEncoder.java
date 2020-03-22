package org.fancy.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.Attribute;
import org.fancy.netty.Connection;
import org.fancy.netty.command.RemotingCommand;
import org.fancy.netty.protocol.Protocol;
import org.fancy.netty.protocol.ProtocolCode;
import org.fancy.netty.protocol.ProtocolManager;

/**
 * 基础协议编码
 */
@ChannelHandler.Sharable
public class ProtocolCodeBasedEncoder extends MessageToByteEncoder<RemotingCommand> {

    private ProtocolCode protocolCode;

    public ProtocolCodeBasedEncoder(ProtocolCode protocolCode) {
        super();
        this.protocolCode = protocolCode;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, RemotingCommand msg, ByteBuf out) throws Exception {
        Attribute<ProtocolCode> attr = ctx.channel().attr(Connection.PROTOCOL);

        ProtocolCode protocolCode;
        if (null == attr || null == attr.get()) {
            protocolCode = this.protocolCode;
        } else {
            protocolCode = attr.get();
        }

        Protocol protocol = ProtocolManager.getProtocol(protocolCode);
        protocol.getEncoder().encode(ctx, msg, out);
    }
}
