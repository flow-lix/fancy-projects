package org.fancy.scheduler.thread;

import org.fancy.scheduler.job.JobExecutor;

public class TriggerJobPoolManager {


    public static void trigger(Integer jobId) {
        JobExecutor.registerJobThread(jobId, null);
    }
}
