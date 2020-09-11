/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.rpc.exception;

import org.fancy.remoting.exception.RemotingException;

public class InvokeServerException extends RemotingException {

    public InvokeServerException(String message) {
        super(message);
    }

    public InvokeServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokeServerException(Throwable e) {
        super(e);
    }
}
