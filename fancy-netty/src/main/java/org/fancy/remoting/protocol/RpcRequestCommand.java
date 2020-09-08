/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.protocol;

import org.fancy.remoting.RpcCommandType;
import org.fancy.remoting.command.CommandCode;
import org.fancy.remoting.command.req.RequestCommand;
import org.fancy.remoting.config.configs.Configs;
import org.fancy.remoting.exception.SerializationException;
import org.fancy.remoting.serialization.CustomSerializer;
import org.fancy.remoting.serialization.SerializerManager;

import java.io.UnsupportedEncodingException;

public class RpcRequestCommand extends RequestCommand {

    private Object requestObject;
    private String requestClass;

    private CustomSerializer customSerializer;
    private Object requestHeader;

    private transient long arriveTime = -1;

    public RpcRequestCommand(Object requestObject) {
        super(RpcCommandCode.RPC_REQUEST);
        this.requestObject = requestObject;
    }

    public CustomSerializer getCustomSerializer() {
        if (this.customSerializer != null) {
            return this.customSerializer;
        }
        return customSerializer;
    }

    @Override
    protected void serializeClazz() throws SerializationException {
        if (this.requestClass != null) {
            try {
                this.requestClass.getBytes(Configs.DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                throw new SerializationException("不支持的字符集: " + Configs.DEFAULT_CHARSET, e);
            }
        }
    }

    @Override
    protected void serializeHeader() throws SerializationException{
        if (this.getCustomSerializer() != null) {
            try {
                this.getCustomSerializer().serializeHeader(this);
            } catch (SerializationException e) {
                throw e;
            } catch (Exception e) {
                throw new SerializationException("Rpc请求序列化请求头时异常！", e);
            }
        }
    }

    @Override
    protected void serializeContent() throws SerializationException {
        if (this.requestObject != null) {
            try {
                if (this.getCustomSerializer() != null &&
                        this.getCustomSerializer().serializeContent(this)) {
                    return;
                }
                this.setContent(SerializerManager.getSerializer(getSerializer()).serialize(this.requestObject));
            } catch (SerializationException e) {
                throw e;
            } catch (Exception e) {
                throw new SerializationException("Rpc请求序列化请求头时异常！", e);
            }
        }
    }

    public void setRequestClass(String requestClass) {
        this.requestClass = requestClass;
    }

    public Object getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(Object requestObject) {
        this.requestObject = requestObject;
    }

    public String getRequestClass() {
        return requestClass;
    }

    public Object getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(Object requestHeader) {
        this.requestHeader = requestHeader;
    }

    public long getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(long arriveTime) {
        this.arriveTime = arriveTime;
    }
}
