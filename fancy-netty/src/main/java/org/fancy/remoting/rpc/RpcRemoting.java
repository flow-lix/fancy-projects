package org.fancy.remoting.rpc;

import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.CommandFactory;
import org.fancy.remoting.Connection;
import org.fancy.remoting.DefaultConnectionManager;
import org.fancy.remoting.InvokeContext;
import org.fancy.remoting.RpcCommandType;
import org.fancy.remoting.Url;
import org.fancy.remoting.command.req.RequestCommand;
import org.fancy.remoting.config.switchs.ProtocolSwitch;
import org.fancy.remoting.exception.RemotingException;
import org.fancy.remoting.exception.SerializationException;
import org.fancy.remoting.protocol.RpcRequestCommand;
import org.fancy.remoting.util.AddressParserUtil;
import org.fancy.remoting.util.RemotingUtil;

@Slf4j
public abstract class RpcRemoting {

    private CommandFactory commandFactory;
    private DefaultConnectionManager connectionManager;

    public RpcRemoting(CommandFactory commandFactory, DefaultConnectionManager connectionManager) {
        this.commandFactory = commandFactory;
        this.connectionManager = connectionManager;
    }

    public void oneway(String addr, Object request, InvokeContext invokeContext) throws RemotingException{
        Url url = AddressParserUtil.parse(addr);
        this.oneway(url, request, invokeContext);
    }

    public abstract void oneway(Url url, Object request, InvokeContext invokeContext) throws RemotingException;

     public void oneway(final Connection conn, final Object request, final InvokeContext invokeContext)
             throws RemotingException {
         RequestCommand cmd = toRemotingCommand(request, conn, invokeContext, -1);
         cmd.setRequestType(RpcCommandType.REQ_ONEWAY);
         preProcessInvokeContext(invokeContext, cmd, conn);
         oneway(conn, cmd);
     }
    /**
     * 预处理调用上下文
     */
    protected abstract void preProcessInvokeContext(InvokeContext invokeContext, RequestCommand cmd, Connection conn);

    protected RequestCommand toRemotingCommand(Object req, Connection conn, InvokeContext invokeContext, int timeoutMills)
            throws SerializationException {
        RpcRequestCommand command = this.commandFactory.createRequestCommand(req);
        if (invokeContext != null) {
            //toDo 9-3 invokeContext check
        } else {
            command.setProtocolSwitch(ProtocolSwitch.create(new int[] {ProtocolSwitch.CRC_SWITCH}));
        }
        command.setTimeout(timeoutMills);
        command.setRequestClass(req.getClass().getName());
        command.setInvokeContext(invokeContext);
        command.serialize();
        log.debug("发送请求，请求ID: {}", command.getId());
        return command;
    }

    public DefaultConnectionManager getConnectionManager() {
        return connectionManager;
    }

    /**
     * oneway
     */
    protected void oneway(Connection conn, RequestCommand request) {
        try {
            conn.getChannel().writeAndFlush(request)
                    .addListener((ChannelFutureListener) channelFuture -> {
                        if (!channelFuture.isSuccess()) {
                            log.error("请求发送失败, 地址: {}", RemotingUtil.parseRemoteAddress(channelFuture.channel()));
                        }
                    });
        } catch (Exception e) {
            log.error("发送请求时异常, 地址: {}", RemotingUtil.parseRemoteAddress(conn.getChannel()), e);
        }
    }

    public Object invokeSync(String address, Object request, Object o, int timeoutMillis) throws RemotingException {
        return null;
    }
}
