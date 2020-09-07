package org.fancy.remoting;

import org.fancy.remoting.common.ConnectEventProcessor;
import org.fancy.remoting.common.RequestBody;
import org.fancy.remoting.exception.RemotingException;
import org.fancy.remoting.rpc.RpcClient;
import org.fancy.remoting.rpc.RpcServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BasicUsageTest {

    private RpcServer rpcServer;
    private RpcClient rpcClient;

    ConnectEventProcessor serverConnProcessor = new ConnectEventProcessor();

    public static final String addr = "localhost:8000";

    @Before
    public void setup() {
        rpcServer = new RpcServer();
        rpcServer.startup();

        rpcClient = new RpcClient();
        rpcClient.startup();
    }

    @Test
    public void testOneWay() throws InterruptedException {
        RequestBody req = new RequestBody(2, "Hello world oneway!");
        for (int i = 0; i < 10; i++) {
            try {
                rpcClient.oneway(addr, req);
            } catch (RemotingException e) {
                e.printStackTrace();
                Assert.fail(e.getMessage());
            }
        }
        Assert.assertTrue(serverConnProcessor.isConnected());
        Assert.assertEquals(1, serverConnProcessor.getConnectTimes());
        Assert.assertEquals(10, serverConnProcessor.getInvokeTime());
    }
}
