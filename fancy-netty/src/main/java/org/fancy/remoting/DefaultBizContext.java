package org.fancy.remoting;

public class DefaultBizContext implements BizContext {

    @Override
    public boolean isRequestTimeout() {
        return false;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public InvokeContext getInvokeContext() {
        return null;
    }
}
