package org.fancy.netty.protocol;

import org.fancy.netty.command.CommandCode;

public enum RpcCommandCode implements CommandCode {

    /**
     * 心跳
     */
    HEARTBEAT(CommandCode.HEARTBEAT),

    RPC_REQUEST((byte)1),

    RPC_RESPONSE((byte)2),

    ;

    private final short code;

    RpcCommandCode(short code) {
        this.code = code;
    }

    @Override
    public short value() {
        return code;
    }
}
