package org.fancy.mq.core.serializer.json;

import com.alibaba.fastjson.JSON;
import org.fancy.mq.core.serializer.Serializer;

/**
 * fastJSON序列化
 */
public class JsonSerializer implements Serializer {

    @Override
    public <T> byte[] serializer(T obj) {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public <T> T deserializer(byte[] bytes) {
        return (T)JSON.parse(bytes);
    }

}
