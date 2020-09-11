/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.rpc.exception;

import org.fancy.remoting.exception.RemotingException;

public class InvokeSendFailedException extends RemotingException {

    public InvokeSendFailedException(String message) {
        super(message);
    }

    public InvokeSendFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokeSendFailedException(Throwable e) {
        super(e);
    }
}
