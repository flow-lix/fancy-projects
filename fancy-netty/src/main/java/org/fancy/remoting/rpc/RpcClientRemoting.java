/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.rpc;

import org.fancy.remoting.CommandFactory;
import org.fancy.remoting.DefaultConnectionManager;

public class RpcClientRemoting extends RpcRemoting{

    public RpcClientRemoting(CommandFactory commandFactory, DefaultConnectionManager connectionManager) {
        super(commandFactory, connectionManager);
    }
}
