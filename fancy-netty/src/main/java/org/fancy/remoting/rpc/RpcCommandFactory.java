package org.fancy.remoting.rpc;

import org.fancy.remoting.CommandFactory;
import org.fancy.remoting.ResponseStatus;
import org.fancy.remoting.command.resp.ResponseCommand;
import org.fancy.remoting.protocol.RpcRequestCommand;

import java.net.InetSocketAddress;

public class RpcCommandFactory implements CommandFactory {

    @Override
    public RpcRequestCommand createRequestCommand(Object req) {
        return new RpcRequestCommand(req);
    }

    @Override
    public ResponseCommand createSendFailedResponse(final InetSocketAddress address, Throwable cause) {
        ResponseCommand command = new ResponseCommand();
        command.setResponseStatus(ResponseStatus.CLIENT_SEND_FAIL);
        command.setResponseTimeMills(System.currentTimeMillis());
        command.setResponseHost(address);
        command.setCause(cause);
        return command;
    }

    @Override
    public ResponseCommand createTimeoutResponse(InetSocketAddress remoteAddress) {
        ResponseCommand responseCommand = new ResponseCommand();
        responseCommand.setResponseStatus(ResponseStatus.TIMEOUT);
        responseCommand.setResponseTimeMills(System.currentTimeMillis());
        responseCommand.setResponseHost(remoteAddress);
        return responseCommand;
    }
}
