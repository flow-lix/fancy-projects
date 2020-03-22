package org.fancy.netty.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {

    private static final AtomicInteger id = new AtomicInteger(0);

    public static int nextId() {
        return id.incrementAndGet();
    }
}
