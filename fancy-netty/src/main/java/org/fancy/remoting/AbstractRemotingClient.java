package org.fancy.remoting;

import org.fancy.remoting.exception.RemotingException;

public abstract class AbstractRemotingClient extends AbstractNettyConfigRemoting {

    public abstract void oneway(final String addr, final Object req) throws RemotingException;

    public abstract Object invokeSync(final String address, final Object request, final int timeoutMillis)
            throws RemotingException, InterruptedException;
}
