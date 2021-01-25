package org.fancy.mq.common;

import java.io.Serializable;

/**
 * @author l
 * 实现Serializable有什么用？
 */
public abstract class AbstractMessage implements Serializable {
    private static final long serialVersionUID = -9033908852614213062L;

    private short code;

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }
}
