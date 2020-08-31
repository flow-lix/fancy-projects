package org.fancy.remoting.codec;

import io.netty.channel.ChannelHandler;
import org.fancy.remoting.protocol.ProtocolCode;
import org.fancy.remoting.protocol.RpcProtocolV2;

public class RpcCodecFactory implements CodecFactory {

    @Override
    public ChannelHandler newDecoder() {
        return null;
    }

    @Override
    public ChannelHandler newEncoder() {
        return new ProtocolCodeBasedEncoder(ProtocolCode.from(RpcProtocolV2.PROTOCOL_CODE));
    }
}
