/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import org.fancy.remoting.command.RemotingCommand;
import org.fancy.remoting.command.resp.ResponseCommand;
import org.fancy.remoting.protocol.RpcRequestCommand;

import java.net.InetSocketAddress;

public interface CommandFactory {

    RpcRequestCommand createRequestCommand(Object req);

    ResponseCommand createSendFailedResponse(final InetSocketAddress address, Throwable cause);

    ResponseCommand createTimeoutResponse(InetSocketAddress remoteAddress);
}
