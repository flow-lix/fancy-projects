package org.fancy.remoting.protocol;

import org.fancy.remoting.CommandHandler;
import org.fancy.remoting.command.CommandDecoder;
import org.fancy.remoting.command.CommandEncoder;

public interface Protocol {

    CommandEncoder getEncoder();

    CommandDecoder getDecoder();

    CommandHandler getCommandHandler();
}
