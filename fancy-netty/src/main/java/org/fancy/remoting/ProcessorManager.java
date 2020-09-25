package org.fancy.remoting;

import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.command.CommandCode;
import org.fancy.remoting.command.RemotingCommand;
import org.fancy.remoting.protocol.RpcCommandCode;
import org.fancy.remoting.protocol.RpcRequestProcessor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

@Slf4j
public class ProcessorManager {

    private ConcurrentMap<CommandCode, RemotingProcessor<RemotingCommand>> cmd2processor = new ConcurrentHashMap<>();

    private RemotingProcessor<RemotingCommand> defaultProcessor;

    /**
     * 没有设置执行器就用这个
     */
    private ExecutorService defaultExecuitor;

    public void registerProcessor(RpcCommandCode rpcRequest, RpcRequestProcessor rpcRequestProcessor) {

    }

    public RemotingProcessor<RemotingCommand> getProcessor(CommandCode commandCode) {
        RemotingProcessor<RemotingCommand> processor = this.cmd2processor.get(commandCode);
        if (processor != null) {
            return processor;
        }
        return this.defaultProcessor;
    }

    public ExecutorService getDefaultExecutor() {
        return defaultExecuitor;
    }
}
