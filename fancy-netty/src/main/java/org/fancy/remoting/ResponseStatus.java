package org.fancy.remoting;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应状态
 */
public enum ResponseStatus {

    /**
     * 成功
     */
    SUCCESS(200),

    ERROR(201),

    SERVER_EXCEPTION(202)
    ;

    private final short code;
    public static final Map<Short, ResponseStatus> STATUS_MAP = new HashMap<>();

    ResponseStatus(int code) {
        this.code = (short) code;
    }

    static {
        for (ResponseStatus s : ResponseStatus.values()) {
            STATUS_MAP.put(s.code, s);
        }
    }

    public static ResponseStatus of(short code) {
        return STATUS_MAP.get(code);
    }

    public int getCode() {
        return code;
    }
}
