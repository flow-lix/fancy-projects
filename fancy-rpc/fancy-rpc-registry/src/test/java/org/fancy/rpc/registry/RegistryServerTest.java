package org.fancy.rpc.registry;

import org.junit.Test;

public class RegistryServerTest {

    @Test
    public void testRegistry() {
        new RegistryServer(9000).start();
    }
}
