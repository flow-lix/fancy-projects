package org.fancy.mq.core.protocol;

import java.util.HashMap;
import java.util.Map;

public class RpcMessage {

    private short msgType;
    private byte serializer;
    private byte compressor;
    private Map<String, String> headerMap = new HashMap<>();
    private Object body;

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public Object getBody() {
        return body;
    }

    public short getMsgType() {
        return msgType;
    }

    public byte getSerializer() {
        return serializer;
    }

    public byte getCompressor() {
        return compressor;
    }

    public void setMsgType(short msgType) {
        this.msgType = msgType;
    }

    public void setSerializer(byte serializer) {
        this.serializer = serializer;
    }

    public void setCompressor(byte compressor) {
        this.compressor = compressor;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
