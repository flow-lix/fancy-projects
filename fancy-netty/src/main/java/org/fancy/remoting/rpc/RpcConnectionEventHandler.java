/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.rpc;

import io.netty.channel.ChannelHandlerContext;
import org.fancy.remoting.handler.ConnectionEventHandler;

public class RpcConnectionEventHandler extends ConnectionEventHandler {

    public RpcConnectionEventHandler() {
        super();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
