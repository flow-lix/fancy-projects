package org.fancy.mq.core.protocol;

/**
 * @author l
 */
public class ProtocolConstants {

    public static final byte HEARTBEAT_REQUEST = 3;

    public static final byte HEARTBEAT_RESPONSE = 4;

    private ProtocolConstants() {
    }

    public static final byte[] MAGIC_CODE_BYTES = {(byte)0xba, (byte)0xbb};


}
