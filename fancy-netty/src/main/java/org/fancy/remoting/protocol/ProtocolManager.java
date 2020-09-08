package org.fancy.remoting.protocol;

public class ProtocolManager {

    private static final Protocol protocol = new RpcProtocol();

    public static Protocol getProtocol() {
        return protocol;
    }

}
