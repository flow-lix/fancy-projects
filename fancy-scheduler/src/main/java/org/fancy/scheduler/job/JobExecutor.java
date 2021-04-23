package org.fancy.scheduler.job;

import org.fancy.scheduler.handler.IJobHandler;
import org.fancy.scheduler.thread.JobThread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JobExecutor {

    private static ConcurrentMap<Integer, JobThread> jobThreadRepository = new ConcurrentHashMap<>();

    public static JobThread registerJobThread(Integer jobId, IJobHandler jobHandler) {
        JobThread newJobThread = new JobThread(jobHandler);
        JobThread oldJobThread = jobThreadRepository.put(jobId, newJobThread);
        if (oldJobThread != null) {
            oldJobThread.shutdown();
            oldJobThread.interrupt();
        }
        newJobThread.start();
        return newJobThread;
    }

}
