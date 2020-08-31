package org.fancy.remoting.codec;

import io.netty.channel.ChannelHandler;

public interface CodecFactory {

    ChannelHandler newDecoder();

    ChannelHandler newEncoder();
}
