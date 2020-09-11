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

    SERVER_EXCEPTION(202),

    TIMEOUT(203),

    CLIENT_SEND_FAIL(204),

    CLIENT_SEND_ERROR(205),

    CONNECTION_CLOSED(206),

    SERVER_THREADPOOL_BUSY(207), CODEC_EXCEPTION(208),

    SERVER_SERIAL_EXCEPTION(209), SERVER_DESERIAL_EXCEPTION(210);

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
