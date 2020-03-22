package org.fancy.netty.serialization;

import org.fancy.netty.exception.SerializationException;

/**
 * 序列化接口
 */
public interface Serializer {

    byte[] serialize(final Object obj) throws SerializationException;

    <T> T deserialize(final byte[] data, Class<T> tClass) throws SerializationException;
}
