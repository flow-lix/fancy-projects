package org.fancy.remoting;

public abstract class AbstractNettyRemoting {


    abstract void doInit();

    abstract boolean doStart() throws InterruptedException;
}
