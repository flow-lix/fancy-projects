package org.fancy.netty.config;

import org.fancy.netty.serialization.SerializerManager;

public class Configs {

    public static final String NETTY_BUFFER_POOLED = "netty.buffer.pooled";
    public static final String NETTY_BUFFER_POOLED_DEFAULT = "true";

    public static final String TCP_IDLE_CHECK = "netty.tcp.idle.check";
    public static final String TCP_IDLE_CHECK_DEFAULE = "true";

    public static final String TCP_IDLE_CHECK_TIME = "netty.tcp.idle.check.time";
    public static final String TCP_IDLE_CHECK_TIME_DEFAULT = "90000";

    public static final String TCP_BACKLOG = "netty.tcp.backlog";
    public static final String TCP_BACKLOG_DEFAULT = "1024";

    public static final String TCP_REUSEADDR = "netty.tcp.reuseaddr";
    public static final String TCP_REUSEADDR_DEFAULT = "true";

    public static final String TCP_NODELAY = "netty.tcp.nodelay";
    public static final String TCP_NODELAY_DEFAULT = "true";

    public static final String TCP_KEEPALIVE = "netty.tcp.keepalive";
    public static final String TCP_KEEPALIVE_DEFAULT = "true";

    public static final String SERIALIZER = "serializer";
    public static final String SERIALIZER_DEFAULT = String.valueOf(SerializerManager.HESSIAN2);

}
