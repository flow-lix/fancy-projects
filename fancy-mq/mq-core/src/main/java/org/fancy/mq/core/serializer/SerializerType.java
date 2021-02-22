package org.fancy.mq.core.serializer;

/**
 * 序列化类型
 */
public enum SerializerType {

    /**
     * JSON
     */
    JSON((byte)1),
    /**
     * protobuf
     */
    PROTOBUF((byte)2);

    private final byte code;

    SerializerType(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
