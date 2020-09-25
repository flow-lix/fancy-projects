package org.fancy.remoting.protocol;

import org.fancy.remoting.CommandFactory;
import org.fancy.remoting.CommandHandler;
import org.fancy.remoting.command.CommandDecoder;
import org.fancy.remoting.command.CommandEncoder;
import org.fancy.remoting.rpc.RpcCommandFactory;

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

    private CommandHandler commandHandler;
    private CommandFactory commandFactory;

    public RpcProtocol() {
        this.commandFactory = new RpcCommandFactory();
        this.commandHandler = new RpcCommandHandler(commandFactory);
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

    @Override
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
