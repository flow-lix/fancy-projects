package org.fancy.remoting;

import org.fancy.remoting.exception.LifeCycleException;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 生命周期管理类
 */
public abstract class AbstractLifeCycle implements LifeCycle {

    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    @Override
    public void startup() throws LifeCycleException {
        if (this.isStarted.compareAndSet(false, true)) {
            return;
        }
        throw new LifeCycleException("The component has already isStarted!");
    }

    @Override
    public void shutdown() throws LifeCycleException {
        if (this.isStarted.compareAndSet(true, false)) {
            return;
        }
        throw new LifeCycleException("The component has already shutdown!");
    }

    @Override
    public boolean isStarted() {
        return this.isStarted.get();
    }

    protected void ensureStarted() {
        if (!isStarted()) {
            throw new LifeCycleException(String.format("Component %s 还没有启动!", getClass().getSimpleName()));
        }
    }
}
