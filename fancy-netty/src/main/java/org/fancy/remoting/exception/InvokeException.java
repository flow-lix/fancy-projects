/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.exception;

public class InvokeException extends RemotingException {

    public InvokeException(String message) {
        super(message);
    }

    public InvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokeException(Throwable e) {
        super(e);
    }
}
