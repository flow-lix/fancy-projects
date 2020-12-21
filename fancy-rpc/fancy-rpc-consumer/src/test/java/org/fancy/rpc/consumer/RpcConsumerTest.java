package org.fancy.rpc.consumer;

import org.fancy.rpc.api.Operation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RpcConsumerTest {

    @Test
    public void testConsume() {
        Operation operation = RpcProxy.createProxy(Operation.class);
        assertEquals(3, operation.add(1, 2));
        assertEquals(-10, operation.sub(5, 15));
        assertEquals(18, operation.mult(3, 6));
        assertEquals(12, operation.div(36, 3));
    }

}
