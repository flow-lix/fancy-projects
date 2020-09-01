/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.common;

import org.fancy.remoting.Connection;
import org.fancy.remoting.ConnectionEventProcessor;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectEventProcessor implements ConnectionEventProcessor {

    private AtomicInteger invokeTime = new AtomicInteger(0);

    private volatile boolean connected;
    private AtomicInteger connectTimes = new AtomicInteger();

    private Connection connection;
    private String remoteAddr;
    private CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void onEvent(String remoteAddress, Connection connection) {
        doCheckConnection(connection);
        this.remoteAddr = remoteAddress;
        this.connection = connection;
        connected = true;
        connectTimes.incrementAndGet();
        latch.countDown();
    }

    private void doCheckConnection(Connection connection) {
        Objects.requireNonNull(connection);
        Objects.requireNonNull(connection.getChannel());
        Objects.requireNonNull(connection.getUrl());
    }

    public boolean isConnected() throws InterruptedException {
        latch.await();
        return connected;
    }

    public int getConnectTimes() throws InterruptedException{
        latch.await();
        return connectTimes.get();
    }

    public Connection getConnection() throws InterruptedException {
        latch.await();
        return connection;
    }

    public String getRemoteAddr() throws InterruptedException {
        latch.await();
        return remoteAddr;
    }

    public CountDownLatch getLatch() throws InterruptedException {
        latch.await();
        return latch;
    }

    public int getInvokeTime() {
        return invokeTime.get();
    }

    public void reset() {
        this.connectTimes.set(0);
        this.connected = false;
        this.connection = null;
    }
}
