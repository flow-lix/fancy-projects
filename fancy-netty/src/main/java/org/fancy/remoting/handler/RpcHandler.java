package org.fancy.remoting.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.fancy.remoting.InvokeContext;
import org.fancy.remoting.RemotingContext;
import org.fancy.remoting.protocol.Protocol;
import org.fancy.remoting.protocol.ProtocolManager;
import org.fancy.remoting.protocol.UserProcessor;

import java.util.concurrent.ConcurrentMap;

/**
 * 消息转发到对应的协议
 */
@ChannelHandler.Sharable
public class RpcHandler extends ChannelInboundHandlerAdapter {

    private boolean serverSide;
    private ConcurrentMap<String, UserProcessor<?>> userProcessors;

    public RpcHandler(ConcurrentMap<String, UserProcessor<?>> userProcessors) {
        serverSide = false;
        this.userProcessors = userProcessors;
    }

    public RpcHandler(boolean serverSide, ConcurrentMap<String, UserProcessor<?>> userProcessors) {
        this.serverSide = serverSide;
        this.userProcessors = userProcessors;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Protocol protocol = ProtocolManager.getProtocol();
        protocol.getCommandHandler().handleCommand(
                new RemotingContext(ctx, serverSide, userProcessors, new InvokeContext()), msg);
    }
}
