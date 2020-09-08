package org.fancy.remoting.rpc;

import org.fancy.remoting.CommandFactory;
import org.fancy.remoting.protocol.RpcRequestCommand;

public class RpcCommandFactory implements CommandFactory {

    @Override
    public RpcRequestCommand createRequestCommand(Object req) {
        return new RpcRequestCommand(req);
    }
}
