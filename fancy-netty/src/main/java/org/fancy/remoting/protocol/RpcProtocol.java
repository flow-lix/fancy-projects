package org.fancy.remoting.protocol;

import org.fancy.remoting.command.CommandDecoder;
import org.fancy.remoting.command.CommandEncoder;

/**
 *  / requestType | CommandCode | requestId | codec | switch | timeout | responseStatus
 *  | header | body | crc32|
 *
 *
 *  / type | cmdcode | requestId | codec | switch | responseStatus |
 *  / headerLen | contentLen | header | content |
 */
public class RpcProtocol implements Protocol {

    private final CommandEncoder encoder;
    private final CommandDecoder decoder;

    public RpcProtocol() {
        this.encoder = new RpcCommandEncoder();
        this.decoder = new RpcCommandDecoder();
    }

    @Override
    public CommandEncoder getEncoder() {
        return encoder;
    }

    @Override
    public CommandDecoder getDecoder() {
        return decoder;
    }

}
