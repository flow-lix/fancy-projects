/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.exception;

public class InvokeTimeoutException extends RemotingException {

    public InvokeTimeoutException(String message) {
        super(message);
    }

    public InvokeTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokeTimeoutException(Throwable e) {
        super(e);
    }
}
