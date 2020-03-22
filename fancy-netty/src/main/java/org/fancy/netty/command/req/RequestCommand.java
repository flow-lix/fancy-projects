package org.fancy.netty.command.req;

import org.fancy.netty.RpcCommandType;
import org.fancy.netty.command.CommandCode;
import org.fancy.netty.command.RpcCommand;

public class RequestCommand extends RpcCommand {

    private int timeout;

    public RequestCommand(CommandCode commandCode) {
        super(RpcCommandType.REQUEST, commandCode);
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
