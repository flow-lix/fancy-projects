package org.fancy.netty.command.resp;

import org.fancy.netty.ResponseStatus;
import org.fancy.netty.command.RpcCommand;

public class RespCommand extends RpcCommand {

    private ResponseStatus responseStatus;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
