package org.fancy.netty.exception;

/**
 * 生命周期异常
 */
public class LifeCycleException extends RuntimeException {

    public LifeCycleException(String message) {
        super(message);
    }
}
