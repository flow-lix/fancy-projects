package org.fancy.netty.command;

import org.fancy.netty.exception.SerializationException;
import org.fancy.netty.protocol.ProtocolCode;

import java.io.Serializable;

/**
 * 远程调用命令
 */
public interface RemotingCommand extends Serializable {

    ProtocolCode getProtocolCode();

    CommandCode getCommandCode();

    int getId();

    void serialize() throws SerializationException;

    void deserialize() throws SerializationException;
}
