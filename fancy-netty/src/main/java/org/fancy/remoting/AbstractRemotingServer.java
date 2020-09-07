/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.exception.LifeCycleException;

@Slf4j
public abstract class AbstractRemotingServer extends AbstractNettyConfigRemoting {

    private final String ip;
    private final int port;

    public AbstractRemotingServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void startup() throws LifeCycleException {
        super.startup();
        try {
            doInit();
            log.warn("完成服务初始化,端口：{}", port);
            if (doStart()) {
                log.warn("服务已启动, 端口: {}", port);
            } else {
                throw new LifeCycleException("服务启动失败！");
            }
        } catch (Exception e) {
            this.shutdown();
            throw new IllegalStateException("ERROR: Failed to start the Server!", e);
        }
    }

    @Override
    public void shutdown() throws LifeCycleException {
        super.shutdown();
        if (!doStop()) {
            throw new LifeCycleException("Stop failed!");
        }
    }

    protected abstract void doInit();

    protected abstract boolean doStart() throws InterruptedException ;

    protected abstract boolean doStop();

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
