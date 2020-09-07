package org.fancy.remoting;

import org.fancy.remoting.exception.LifeCycleException;

/**
 * lifeCycle 接口
 */
public interface LifeCycle {

    void startup() throws LifeCycleException;

    void shutdown()throws LifeCycleException;

    boolean isStarted();

}
