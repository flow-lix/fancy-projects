package org.fancy.mq.core.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.fancy.mq.core.serializer.hessian.HessianSerializer;
import org.fancy.mq.core.serializer.json.JsonSerializer;

import static org.fancy.mq.core.protocol.ProtocolConstants.MAGIC_CODE_BYTES;

/**
 * 0     1     2     3     4     5     6     7     8     9     10     11     12     13     14     15
 * -----------|------------|------------+------+------+------+-------+-------+--------+--------+--------+
 *  magicCode |   MsgType  |Seria|comp| HeaderLength |        BodyLength           | Header Map [Optional]... |
 *                        lizer ressor
 *  body...
 */
public class ProtocolEncoder extends MessageToByteEncoder {

    /**
     * 入栈消息编码，下行流写输出,从应用层写到数据链路层
     * @param ctx ChannelHandler上下文
     * @param msg 下行消息
     * @param out 输出缓存
     * @throws Exception 编码异常
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (msg instanceof RpcMessage) {
            RpcMessage message = (RpcMessage) msg;
            out.writeBytes(MAGIC_CODE_BYTES);
            short msgType = message.getMsgType();
            out.writeShort(msgType);
            out.writeByte(message.getSerializer());
            out.writeByte(message.getCompressor());

            // 预留header(2B) + body(4B)长度位
            int reserved = 2+4;
            int begin = out.writerIndex();
            out.writerIndex(begin + reserved);
            // 写入header
            int headerLength = HashMapSerializer.getInstance().serializerHeader(message.getHeaderMap(), out);
            // 写入body
            byte[] body;
            int bodyLength = 0;
            if (msgType != ProtocolConstants.HEARTBEAT_REQUEST && msgType != ProtocolConstants.HEARTBEAT_RESPONSE) {
                // 序列化
                body = new HessianSerializer().serializer(message.getBody());
                // toDo 压缩
                out.writeBytes(body);
                bodyLength = body.length;
            }
            int endIdx = out.writerIndex();

            // 定位到headerLength位置
            out.writerIndex(begin);
            out.writeShort(headerLength);
            out.writeInt(bodyLength);
            // 重置到写缓存end位置
            out.writerIndex(endIdx);
        } else {
            throw new UnsupportedOperationException("Not support this class:" + msg.getClass());
        }
    }
}
