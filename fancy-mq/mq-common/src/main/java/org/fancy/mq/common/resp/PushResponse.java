/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.mq.common.resp;

import org.fancy.mq.common.AbstractMessage;

/**
 * @author lxiang
 */
public class PushResponse extends AbstractMessage {

    public static final PushResponse SUCCESS = new PushResponse();

    private String msg;

    public static PushResponse failOf(String msg) {
        PushResponse response = new PushResponse();
        response.setCode((short) 500);
        response.setMsg(msg);
        return response;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
