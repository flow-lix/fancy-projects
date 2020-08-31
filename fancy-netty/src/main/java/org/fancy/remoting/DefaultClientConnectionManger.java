/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import org.fancy.remoting.channel.ConnectionFactory;
import org.fancy.remoting.channel.ConnectionSelectStrategy;
import org.fancy.remoting.config.switches.GlobalSwitch;
import org.fancy.remoting.handler.ConnectionEventHandler;

public class DefaultClientConnectionManger extends DefaultConnectionManager {

    public DefaultClientConnectionManger(ConnectionSelectStrategy connectionSelectStrategy, ConnectionFactory connectionFactory,
                                         ConnectionEventHandler connectionEventHandler, ConnectionEventListener connectionEventListener,
                                         GlobalSwitch globalSwitch) {
        super(connectionSelectStrategy, connectionFactory, connectionEventHandler, connectionEventListener, globalSwitch);
    }
}
