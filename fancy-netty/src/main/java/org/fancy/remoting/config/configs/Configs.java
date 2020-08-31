package org.fancy.remoting.config.configs;

import org.fancy.remoting.serialization.SerializerManager;

public class Configs {

    public static final String NETTY_BUFFER_POOLED = "remoting.buffer.pooled";
    public static final String NETTY_BUFFER_POOLED_DEFAULT = "true";

    public static final String TCP_IDLE_CHECK = "remoting.tcp.idle.check";
    public static final String TCP_IDLE_CHECK_DEFAULE = "true";

    public static final String TCP_IDLE_CHECK_TIME = "remoting.tcp.idle.check.time";
    public static final String TCP_IDLE_CHECK_TIME_DEFAULT = "90000";

    public static final String TCP_BACKLOG = "remoting.tcp.backlog";
    public static final String TCP_BACKLOG_DEFAULT = "1024";

    public static final String TCP_REUSEADDR = "remoting.tcp.reuseaddr";
    public static final String TCP_REUSEADDR_DEFAULT = "true";

    public static final String TCP_NODELAY = "remoting.tcp.nodelay";
    public static final String TCP_NODELAY_DEFAULT = "true";

    public static final String TCP_KEEPALIVE = "remoting.tcp.keepalive";
    public static final String TCP_KEEPALIVE_DEFAULT = "true";

    public static final String SERIALIZER = "serializer";
    public static final String SERIALIZER_DEFAULT = String.valueOf(SerializerManager.HESSIAN2);

}
