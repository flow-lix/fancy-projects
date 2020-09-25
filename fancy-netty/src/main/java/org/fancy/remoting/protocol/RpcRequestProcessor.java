/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.protocol;

import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.AbstractRemotingProcessor;
import org.fancy.remoting.InvokeContext;
import org.fancy.remoting.RemotingContext;

import java.util.concurrent.ExecutorService;

@Slf4j
public class RpcRequestProcessor extends AbstractRemotingProcessor<RpcRequestCommand> {

    @Override
    protected void doProcess(RemotingContext ctx, RpcRequestCommand cmd) {
        long currentTimestamp = System.currentTimeMillis();
        preProcessRemotingContext(ctx, cmd, currentTimestamp);
        if (!deserializeRequestCommand(ctx, cmd)) {
            return;
        }
        dispatchToUserProcessor(ctx, cmd);
    }

    private boolean deserializeRequestCommand(RemotingContext ctx, RpcRequestCommand cmd) {
        boolean result;
        try {
            cmd.deserialize();
            result = true;
        } catch (Exception e) {
            log.error("DeserializationException occurred when process in RpcRequestProcessor, id={}",
                    cmd.getId());
            result = false;
        }
        return result;
    }

    private void preProcessRemotingContext(RemotingContext ctx, RpcRequestCommand cmd, long currentTimestamp) {
        ctx.setArriveTimestamp(cmd.getArriveTime());
        ctx.setTimeout(cmd.getTimeout());
        ctx.setRpcCommandType(cmd.getRequestType());
        ctx.getInvokeContext().putIfAbsent(InvokeContext.PROCESS_WAIT_TIME,
                currentTimestamp - cmd.getArriveTime());
    }

    private void dispatchToUserProcessor(RemotingContext ctx, RpcRequestCommand cmd) {

    }

    @Override
    public ExecutorService getExecutor() {
        return null;
    }

    @Override
    public void setExecutor(ExecutorService executor) {

    }
}
