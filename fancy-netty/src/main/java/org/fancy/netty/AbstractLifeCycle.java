package org.fancy.netty;

import org.fancy.netty.exception.LifeCycleException;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 生命周期管理类
 */
public abstract class AbstractLifeCycle implements LifeCycle {

    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    public void startup() throws LifeCycleException {
        if (this.isStarted.compareAndSet(false, true)) {
            return;
        }
        throw new LifeCycleException("The component has already isStarted!");
    }

    public void shutdown() throws LifeCycleException {
        if (this.isStarted.compareAndSet(true, false)) {
            return;
        }
        throw new LifeCycleException("The component has already shutdown!");
    }

    public boolean getIsStarted() {
        return this.isStarted.get();
    }
}
