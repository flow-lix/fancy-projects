/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.rpc;

import org.fancy.remoting.LifeCycle;
import org.fancy.remoting.Scannable;
import org.fancy.remoting.exception.LifeCycleException;

import java.util.LinkedList;
import java.util.List;

/**
 * 扫描任务
 */
public class RpcTaskScanner implements LifeCycle {

    private List<Scannable> scanList;

    public RpcTaskScanner() {
        this.scanList = new LinkedList<>();
    }

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

    public void add(Scannable scannable) {
        this.scanList.add(scannable);
    }
}
