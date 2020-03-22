package org.fancy.netty.command.req;

import org.fancy.netty.protocol.RpcCommandCode;

public class HeartbeatCommand extends RequestCommand {
    private static final long serialVersionUID = 7572710113672285569L;

    public HeartbeatCommand() {
        super(RpcCommandCode.HEARTBEAT);
    }
}
