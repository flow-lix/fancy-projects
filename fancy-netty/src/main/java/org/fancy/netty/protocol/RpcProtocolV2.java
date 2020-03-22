package org.fancy.netty.protocol;

import org.fancy.netty.command.CommandDecoder;
import org.fancy.netty.command.CommandEncoder;

/**
 *  / requestType | CommandCode | requestId | codec | switch | timeout | responseStatus
 *  | header | body | crc32|
 *
 *
 *  / type | cmdcode | requestId | codec | switch | responseStatus |
 *  / headerLen | contentLen | header | content |
 */
public class RpcProtocolV2 implements Protocol {

    public static final byte PROTOCOL_CODE = 2;

    private final CommandEncoder encoder;
    private final CommandDecoder decoder;

    public RpcProtocolV2(CommandEncoder encoder, CommandDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
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
