package org.fancy.remoting.command;

import org.fancy.remoting.exception.SerializationException;
import org.fancy.remoting.protocol.ProtocolCode;

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
