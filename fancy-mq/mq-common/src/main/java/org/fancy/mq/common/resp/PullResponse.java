package org.fancy.mq.common.resp;

import org.fancy.mq.common.AbstractMessage;

import java.util.concurrent.CountDownLatch;

public class PullResponse extends AbstractMessage {

    private String name;
    private long timestamp;

    private transient CountDownLatch latch;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void countDown() {
        this.latch.countDown();
    }
}
