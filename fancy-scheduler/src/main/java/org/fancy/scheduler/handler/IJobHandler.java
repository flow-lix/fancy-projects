package org.fancy.scheduler.handler;

public abstract class IJobHandler {

    public abstract void execute(String param) throws Exception;

}
