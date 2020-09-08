/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.rpc;

public class RpcServer {

    private NettyRemotingServer remotingServer;

    public RpcServer(String ip, int port) {
        this.remotingServer = new NettyRemotingServer(ip, port);
    }

    public void start() {
        this.remotingServer.startup();
    }
}
