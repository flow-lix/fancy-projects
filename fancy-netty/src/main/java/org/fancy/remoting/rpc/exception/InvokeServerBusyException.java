/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.rpc.exception;

import org.fancy.remoting.exception.RemotingException;

public class InvokeServerBusyException extends RemotingException {

    public InvokeServerBusyException(String message) {
        super(message);
    }

    public InvokeServerBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokeServerBusyException(Throwable e) {
        super(e);
    }
}
