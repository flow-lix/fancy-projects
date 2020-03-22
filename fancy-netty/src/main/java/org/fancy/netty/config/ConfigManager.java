package org.fancy.netty.config;

/**
 * 配置管理
 */
public class ConfigManager {

    public static byte serializer = getByte(Configs.SERIALIZER, Configs.SERIALIZER_DEFAULT);

    public static boolean isBufferPooled() {
        return getBoolean(Configs.NETTY_BUFFER_POOLED, Configs.NETTY_BUFFER_POOLED_DEFAULT);
    }

    public static boolean usTcpIdleCheck() {
        return getBoolean(Configs.TCP_IDLE_CHECK, Configs.TCP_IDLE_CHECK_DEFAULE);
    }

    public static int getTcpIdleTime() {
        return getInt(Configs.TCP_IDLE_CHECK_TIME, Configs.TCP_IDLE_CHECK_TIME_DEFAULT);
    }

    public static int getTcpBacklog() {
        return getInt(Configs.TCP_BACKLOG, Configs.TCP_BACKLOG_DEFAULT);
    }

    public static boolean isTcpReuseAddr() {
        return getBoolean(Configs.TCP_REUSEADDR, Configs.TCP_REUSEADDR_DEFAULT);
    }

    public static boolean isTcpNoDelay() {
        return getBoolean(Configs.TCP_NODELAY, Configs.TCP_NODELAY_DEFAULT);
    }

    public static boolean isTcpKeepAlive() {
        return getBoolean(Configs.TCP_KEEPALIVE, Configs.TCP_KEEPALIVE_DEFAULT);
    }

    private static int getInt(String key, String defVal) {
        return Integer.parseInt(System.getProperty(key, defVal));
    }

    public static boolean getBoolean(String key, String defVal) {
        return Boolean.parseBoolean(System.getProperty(key, defVal));
    }

    private static byte getByte(String key, String defVal) {
        return Byte.parseByte(System.getProperty(key, defVal));
    }
}
