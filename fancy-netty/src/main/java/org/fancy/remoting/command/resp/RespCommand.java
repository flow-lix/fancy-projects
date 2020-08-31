package org.fancy.remoting.command.resp;

import org.fancy.remoting.ResponseStatus;
import org.fancy.remoting.command.RpcCommand;

public class RespCommand extends RpcCommand {

    private ResponseStatus responseStatus;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
