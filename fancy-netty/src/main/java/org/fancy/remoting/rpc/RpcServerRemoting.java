/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.rpc;

import org.fancy.remoting.CommandFactory;
import org.fancy.remoting.Connection;
import org.fancy.remoting.DefaultConnectionManager;
import org.fancy.remoting.InvokeContext;
import org.fancy.remoting.Url;
import org.fancy.remoting.command.req.RequestCommand;
import org.fancy.remoting.exception.RemotingException;

public class RpcServerRemoting extends RpcRemoting {

    public RpcServerRemoting(CommandFactory commandFactory, DefaultConnectionManager connectionManager) {
        super(commandFactory, connectionManager);
    }

    @Override
    public void oneway(Url url, Object request, InvokeContext invokeContext) throws RemotingException {
        Connection conn = getConnectionManager().get(url.getUniqueKey());
        if (null == conn) {
            throw new RemotingException("客户端" + url.getOriginUrl() + "未建立连接");
        }
        getConnectionManager().check(conn);
        this.oneway(conn, request, invokeContext);
    }

    @Override
    protected void preProcessInvokeContext(InvokeContext invokeContext, RequestCommand cmd, Connection conn) {

    }

    @Override
    protected Object invokeSync(Url url, Object request, InvokeContext invokeContext, int timeoutMillis) throws RemotingException {
        return null;
    }
}
