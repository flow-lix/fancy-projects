package org.fancy.remoting.command.req;

import org.fancy.remoting.RpcCommandType;
import org.fancy.remoting.command.CommandCode;
import org.fancy.remoting.command.RpcCommand;

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
