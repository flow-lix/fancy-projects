package org.fancy.remoting.protocol;

import java.util.HashMap;
import java.util.Map;

public class ProtocolManager {

    private static final Map<ProtocolCode, Protocol> PROTOCOL_POOL = new HashMap<>();

    public void registerProtocol(ProtocolCode protocolCode, Protocol protocol) {
         PROTOCOL_POOL.putIfAbsent(protocolCode, protocol);
    }

    public static Protocol getProtocol(ProtocolCode protocolCode) {
        return PROTOCOL_POOL.get(protocolCode);
    }
}
