package org.fancy.remoting.serialization;

import org.fancy.remoting.exception.SerializationException;

/**
 * 序列化接口
 */
public interface Serializer {

    byte[] serialize(final Object obj) throws SerializationException;

    <T> T deserialize(final byte[] data, String classOfT) throws SerializationException;
}
