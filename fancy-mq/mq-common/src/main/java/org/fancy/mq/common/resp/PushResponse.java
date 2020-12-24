/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.mq.common.resp;

public class PushResponse {

    public static final PushResponse SUCCESS = new PushResponse();

    private short code;
    private String msg;

    public static PushResponse failOf(String msg) {
        PushResponse response = new PushResponse();
        response.setCode((short) 500);
        response.setMsg(msg);
        return response;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
