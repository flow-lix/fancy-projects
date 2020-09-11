/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.serialization;

import org.fancy.remoting.InvokeContext;
import org.fancy.remoting.command.RpcCommand;
import org.fancy.remoting.exception.SerializationException;

public interface CustomSerializer {

    /**
     * 序列化请求、响应头
     */
    <T extends RpcCommand> boolean serializeHeader(T cmd) throws SerializationException;

    /**
     * 反序列化请求、响应头
     */
    <T extends RpcCommand> boolean deserializeHeader(T cmd, InvokeContext context) throws SerializationException;

    /**
     * 序列化请求、响应内容
     */
    <T extends RpcCommand> boolean serializeContent(T cmd) throws SerializationException;

    /**
     * 反序列化请求、响应内容
     */
    <T extends RpcCommand> boolean deserializeContent(T cmd, InvokeContext invokeContext) throws SerializationException;

}
