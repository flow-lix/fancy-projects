package org.fancy.remoting;

import org.fancy.remoting.command.RemotingCommand;

import java.util.concurrent.ExecutorService;

/**
 * 远程命令处理器
 * @param <T>
 */
public interface RemotingProcessor<T extends RemotingCommand> {

    /**
     * 处理远程命令
     * @param ctx
     * @param msg
     * @param executor
     */
    void process(RemotingContext ctx, T msg, ExecutorService executor);

    /**
     * 查询执行器
     * @return
     */
    ExecutorService getExecutor();

    /**
     * 设置执行器
     * @param executor
     */
    void setExecutor(ExecutorService executor);
}
