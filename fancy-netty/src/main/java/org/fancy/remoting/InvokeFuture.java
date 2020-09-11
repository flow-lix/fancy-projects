package org.fancy.remoting;

import org.fancy.remoting.command.RemotingCommand;
import org.fancy.remoting.command.resp.ResponseCommand;

public interface InvokeFuture {

    /**
     * 调用唯一id
     * @return
     */
    int invokeId();

    void putResponse(RemotingCommand sendFailedResponse);

    ResponseCommand waitResponse(int timeoutMillis) throws InterruptedException;
}
