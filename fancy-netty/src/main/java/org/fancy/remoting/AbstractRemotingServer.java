/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

public abstract class AbstractRemotingServer extends AbstractNettyConfigRemoting {

    private final String ip;
    private final int port;

    public AbstractRemotingServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
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
