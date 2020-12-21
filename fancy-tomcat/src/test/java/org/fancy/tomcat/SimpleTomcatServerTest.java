package org.fancy.tomcat;

import org.fancy.tomcat.exception.TomcatException;
import org.fancy.tomcat.server.NettyTomcatServer;
import org.fancy.tomcat.server.SimpleTomcatServer;
import org.junit.Test;

public class SimpleTomcatServerTest {

    @Test
    public void testServer() throws TomcatException {
        new SimpleTomcatServer().start();
    }

    @Test
    public void testNettyServer() throws TomcatException {
        new NettyTomcatServer().start();
    }

}
