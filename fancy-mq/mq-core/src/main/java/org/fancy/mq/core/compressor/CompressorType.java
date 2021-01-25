package org.fancy.mq.core.compressor;

/**
 * 压缩类型
 */
public enum CompressorType {

    /**
     * 不压缩
     */
    NONE((byte)0),
    /**
     * ZIP压缩
     */
    ZIP((byte)1),
    /**
     * GZIP压缩
     */
    GZIP((byte)2),
    /**
     * 默认压缩
     */
    DEFAULT((byte)3);

    private final byte code;

    CompressorType(byte code) {
        this.code = code;
    }

    public static CompressorType get(byte type) {
        for (CompressorType c : CompressorType.values()) {
            if (c.code == type) {
                return c;
            }
        }
        throw new IllegalArgumentException("unknown codec:" + type);
    }

    public byte getCode() {
        return code;
    }
}
