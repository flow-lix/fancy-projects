/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import org.fancy.remoting.ConnectionEventListener;
import org.fancy.remoting.ConnectionManger;
import org.fancy.remoting.Reconnector;
import org.fancy.remoting.config.switches.GlobalSwitch;

@ChannelHandler.Sharable
public class ConnectionEventHandler extends ChannelDuplexHandler {

    private ConnectionManger connectionManger;

    private ConnectionEventListener connectionEventListener;

    private Reconnector reconnector;

    private GlobalSwitch globalSwitch;

    public ConnectionEventHandler() {
    }

    public ConnectionManger getConnectionManger() {
        return connectionManger;
    }

    public void setConnectionManger(ConnectionManger connectionManger) {
        this.connectionManger = connectionManger;
    }

    public ConnectionEventListener getConnectionEventListener() {
        return connectionEventListener;
    }

    public void setConnectionEventListener(ConnectionEventListener connectionEventListener) {
        this.connectionEventListener = connectionEventListener;
    }
}
