package org.fancy.mq.client.consumer;

import java.util.Iterator;

/**
 * 一次拉取操作所获得的消息集
 */
public class ConsumerRecords implements Iterable<ConsumerRecord>{

    @Override
    public Iterator<ConsumerRecord> iterator() {
        return null;
    }
}
