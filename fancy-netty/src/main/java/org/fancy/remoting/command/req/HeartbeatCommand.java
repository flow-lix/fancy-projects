package org.fancy.remoting.command.req;

import org.fancy.remoting.protocol.RpcCommandCode;

public class HeartbeatCommand extends RequestCommand {
    private static final long serialVersionUID = 7572710113672285569L;

    public HeartbeatCommand() {
        super(RpcCommandCode.HEARTBEAT);
    }
}
