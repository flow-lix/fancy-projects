package org.fancy.mq.core.serializer;

/**
 * 序列化接口
 */
public interface Serializer<T> {

    /**
     * 序列化
     */
    byte[] serializer(T obj);

    /**
     * 反序列化
     */
    T deserializer(byte[] bytes);
}
