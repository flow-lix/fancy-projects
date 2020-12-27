/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.mq.common;

import java.io.Serializable;

/**
 * 实现Serializable有什么用？
 */
public abstract class AbstractMessage implements Serializable {

    private short code;

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }
}
