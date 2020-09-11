package org.fancy.remoting.serialization;

import org.fancy.remoting.exception.SerializationException;

/**
 * What Hassian
 */
public class HassianSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, String classOfT) throws SerializationException {
        return null;
    }
}
