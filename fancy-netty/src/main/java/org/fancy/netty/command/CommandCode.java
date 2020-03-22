package org.fancy.netty.command;

public interface CommandCode {

    short HEARTBEAT = 0;

    short value();
}
