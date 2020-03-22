package org.fancy.netty;

import org.fancy.netty.exception.LifeCycleException;

/**
 * lifeCycle 接口
 */
public interface LifeCycle {

    void startup() throws LifeCycleException;

    void shutdown()throws LifeCycleException;

    boolean getIsStarted();
}
