package org.fancy.mq.core.serializer;

import com.alibaba.fastjson.JSON;
import org.fancy.mq.core.serializer.hessian.HessianSerializer;
import org.fancy.mq.core.serializer.json.JsonSerializer;
import org.fancy.mq.core.serializer.protobuf.ProtobufSerializer;
import org.fancy.mq.core.serializer.protobuf.SimpleMessage;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.Objects;

public class SerializerTest {

    @Test
    public void testSerializer() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Pojo push = new Pojo();
            push.setName("dog");
            push.setTimestamp(System.currentTimeMillis());
            Assert.assertEquals(push, new HessianSerializer().deserializer(new HessianSerializer<>().serializer(push)));
        }
        // 333 1614
        System.out.println("cost: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void testProtoSerializer() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            SimpleMessage.ProtoMessage message = SimpleMessage.ProtoMessage.newBuilder()
                    .setCode(5)
                    .setName("xxx")
                    .setTimestamp(System.currentTimeMillis())
                    .build();
            Assert.assertEquals(message, new ProtobufSerializer().deserializer(new ProtobufSerializer().serializer(message)));
        }
        // 154 225
        System.out.println("cost: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void testJson() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Pojo message = new Pojo();
            message.setCode((short) 1);
            message.setName("dog");
            message.setTimestamp(System.currentTimeMillis());
            Assert.assertEquals(message, JSON.parseObject(JSON.toJSONString(message), Pojo.class));
        }
        // 154 225
        System.out.println("cost: " + (System.currentTimeMillis() - start));
    }

    static class Pojo implements Serializable {
        private static final long serialVersionUID = 8335013475547797513L;
        private short code;
        private String name;
        private long timestamp;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pojo pojo = (Pojo) o;
            return timestamp == pojo.timestamp &&
                    Objects.equals(name, pojo.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, timestamp);
        }

        public short getCode() {
            return code;
        }

        public void setCode(short code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
