package org.fancy.netty;

public abstract class AbstractNettyRemoting extends AbstractLifeCycle {


    abstract void doInit();

    abstract boolean doStart() throws InterruptedException;
}
