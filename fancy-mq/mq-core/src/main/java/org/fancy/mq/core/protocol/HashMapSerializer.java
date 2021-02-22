package org.fancy.mq.core.protocol;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HashMapSerializer {

    private static final HashMapSerializer INSTANCE = new HashMapSerializer();

    private HashMapSerializer() {
    }

    public static HashMapSerializer getInstance() {
        return INSTANCE;
    }

    /**
     * 序列化Header
     * @return 字节长度
     */
    public int serializerHeader(Map<String, String> headerMap, ByteBuf out) {
        if (headerMap == null || !headerMap.isEmpty()) {
            return 0;
        }
        int start = out.writerIndex();
        // hahsMap的迭代，如果是红黑树，迭代TreeNode，循环next
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            encodeString(entry.getKey(), out);
            encodeString(entry.getValue(), out);
        }
        return out.writerIndex() - start;
    }

    public Map<String, String> deserializerHeader(ByteBuf in, int headerLen) {
        if (headerLen == 0) {
            return Collections.emptyMap();
        }
        HashMap<String, String> headerMap = new HashMap<>();
        int startIdx = in.readerIndex();
        while (in.readerIndex() - startIdx < headerLen) {
            String key = decodeString(in);
            String val = decodeString(in);
            headerMap.put(key, val);
        }
        return headerMap;
    }

    private void encodeString(String str, ByteBuf out) {
        if (str == null) {
            out.writeShort(-1);
        } else if (str.isEmpty()) {
            out.writeShort(0);
        } else {
            out.writeShort(str.length());
            out.writeBytes(str.getBytes());
        }
    }

    private String decodeString(ByteBuf in) {
        short length = in.readShort();
        if (length == -1) {
            return null;
        } else if (length == 0) {
            return "";
        } else {
            byte[] body = new byte[in.readShort()];
            in.readBytes(body);
            return new String(body);
        }
    }

}
