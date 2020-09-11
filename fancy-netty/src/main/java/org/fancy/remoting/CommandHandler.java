package org.fancy.remoting;

/**
 * 命令处理接口
 */
public interface CommandHandler {

    /**
     * 处理命令
     * @param remotingContext
     * @param msg
     */
    void handlerCommand(RemotingContext remotingContext, Object msg);
}
