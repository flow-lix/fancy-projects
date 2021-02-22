package org.fancy.mq.core.serializer.hessian;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.Objects;

public class HessianSerializerTest {

    @Test
    public void testSerializer() {
        Pojo push = new Pojo();
        push.setName("dog");
        push.setTimestamp(System.currentTimeMillis());
        Assert.assertEquals(push, new HessianSerializer().deserializer(new HessianSerializer().serializer(push)));
    }

    static class Pojo implements Serializable {
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
