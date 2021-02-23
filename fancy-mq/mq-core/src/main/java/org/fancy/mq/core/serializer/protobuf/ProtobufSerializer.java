package org.fancy.mq.core.serializer.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.fancy.mq.core.serializer.Serializer;

@Slf4j
public class ProtobufSerializer implements Serializer<SimpleMessage.ProtoMessage> {

    @Override
    public byte[] serializer(SimpleMessage.ProtoMessage obj) {
        SimpleMessage.ProtoMessage msg = SimpleMessage.ProtoMessage.newBuilder()
                .setCode(obj.getCode())
                .setName(obj.getName())
                .setTimestamp(obj.getTimestamp())
                .build();
        return msg.toByteArray();
    }

    @Override
    public SimpleMessage.ProtoMessage deserializer(byte[] bytes) {
        try {
            return SimpleMessage.ProtoMessage.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            log.error("Protobuf 反序列化异常!", e);
        }
        return SimpleMessage.ProtoMessage.newBuilder()
                .setCode(-1)
                .setName("N/A")
                .setTimestamp(0L)
                .build();
    }

}
