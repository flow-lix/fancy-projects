package org.fancy.remoting;

import io.netty.channel.ChannelHandlerContext;
import org.fancy.remoting.command.RemotingCommand;

import java.util.concurrent.ExecutorService;

public abstract class AbstractRemotingProcessor<T extends RemotingCommand> implements RemotingProcessor<T> {

    private ExecutorService executor;
    private CommandFactory commandFactory;

    public AbstractRemotingProcessor() {
    }

    public AbstractRemotingProcessor(ExecutorService executor) {
        this.executor = executor;
    }

    public AbstractRemotingProcessor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public AbstractRemotingProcessor(ExecutorService executor, CommandFactory commandFactory) {
        this.executor = executor;
        this.commandFactory = commandFactory;
    }

    @Override
    public void process(RemotingContext ctx, T msg, ExecutorService executor) {
        ProcessTask task = new ProcessTask(ctx, msg);

    }

    class ProcessTask implements Runnable {
        private RemotingContext ctx;
        private T msg;

        public ProcessTask(RemotingContext ctx, T msg) {
            this.ctx = ctx;
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                AbstractRemotingProcessor.this.doProcess(ctx, msg);
            } catch (Exception e) {

            }
        }
    }

    protected abstract void doProcess(RemotingContext ctx, T msg);
}
