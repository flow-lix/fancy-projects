/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.common;

import java.io.Serializable;

public class RequestBody implements Serializable {

    public static final String DEFAULT_CLIENT_STR        = "HELLO WORLD! I'm from client";
    public static final String DEFAULT_SERVER_STR        = "HELLO WORLD! I'm from server";
    public static final String DEFAULT_SERVER_RETURN_STR = "HELLO WORLD! I'm server return";
    public static final String DEFAULT_CLIENT_RETURN_STR = "HELLO WORLD! I'm client return";

    public static final String DEFAULT_ONEWAY_STR        = "HELLO WORLD! I'm oneway req";
    public static final String DEFAULT_SYNC_STR          = "HELLO WORLD! I'm sync req";
    public static final String DEFAULT_FUTURE_STR        = "HELLO WORLD! I'm future req";
    public static final String DEFAULT_CALLBACK_STR      = "HELLO WORLD! I'm call back req";

    private int id;
    private String msg;
    private byte[] body;

    public RequestBody() {
        // json serializer 默认构造器
    }

    public RequestBody(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

}
