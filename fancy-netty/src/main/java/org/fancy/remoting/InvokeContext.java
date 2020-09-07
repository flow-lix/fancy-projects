/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InvokeContext {

    public static final String PROCESS_WAIT_TIME = "process.wait.time";

    public static final String CLIENT_CONN_CREATE_TIME = "client.conn.create.time";

    public static final String CLIENT_LOCAL_IP = "client.local.ip";
    public static final String CLIENT_LOCAL_PORT = "client.local.port";

    public static final String CLIENT_REMOTE_IP  = "client.remote.ip";
    public static final String CLIENT_REMOTE_PORT = "client.remote.port";

    public static final String INVOKE_REQUEST_ID = "invoke.request.id";

    public static final int INITIAL_SIZE = 4;

    private ConcurrentMap<String, Object> context;

    public InvokeContext() {
        this.context = new ConcurrentHashMap<>(INITIAL_SIZE);
    }

    public Object get(String key) {
        return context.get(key);
    }

    public void putIfAbsent(String key, Object value) {
        this.context.putIfAbsent(key, value);
    }
}
