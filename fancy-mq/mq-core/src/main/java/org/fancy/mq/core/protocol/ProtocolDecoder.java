package org.fancy.mq.core.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.fancy.mq.core.compressor.CompressorFactory;
import org.fancy.mq.core.serializer.hessian.HessianSerializer;
import org.fancy.mq.core.serializer.json.JsonSerializer;

import java.util.List;

/**
 * 0     1     2     3     4     5     6     7     8     9     10     11     12     13     14     15
 * -----------|------------------------+------+------+------+-------+-------+--------+--------+--------+
 *  magicCode | Msg |Seria|comp| HeaderLength |        BodyLength           | Header Map [Optional]... |
 *             Type lizer ressor
 *  body...
 */
public class ProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte b0 = in.readByte();
        byte b1 = in.readByte();
        if (ProtocolConstants.MAGIC_CODE_BYTES[0] != b0
                && ProtocolConstants.MAGIC_CODE_BYTES[1] != b1) {
            throw new IllegalStateException("Unknown magic code: " + b0 + ", " + b1);
        }
        RpcMessage message = new RpcMessage();
        message.setMsgType(in.readShort());
        message.setSerializer(in.readByte());
        message.setCompressor(in.readByte());

        int headerLen = in.readShort();
        message.getHeaderMap().putAll(HashMapSerializer.getInstance().deserializerHeader(in, headerLen));

        int bodyLen = in.readInt();
        byte[] body = new byte[bodyLen];
        in.readBytes(body);
        // decompressor 解压
        body = CompressorFactory.getCompressor(message.getCompressor()).decompressor(body);
        // deserializer 反序列化
        message.setBody(new HessianSerializer().deserializer(body));
        out.add(message);
    }

}
