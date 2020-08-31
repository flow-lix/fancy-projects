/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.fancy.remoting.protocol.UserProcessor;

import java.util.concurrent.ConcurrentMap;

@ChannelHandler.Sharable
public class RpcHandler extends ChannelInboundHandlerAdapter {

    private ConcurrentMap<String, UserProcessor<?>> userProcessors;

    public RpcHandler(ConcurrentMap<String, UserProcessor<?>> userProcessors) {
        this.userProcessors = userProcessors;
    }
}
