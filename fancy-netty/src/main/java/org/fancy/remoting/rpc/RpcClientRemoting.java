package org.fancy.remoting.rpc;

import org.fancy.remoting.CommandFactory;
import org.fancy.remoting.Connection;
import org.fancy.remoting.DefaultConnectionManager;
import org.fancy.remoting.InvokeContext;
import org.fancy.remoting.RpcCommandType;
import org.fancy.remoting.Url;
import org.fancy.remoting.command.req.RequestCommand;
import org.fancy.remoting.common.RequestBody;
import org.fancy.remoting.exception.RemotingException;
import org.fancy.remoting.exception.SerializationException;
import org.fancy.remoting.util.RemotingUtil;

public class RpcClientRemoting extends RpcRemoting{

    public RpcClientRemoting(CommandFactory commandFactory, DefaultConnectionManager connectionManager) {
        super(commandFactory, connectionManager);
    }

    @Override
    public void oneway(Url url, Object request, InvokeContext invokeContext) throws RemotingException {
        final Connection conn = getConnectionAndInitInvokeContext(url, invokeContext);
        getConnectionManager().check(conn);
        this.oneway(conn, request, invokeContext);
    }

    private void oneway(Connection conn, RequestBody req, InvokeContext invokeContext) throws SerializationException {
        RequestCommand requestCommand = toRemotingCommand(req, conn, invokeContext, -1);
        requestCommand.setRequestType(RpcCommandType.REQ_ONEWAY);
        preProcessInvokeContext(invokeContext, requestCommand, conn);
        super.oneway(conn, requestCommand);
    }

    @Override
    protected void preProcessInvokeContext(InvokeContext invokeContext, RequestCommand cmd, Connection conn) {
        if (invokeContext != null) {
            invokeContext.putIfAbsent(InvokeContext.CLIENT_LOCAL_IP, RemotingUtil.parseLocalIP(conn.getChannel()));
            invokeContext.putIfAbsent(InvokeContext.CLIENT_LOCAL_PORT, RemotingUtil.parseLocalPort(conn.getChannel()));
            invokeContext.putIfAbsent(InvokeContext.CLIENT_REMOTE_IP, RemotingUtil.parseRemoteIP(conn.getChannel()));
            invokeContext.putIfAbsent(InvokeContext.CLIENT_REMOTE_PORT, RemotingUtil.parseRemotePort(conn.getChannel()));
            invokeContext.putIfAbsent(InvokeContext.INVOKE_REQUEST_ID, cmd.getId());
        }
    }

    /**
     * get连接并且初始化InvokeContext
     * @param url target url
     * @param invokeContext
     * @return
     */
    private Connection getConnectionAndInitInvokeContext(Url url, InvokeContext invokeContext) throws RemotingException {
        long start = System.currentTimeMillis();
        Connection conn;
        try {
            conn = getConnectionManager().getAndCreateIfAbsent(url);
        } finally {
            if (invokeContext != null) {
                invokeContext.putIfAbsent(InvokeContext.CLIENT_CONN_CREATE_TIME, System.currentTimeMillis() - start);
            }
        }
        return conn;
    }
}
