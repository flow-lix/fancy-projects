package org.fancy.remoting.protocol;

import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.CommandFactory;
import org.fancy.remoting.CommandHandler;
import org.fancy.remoting.ProcessorManager;
import org.fancy.remoting.RemotingContext;
import org.fancy.remoting.RemotingProcessor;
import org.fancy.remoting.command.RemotingCommand;
import org.fancy.remoting.command.RpcCommand;

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
    public void handleCommand(RemotingContext remotingContext, Object msg) {

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void process(RemotingContext ctx, Object msg) {
        final RpcCommand cmd = (RpcCommand) msg;
        final RemotingProcessor<RemotingCommand> processor = processorManager.getProcessor(cmd.getCommandCode());
        processor.process(ctx, cmd, processorManager.getDefaultExecutor());
    }

}
