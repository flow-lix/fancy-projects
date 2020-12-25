/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.mq.broker.queue;

import org.fancy.mq.common.PushRequest;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class BrokerQueue {

    /**
     * 阻塞有界队列
     */
    private static final ArrayBlockingQueue<PushRequest> BLOCKING_QUEUE = new ArrayBlockingQueue<>(1024);

    private static final int MAX_SIZE = 1024;

    private static final ArrayList<PushRequest> ARRAY_LIST = new ArrayList<>(MAX_SIZE);

    public static boolean offer(PushRequest message) {
        return BLOCKING_QUEUE.offer(message);
    }

    public static PushRequest take() {
        return BLOCKING_QUEUE.poll();
    }

    public static boolean add(PushRequest message) {
        if (ARRAY_LIST.size() == MAX_SIZE) {
            return false;
        }
        return ARRAY_LIST.add(message);
    }

    public static PushRequest pull(int idx) {
        return (idx < 0 || idx > ARRAY_LIST.size()) ? null : ARRAY_LIST.get(idx);
    }

}
