package org.fancy.remoting.protocol;

import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.CommandFactory;
import org.fancy.remoting.CommandHandler;
import org.fancy.remoting.ProcessorManager;
import org.fancy.remoting.RemotingContext;

@Slf4j
@ChannelHandler.Sharable
public class RpcCommandHandler implements CommandHandler {

    private ProcessorManager processorManager;
    private CommandFactory commandFactory;

    public RpcCommandHandler(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
        this.processorManager = new ProcessorManager();
        this.processorManager.registerProcessor(RpcCommandCode.RPC_REQUEST, new RpcRequestProcessor());
    }

    @Override
    public void handlerCommand(RemotingContext remotingContext, Object msg) {

    }
}
