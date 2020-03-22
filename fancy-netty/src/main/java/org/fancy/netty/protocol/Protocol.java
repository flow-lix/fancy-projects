package org.fancy.netty.protocol;

import org.fancy.netty.command.CommandDecoder;
import org.fancy.netty.command.CommandEncoder;

public interface Protocol {

    CommandEncoder getEncoder();

    CommandDecoder getDecoder();
}
