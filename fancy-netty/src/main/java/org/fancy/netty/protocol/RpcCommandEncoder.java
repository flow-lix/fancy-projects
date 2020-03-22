package org.fancy.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.fancy.netty.command.CommandEncoder;
import org.fancy.netty.command.RemotingCommand;
import org.fancy.netty.command.req.RequestCommand;
import org.fancy.netty.command.RpcCommand;
import org.fancy.netty.command.resp.RespCommand;

/**
 * Rpc encoder
 */
public class RpcCommandEncoder implements CommandEncoder {

//    private static final Logger LOGGER = LoggerFac
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
                } else if (cmd instanceof RespCommand) {
                    out.writeShort(((RespCommand) cmd).getResponseStatus().getCode());
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
                System.err.println(String.format("Message of type [%s] is not subclass of RpcCommand!", msg.getClass()));
            }
        } catch (Exception e) {
            System.err.println("Exception caughted!");
            throw e;
        }
    }
}
