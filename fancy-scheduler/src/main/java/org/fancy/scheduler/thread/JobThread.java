package org.fancy.scheduler.thread;

import org.fancy.scheduler.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class JobThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobThread.class);

    private volatile boolean stoped = false;

    private IJobHandler jobHandler;

    private LinkedBlockingQueue<String> triggerQueue;

    public JobThread(IJobHandler jobHandler) {
        this.jobHandler = jobHandler;
    }

    /**
     * new trigger to queue
     */
    public void pushTriggerQueue(String triggerParam) {
        triggerQueue.add(triggerParam);
    }

    @Override
    public void run() {

        while (!stoped) {
            try {
                String triggerParam = triggerQueue.poll(3L, TimeUnit.SECONDS);
                jobHandler.execute(triggerParam);
            } catch (Throwable e) {
                LOGGER.error("任务执行出错", e);
            }
        }
    }

    public void shutdown() {
        stoped = true;
    }
}
