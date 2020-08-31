/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import org.fancy.remoting.exception.LifeCycleException;

public class DefaultConnectionMonitor implements LifeCycle {

    @Override
    public void startup() throws LifeCycleException {

    }

    @Override
    public void shutdown() throws LifeCycleException {

    }

    @Override
    public boolean isStarted() {
        return false;
    }
}
