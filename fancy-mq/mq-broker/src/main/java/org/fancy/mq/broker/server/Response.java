/**
 * @Copyright (c) 2017, 广电运通信息科技有限公司 All Rights Reserved.
 */
package org.fancy.mq.broker.server;

public final class Response<T> {

    private Integer code;
    private T data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code:" + code +
                ", data: " + data +
                '}';
    }
}
