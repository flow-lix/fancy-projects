/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.mq.common.resp;

import org.fancy.mq.common.Message;

import java.util.concurrent.CountDownLatch;

public class PullResponse {

    private Message message;

    private transient CountDownLatch latch;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void countDown() {
        this.latch.countDown();
    }
}
