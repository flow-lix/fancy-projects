package org.fancy.mq.client.common.record;

import java.util.NoSuchElementException;

/**
 * 消息时间戳类型
 */
public enum TimestampType {

    /**
     * 没有类型
     */
    NO_TIMESTAMP_TYPE(-1, "NoTimestampType"),
    /**
     * 消息创建的时间
     */
    CREATE_TIME(0, "CreateTime"),
    /**
     * 消息追加到日志的时间
     */
    LOG_APPEND_TIME(1, "LogAppendTime");

    private final int id;
    private final String name;

    TimestampType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TimestampType forName(String name) {
        for(TimestampType timestampType : values()) {
            if (timestampType.equals(name)) {
                return timestampType;
            }
        }
        throw new NoSuchElementException("Invalid timestamp type: " + name);
    }

    @Override
    public String toString() {
        return name;
    }
}
