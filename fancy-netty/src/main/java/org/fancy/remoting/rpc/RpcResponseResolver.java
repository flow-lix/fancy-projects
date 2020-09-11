package org.fancy.remoting.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fancy.remoting.ResponseStatus;
import org.fancy.remoting.command.resp.ResponseCommand;
import org.fancy.remoting.exception.CodecException;
import org.fancy.remoting.exception.DeserializationException;
import org.fancy.remoting.exception.InvokeException;
import org.fancy.remoting.exception.InvokeTimeoutException;
import org.fancy.remoting.exception.RemotingException;
import org.fancy.remoting.exception.SerializationException;
import org.fancy.remoting.protocol.RpcResponseCommand;
import org.fancy.remoting.rpc.exception.ConnectionClosedException;
import org.fancy.remoting.rpc.exception.InvokeSendFailedException;
import org.fancy.remoting.rpc.exception.InvokeServerBusyException;
import org.fancy.remoting.rpc.exception.InvokeServerException;

@Slf4j
public class RpcResponseResolver {

    public static Object resolveResponseObject(ResponseCommand responseCommand, String address)
            throws RemotingException {
        preProcess(responseCommand, address);
        if (responseCommand.getResponseStatus() == ResponseStatus.SUCCESS) {
            return toResponseObject(responseCommand);
        } else {
            String msg = String.format("RPC调用异常: %s, 地址: %s, ID: %s", responseCommand.getResponseStatus(),
                    address, responseCommand.getId());
            log.warn(msg);
            throw new InvokeException(msg);
        }
    }

    private static Object toResponseObject(ResponseCommand responseCommand) throws CodecException {
        RpcResponseCommand command = (RpcResponseCommand) responseCommand;
        command.deserialize();
        return command.getResponseObject();
    }

    private static void preProcess(ResponseCommand responseCommand, String address) throws RemotingException {
        RemotingException e = null;
        String msg = null;
        if (responseCommand == null) {
            msg = String.format("Rpc invocation timeout[responseCommand null]! the address is %s",
                    address);
            e = new InvokeTimeoutException(msg);
        } else {
            switch (responseCommand.getResponseStatus()) {
                case TIMEOUT:
                    msg = String.format(
                            "Rpc invocation timeout[responseCommand TIMEOUT]! the address is %s", address);
                    e = new InvokeTimeoutException(msg);
                    break;
                case CLIENT_SEND_ERROR:
                    msg = String.format("Rpc invocation send failed! the address is %s", address);
                    e = new InvokeSendFailedException(msg, responseCommand.getCause());
                    break;
                case CONNECTION_CLOSED:
                    msg = String.format("Connection closed! the address is %s", address);
                    e = new ConnectionClosedException(msg);
                    break;
                case SERVER_THREADPOOL_BUSY:
                    msg = String.format("Server thread pool busy! the address is %s, id=%s", address,
                            responseCommand.getId());
                    e = new InvokeServerBusyException(msg);
                    break;
                case CODEC_EXCEPTION:
                    msg = String.format("Codec exception! the address is %s, id=%s", address,
                            responseCommand.getId());
                    e = new CodecException(msg);
                    break;
                case SERVER_SERIAL_EXCEPTION:
                    msg = String
                            .format(
                                    "Server serialize response exception! the address is %s, id=%s, serverSide=true",
                                    address, responseCommand.getId());
                    e = new SerializationException(msg);
                    break;
                case SERVER_DESERIAL_EXCEPTION:
                    msg = String
                            .format(
                                    "Server deserialize request exception! the address is %s, id=%s, serverSide=true",
                                    address, responseCommand.getId());
                    e = new DeserializationException(msg);
                    break;
                case SERVER_EXCEPTION:
                    msg = String.format(
                            "Server exception! Please check the server log, the address is %s, id=%s",
                            address, responseCommand.getId());
                    e = new InvokeServerException(msg);
                    break;
                default:
                    break;
            }
        }
        if (StringUtils.isNotBlank(msg)) {
            log.warn(msg);
        }
        if (e != null) {
            throw e;
        }
    }
}
