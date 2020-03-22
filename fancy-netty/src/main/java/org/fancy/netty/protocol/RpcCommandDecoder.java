package org.fancy.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.fancy.netty.RpcCommandType;
import org.fancy.netty.command.CommandCode;
import org.fancy.netty.command.CommandDecoder;
import org.fancy.netty.command.req.HeartbeatCommand;
import org.fancy.netty.command.req.RequestCommand;
import org.fancy.netty.config.switchs.ProtocolSwitch;

import java.util.List;

public class RpcCommandDecoder implements CommandDecoder {

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        byte type = in.readByte();
        if (type == RpcCommandType.REQUEST) {
            short cmdCode = in.readShort();
            int requestId= in.readInt();
            byte serializer = in.readByte();
            byte pSwitcher = in.readByte();
            int timeout = in.readInt();
            short headerLen = in.readShort();
            int contentLen = in.readInt();
            byte[] header = null;
            if (headerLen > 0) {
                header = new byte[headerLen];
                in.readBytes(header);
            }
            byte[] content = null;
            if (contentLen > 0) {
                content = new byte[contentLen];
                in.readBytes(contentLen);
            }

            RequestCommand command;
            if (cmdCode == CommandCode.HEARTBEAT) {
                command = new HeartbeatCommand();
            } else {
                command = createRequestCommand();
            }
            command.setRequestType(type);
            command.setId(requestId);
            command.setSerializer(serializer);
            command.setpSwitch(ProtocolSwitch.create(pSwitcher));
            command.setTimeout(timeout);
            command.setHeader(header);
            command.setContent(content);
            out.add(command);
        } else if (type == RpcCommandType.RESPOSNE) {

        }
    }

    private RequestCommand createRequestCommand() {
        RequestCommand command = new RequestCommand(RpcCommandCode.RPC_REQUEST);
        return command;
    }
}
