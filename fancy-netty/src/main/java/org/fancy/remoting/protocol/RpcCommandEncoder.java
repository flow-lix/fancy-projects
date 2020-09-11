package org.fancy.remoting.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.command.CommandEncoder;
import org.fancy.remoting.command.RemotingCommand;
import org.fancy.remoting.command.req.RequestCommand;
import org.fancy.remoting.command.RpcCommand;
import org.fancy.remoting.command.resp.ResponseCommand;

/**
 * Rpc encoder
 */
@Slf4j
public class RpcCommandEncoder implements CommandEncoder {

    @Override
    public void encode(ChannelHandlerContext ctx, RemotingCommand msg, ByteBuf out) {
        try {
            if (msg instanceof RpcCommand) {
                RpcCommand cmd = (RpcCommand) msg;
                out.writeByte(cmd.getRequestType());
                out.writeShort(cmd.getCommandCode().value());
                out.writeInt(cmd.getId());
                // codec
                out.writeByte(cmd.getSerializer());
                // runtime switch
                out.writeByte(cmd.getpSwitch().toBytes());
                if (cmd instanceof RequestCommand) {
                    out.writeInt(((RequestCommand) cmd).getTimeout());
                } else if (cmd instanceof ResponseCommand) {
                    out.writeShort(((ResponseCommand) cmd).getResponseStatus().getCode());
                }
                out.writeShort(cmd.getHeaderLength());
                out.writeInt(cmd.getContentLength());
                if (cmd.getHeaderLength() > 0) {
                    out.writeBytes(cmd.getHeader());
                }
                if (cmd.getContentLength() > 0) {
                    out.writeBytes(cmd.getContent());
                }
            } else {
                log.error("Message of type {} is not subclass of RpcCommand!", msg.getClass());
            }
        } catch (Exception e) {
            log.error("Exception caughted!", e);
            throw e;
        }
    }
}
