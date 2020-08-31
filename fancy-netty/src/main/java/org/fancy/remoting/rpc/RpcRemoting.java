/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.rpc;

import org.fancy.remoting.CommandFactory;
import org.fancy.remoting.DefaultConnectionManager;

public abstract class RpcRemoting {

    private CommandFactory commandFactory;
    private DefaultConnectionManager connectionManager;

    public RpcRemoting(CommandFactory commandFactory, DefaultConnectionManager connectionManager) {
        this.commandFactory = commandFactory;
        this.connectionManager = connectionManager;
    }
}
