package org.fancy.test;

import cn.hutool.core.thread.NamedThreadFactory;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Synchronized 测试
 */
public class SynchronizedTest {

    // 超过corePoolSize的线程要等待keepAliveTime后才销毁
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(0, 20,
            6, TimeUnit.SECONDS, new SynchronousQueue<>(),
           new NamedThreadFactory("synchronized_test_pool", false));

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            THREAD_POOL.execute(new RunningTest());
        }
        THREAD_POOL.awaitTermination(10, TimeUnit.SECONDS);
        // 如果线程池内的线程是守护线程，则JVM直接shutdown
        System.out.println("finished!");
    }

    static class RunningTest implements Runnable {
        @Override
        public void run() {
            synchronized (SynchronizedTest.class) {
                System.out.printf("线程：%s, 时间：%s", Thread.currentThread().getName(), new Date());
                System.out.println();
                try {
                    Thread.sleep(5 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
