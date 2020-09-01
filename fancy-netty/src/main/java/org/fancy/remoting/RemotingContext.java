/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import io.netty.channel.ChannelHandlerContext;
import org.fancy.remoting.protocol.UserProcessor;

import java.util.concurrent.ConcurrentMap;

public class RemotingContext {

    private ChannelHandlerContext ctx;

    private boolean timeoutDiscard = true;
    private long arriveTimestamp;

    private int timeout;
    private int rpcCommandType;

    private ConcurrentMap<String, UserProcessor<?>> userProcessors;
    private InvokeContext invokeContext;
}
