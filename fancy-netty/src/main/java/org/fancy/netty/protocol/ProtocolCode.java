package org.fancy.netty.protocol;

import java.io.Serializable;

public class ProtocolCode implements Serializable {

    private final byte[] code;

    private ProtocolCode(byte[] code) {
        this.code = code;
    }

    public static ProtocolCode from(byte... version) {
        return new ProtocolCode(version);
    }
}
