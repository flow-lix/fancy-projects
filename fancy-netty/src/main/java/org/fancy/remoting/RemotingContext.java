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

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public boolean isServerSide() {
        return serverSide;
    }

    public void setServerSide(boolean serverSide) {
        this.serverSide = serverSide;
    }

    public boolean isTimeoutDiscard() {
        return timeoutDiscard;
    }

    public void setTimeoutDiscard(boolean timeoutDiscard) {
        this.timeoutDiscard = timeoutDiscard;
    }

    public long getArriveTimestamp() {
        return arriveTimestamp;
    }

    public void setArriveTimestamp(long arriveTimestamp) {
        this.arriveTimestamp = arriveTimestamp;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRpcCommandType() {
        return rpcCommandType;
    }

    public void setRpcCommandType(int rpcCommandType) {
        this.rpcCommandType = rpcCommandType;
    }

    public ConcurrentMap<String, UserProcessor<?>> getUserProcessors() {
        return userProcessors;
    }

    public void setUserProcessors(ConcurrentMap<String, UserProcessor<?>> userProcessors) {
        this.userProcessors = userProcessors;
    }

    public InvokeContext getInvokeContext() {
        return invokeContext;
    }

    public void setInvokeContext(InvokeContext invokeContext) {
        this.invokeContext = invokeContext;
    }
}
