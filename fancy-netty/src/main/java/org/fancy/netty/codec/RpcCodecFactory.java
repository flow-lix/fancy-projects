package org.fancy.netty.codec;

import io.netty.channel.ChannelHandler;
import org.fancy.netty.protocol.ProtocolCode;
import org.fancy.netty.protocol.RpcProtocolV2;

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
