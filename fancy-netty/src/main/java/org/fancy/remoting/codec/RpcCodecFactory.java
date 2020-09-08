package org.fancy.remoting.codec;

import io.netty.channel.ChannelHandler;
import org.fancy.remoting.protocol.ProtocolCode;
import org.fancy.remoting.protocol.RpcProtocol;

public class RpcCodecFactory implements CodecFactory {

    @Override
    public ChannelHandler newDecoder() {
        return new ProtocolCodeBasedDecoder();
    }

    @Override
    public ChannelHandler newEncoder() {
        return new ProtocolCodeBasedEncoder();
    }
}
