package org.fancy.mq.core.serializer;

/**
 * 序列化接口
 */
public interface Serializer {

    /**
     * 序列化
     */
    <T> byte[] serializer(T obj);

    /**
     * 反序列化
     */
    <T> T deserializer(byte[] bytes);
}
