package org.fancy.mq.consumer;

import org.fancy.mq.common.resp.PullResponse;

import java.util.concurrent.CountDownLatch;

public class AsyncResponse {

    private PullResponse response;

    private CountDownLatch countDownLatch;

    public AsyncResponse(CountDownLatch latch) {
        this.countDownLatch = latch;
    }


    public PullResponse getResponse() {
        return response;
    }

    public void setResponse(PullResponse response) {
        this.response = response;
    }

    public void countDown() {
        this.countDownLatch.countDown();
    }
}
