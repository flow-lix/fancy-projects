/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.channel.ConnectionFactory;
import org.fancy.remoting.channel.ConnectionSelectStrategy;
import org.fancy.remoting.channel.RandomSelectStrategy;
import org.fancy.remoting.config.switches.GlobalSwitch;
import org.fancy.remoting.exception.LifeCycleException;
import org.fancy.remoting.exception.RemotingException;
import org.fancy.remoting.handler.ConnectionEventHandler;
import org.fancy.remoting.util.RunStateRecordFutureTask;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultConnectionManager extends AbstractLifeCycle implements ConnectionManger, Scannable {

    private ConcurrentMap<String, RunStateRecordFutureTask<ConnectionPool>> connTasks;

    private ConcurrentMap<String, FutureTask<Integer>> governTasks;

    private ConnectionSelectStrategy connectionSelectStrategy;

    private ConnectionFactory connectionFactory;

    private ConnectionEventHandler connectionEventHandler;

    private ConnectionEventListener connectionEventListener;

    private GlobalSwitch globalSwitch;

    /**
     * 异步创建连接执行器
     */
    private ThreadPoolExecutor asyncCreateConnectionExecutor;

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
    public void check(Connection connection) throws RemotingException{
        if (connection == null) {
            throw new RemotingException("连接不能为空！");
        }
        if (connection.getChannel() == null || !connection.getChannel().isActive()) {
            throw new RemotingException("检查连接失败，地址：" + connection.getUrl());
        }
        if (!connection.getChannel().isWritable()) {
            throw new RemotingException("检查连接失败，连接不可写，地址： " + connection.getUrl());
        }
    }

    @Override
    public void startup() throws LifeCycleException {
        super.startup();
        this.asyncCreateConnectionExecutor = new ThreadPoolExecutor(3, 8, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50), new DefaultThreadFactory("conn-warmup-executor", true));
        this.connectionEventHandler.setConnectionManger(this);
        this.connectionEventHandler.setConnectionEventListener(connectionEventListener);
        this.connectionFactory.init(connectionEventHandler);
    }

    @Override
    public void shutdown() throws LifeCycleException {

    }

    @Override
    public void scan() {

    }

    @Override
    public Connection get(String poolKey) {
        return null;
    }

    @Override
    public void add(Connection connection, String poolKey) {

    }

    public Connection getAndCreateIfAbsent(Url url) throws RemotingException {
        ConnectionPool pool = this.getConnectionPoolAndCreateIfAbsent(url.getUniqueKey(),
                new ConnectionPoolCall(url));
        Objects.requireNonNull(pool);
        return pool.get();
    }

    private ConnectionPool getConnectionPoolAndCreateIfAbsent(String poolKey, ConnectionPoolCall callable)
            throws RemotingException {
        RunStateRecordFutureTask<ConnectionPool> initialiTask;
        int retry = 2;
        ConnectionPool pool;
        for (int i = 0; i < retry; i++) {
            initialiTask = this.connTasks.get(poolKey);
            if (null == initialiTask) {
                RunStateRecordFutureTask<ConnectionPool> newTask = new RunStateRecordFutureTask<>(callable);
                initialiTask = this.connTasks.putIfAbsent(poolKey, newTask);
                if (null == initialiTask) {
                    initialiTask = newTask;
                    initialiTask.run();
                }
            }
            try {
                pool = initialiTask.get();
                if (pool != null) {
                    return pool;
                }
            } catch (Exception e) {
                log.error("获取连接池异常!", e);
                throw new RemotingException(e);
            }
        }
        this.connTasks.remove(poolKey);
        throw new RemotingException("重试" + retry + "次后还是获取不到连接池!");
    }

    private void doCreate(final Url url, final ConnectionPool pool, final String taskName,
                          final int syncCreateNumWhenNotWarmup) throws RemotingException {
        final int actualNum = pool.size();
        final int expectNum = url.getConnNum();
        if (actualNum >= expectNum) {
            return;
        }
        log.debug("Actual num: {}, expect num {}, task name: {}", actualNum, expectNum, taskName);
        if (url.isConnWarmup()) {
            for (int i = actualNum; i < expectNum; i++) {
                pool.add(create(url));
            }
        } else {
            if (syncCreateNumWhenNotWarmup < 0 || syncCreateNumWhenNotWarmup > expectNum) {
                throw new IllegalArgumentException("不热更时同步创建的数量必须在[0, " + expectNum + "]之间！");
            }
            for (int i = 0; i < syncCreateNumWhenNotWarmup; i++) {
                pool.add(create(url));
            }
            if (syncCreateNumWhenNotWarmup == expectNum) {
                return;
            }
            // 开始异步创建
            pool.makeAsyncCreateStart();
            try {
                this.asyncCreateConnectionExecutor.execute(() -> {
                    try {
                        for (int i = pool.size(); i < expectNum; i++) {
                            try {
                                pool.add(create(url));
                            } catch (Exception e) {
                                log.error("异步创建连接[{}]失败!", url.getUniqueKey());
                            }
                        }
                    } finally {
                        pool.markAsyncCreateDone();
                    }
                });
            } catch (RejectedExecutionException e) {
                // 被拒绝时创建完成
                pool.markAsyncCreateDone();
                throw e;
            }
        }
    }

    private Connection create(Url url) throws RemotingException {
        Connection conn;
        try {
             conn = this.connectionFactory.createConnection(url);
        } catch (Exception e) {
            throw new RemotingException("创建连接失败,地址：" + url.getOriginUrl(), e);
        }
        return conn;
    }

    /**
     * 回调连接池
     */
    private  class ConnectionPoolCall implements Callable<ConnectionPool> {
        private boolean whetherInitConnection;
        private Url url;

        public ConnectionPoolCall() {
            this.whetherInitConnection = false;
        }

        public ConnectionPoolCall(Url url) {
            whetherInitConnection = true;
            this.url = url;
        }

        @Override
        public ConnectionPool call() throws Exception {
            final ConnectionPool pool = new ConnectionPool(connectionSelectStrategy);
            if (whetherInitConnection) {
                doCreate(this.url, pool, this.getClass().getSimpleName(), 1);
            }
            return pool;
        }
    }
}
