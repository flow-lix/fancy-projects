package org.fancy.mq.client.consumer;

import org.fancy.mq.client.common.header.Headers;
import org.fancy.mq.client.common.record.TimestampType;

/**
 * 消费到的每条消息的类型
 */
public class ConsumerRecord<K, V> {

    // 消息所属的topic
    private final String topic;
    // 消息所属的分区编号
    private final int partition;
    // 消息所在分区的偏移量
    private final long offset;
    private final K key;
    private final V value;
    private final int serializerKeySize;
    private final int serializerValueSize;
    private final Headers headers;

    private final long timestamp;
    // 时间戳类型， 创建时间戳或追加到日志的时间戳
    private final TimestampType timestampType;

    // CRC32的校验值
    private long checkNum;

    /**
     * 创建一个从topic和partition中接收到的消息
     */
    public ConsumerRecord(String topic,
                          int partition,
                          long offset,
                          K key,
                          V value,
                          int serializerKeySize,
                          int serializerValueSize,
                          Headers headers,
                          long timestamp,
                          TimestampType timestampType) {
        if (topic == null) {
            throw new IllegalArgumentException("Topic cannot be null.");
        }
        if (headers == null) {
            throw new IllegalArgumentException("headers cannot be null");
        }
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.key = key;
        this.value = value;
        this.serializerKeySize = serializerKeySize;
        this.serializerValueSize = serializerValueSize;
        this.headers = headers;
        this.timestamp = timestamp;
        this.timestampType = timestampType;
    }

    public String topic() {
        return topic;
    }

    public int partition() {
        return partition;
    }

    public long offset() {
        return offset;
    }

    public K key() {
        return key;
    }

    public V value() {
        return value;
    }
}
