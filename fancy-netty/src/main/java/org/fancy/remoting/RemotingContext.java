package org.fancy.remoting;

import io.netty.channel.ChannelHandlerContext;
import org.fancy.remoting.protocol.UserProcessor;

import java.util.concurrent.ConcurrentMap;

/**
 * 包装ChannelHandlerContext
 */
public class RemotingContext {

    private ChannelHandlerContext ctx;

    /**
     * 是否是服务端
     */
    private boolean serverSide;

    private boolean timeoutDiscard = true;
    private long arriveTimestamp;

    private int timeout;
    private int rpcCommandType;

    private ConcurrentMap<String, UserProcessor<?>> userProcessors;
    private InvokeContext invokeContext;

    public RemotingContext(ChannelHandlerContext ctx, boolean serverSide,
                           ConcurrentMap<String, UserProcessor<?>> userProcessors) {
        this.ctx = ctx;
        this.serverSide = serverSide;
        this.userProcessors = userProcessors;
    }

    public RemotingContext(ChannelHandlerContext ctx, boolean serverSide,
                           ConcurrentMap<String, UserProcessor<?>> userProcessors, InvokeContext invokeContext) {
        this.ctx = ctx;
        this.serverSide = serverSide;
        this.userProcessors = userProcessors;
        this.invokeContext = invokeContext;
    }
}
