package org.fancy.remoting.command.resp;

import org.fancy.remoting.InvokeContext;
import org.fancy.remoting.ResponseStatus;
import org.fancy.remoting.command.RpcCommand;

import java.net.InetSocketAddress;

public class ResponseCommand extends RpcCommand {
    private static final long serialVersionUID = 1918551247051128796L;

    private ResponseStatus responseStatus;
    private long           responseTimeMills;
    private InetSocketAddress responseHost;
    private Throwable cause;

    @Override
    public InvokeContext getInvokeContext() {
        return null;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public long getResponseTimeMills() {
        return responseTimeMills;
    }

    public void setResponseTimeMills(long responseTimeMills) {
        this.responseTimeMills = responseTimeMills;
    }

    public InetSocketAddress getResponseHost() {
        return responseHost;
    }

    public void setResponseHost(InetSocketAddress responseHost) {
        this.responseHost = responseHost;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
