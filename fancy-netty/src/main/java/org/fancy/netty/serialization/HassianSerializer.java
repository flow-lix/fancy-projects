package org.fancy.netty.serialization;

import org.fancy.netty.exception.SerializationException;

/**
 * What Hassian
 */
public class HassianSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> tClass) throws SerializationException {
        return null;
    }
}
