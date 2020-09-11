/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.rpc;

import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.CommandFactory;
import org.fancy.remoting.InvokeCallback;
import org.fancy.remoting.InvokeCallbackListener;
import org.fancy.remoting.InvokeContext;
import org.fancy.remoting.InvokeFuture;
import org.fancy.remoting.command.RemotingCommand;
import org.fancy.remoting.command.resp.ResponseCommand;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultInvokeFuture implements InvokeFuture {

    private int invokeId;
    private InvokeCallbackListener callbackListener;
    private InvokeCallback callback;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private ResponseCommand responseCommand;

    private InvokeContext invokeContext;

    private CommandFactory commandFactory;

    @Override
    public int invokeId() {
        return 0;
    }

    public DefaultInvokeFuture(int invokeId, InvokeCallbackListener callbackListener,
                               InvokeCallback callback, CommandFactory commandFactory) {
        this.invokeId = invokeId;
        this.callbackListener = callbackListener;
        this.callback = callback;
        this.commandFactory = commandFactory;
    }

    public DefaultInvokeFuture(int invokeId, InvokeCallbackListener callbackListener,
                               InvokeCallback callback, CommandFactory commandFactory, InvokeContext invokeContext) {
        this(invokeId, callbackListener, callback, commandFactory);
        this.invokeContext = invokeContext;
    }

    @Override
    public void putResponse(RemotingCommand remotingCommand) {
        this.responseCommand = (ResponseCommand) remotingCommand;
        countDownLatch.countDown();
    }

    @Override
    public ResponseCommand waitResponse(int timeoutMillis) throws InterruptedException {
        countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return responseCommand;
    }
}
