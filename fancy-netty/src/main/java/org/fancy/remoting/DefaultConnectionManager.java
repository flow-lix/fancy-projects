/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.channel.ConnectionFactory;
import org.fancy.remoting.channel.ConnectionSelectStrategy;
import org.fancy.remoting.channel.RandomSelectStrategy;
import org.fancy.remoting.config.switches.GlobalSwitch;
import org.fancy.remoting.exception.LifeCycleException;
import org.fancy.remoting.handler.ConnectionEventHandler;
import org.fancy.remoting.util.RunStateRecordFutureTask;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.FutureTask;

@Slf4j
public class DefaultConnectionManager implements LifeCycle, Scannable {

    private ConcurrentMap<String, RunStateRecordFutureTask<ConnectionPool>> connTasks;

    private ConcurrentMap<String, FutureTask<Integer>> governTasks;

    private ConnectionSelectStrategy connectionSelectStrategy;

    private ConnectionFactory connectionFactory;

    private ConnectionEventHandler connectionEventHandler;

    private ConnectionEventListener connectionEventListener;

    private GlobalSwitch globalSwitch;

    public DefaultConnectionManager() {
        this.connTasks = new ConcurrentHashMap<>();
        this.governTasks = new ConcurrentHashMap<>();
        this.connectionSelectStrategy = new RandomSelectStrategy();
    }

    public DefaultConnectionManager(ConnectionSelectStrategy connectionSelectStrategy) {
        this.connTasks = new ConcurrentHashMap<>();
        this.governTasks = new ConcurrentHashMap<>();
        this.connectionSelectStrategy = connectionSelectStrategy;
    }

    public DefaultConnectionManager(ConnectionSelectStrategy connectionSelectStrategy, ConnectionFactory connectionFactory) {
        this(connectionSelectStrategy);
        this.connectionFactory = connectionFactory;
    }

    public DefaultConnectionManager(ConnectionSelectStrategy connectionSelectStrategy, ConnectionFactory connectionFactory,
                                    ConnectionEventHandler connectionEventHandler, ConnectionEventListener connectionEventListener) {
        this(connectionSelectStrategy, connectionFactory);
        this.connectionEventHandler = connectionEventHandler;
        this.connectionEventListener = connectionEventListener;
    }

    public DefaultConnectionManager(ConnectionSelectStrategy connectionSelectStrategy, ConnectionFactory connectionFactory,
                                    ConnectionEventHandler connectionEventHandler, ConnectionEventListener connectionEventListener,
                                    GlobalSwitch globalSwitch) {
        this(connectionSelectStrategy, connectionFactory, connectionEventHandler, connectionEventListener);
        this.globalSwitch = globalSwitch;
    }

    @Override
    public void startup() throws LifeCycleException {

    }

    @Override
    public void shutdown() throws LifeCycleException {

    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public void scan() {

    }
}
