/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.common;

import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.BizContext;
import org.fancy.remoting.exception.RemotingException;
import org.fancy.remoting.protocol.AbstractUserProcessor;
import org.junit.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.fancy.remoting.InvokeContext.PROCESS_WAIT_TIME;

@Slf4j
public class SimpleServerUserProcessor extends AbstractUserProcessor<RequestBody> {

    private AtomicInteger invokeTime = new AtomicInteger(0);
    private AtomicInteger syncTime = new AtomicInteger(0);
    private AtomicInteger asyncTime = new AtomicInteger(0);
    private AtomicInteger futureTime = new AtomicInteger(0);

    private String remoteAddr;

    private CountDownLatch latch = new CountDownLatch(1);

    @Override
    public Object handlerSyncRequest(BizContext bizContext, RequestBody request) throws RemotingException{
        if (bizContext.isRequestTimeout()) {
            String errMsg = "停止在业务线程中处理，已经超时了!";
            processTimes(request);
            log.warn(errMsg);
            throw new RemotingException(errMsg);
        }
        this.remoteAddr = bizContext.getRemoteAddr();

        Assert.assertNotNull(bizContext.getConnection());
        Assert.assertTrue(bizContext.getConnection().isFine());

        Long waitTime = (Long) bizContext.getInvokeContext().get(PROCESS_WAIT_TIME);
        log.info("Server User processor process wait time {}", waitTime);

        latch.countDown();
        log.warn("Server User processor say, remote address is {}.", this.remoteAddr);
        processTimes(request);

        return RequestBody.DEFAULT_SERVER_RETURN_STR;
    }

    private void processTimes(RequestBody request) {
    }

    @Override
    public void handlerAsyncRequest() {

    }
}
