package org.fancy.mq.common;

import java.io.Serializable;

/**
 * @author l
 * 能否继承私有变量
 */
public class PushRequest extends AbstractMessage implements Serializable {
    private static final long serialVersionUID = -5166150441873742865L;
    private String name;
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PushRequest{" +
                "name='" + name + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
