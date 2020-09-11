/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.protocol;

import org.fancy.remoting.InvokeContext;
import org.fancy.remoting.command.resp.ResponseCommand;
import org.fancy.remoting.config.configs.Configs;
import org.fancy.remoting.exception.DeserializationException;
import org.fancy.remoting.exception.SerializationException;
import org.fancy.remoting.serialization.CustomSerializer;
import org.fancy.remoting.serialization.SerializerManager;

import java.io.UnsupportedEncodingException;

public class RpcResponseCommand extends ResponseCommand {

    private Object responseHeader;
    private Object responseObject;

    private String responseClass;

    private String errorMsg;

    private CustomSerializer customSerializer;

    @Override
    protected void serializeClazz() throws SerializationException {
        super.serializeClazz();
    }

    @Override
    protected void serializeHeader() throws SerializationException {
        if (this.getCustomSerializer() != null) {
            try {
                this.getCustomSerializer().serializeHeader(this);
            } catch (SerializationException e) {
                throw new SerializationException("序列化响应头时异常！", e);
            }
        }
    }

    @Override
    protected void serializeContent() throws SerializationException {
        if (this.getResponseObject() != null) {
            try {
                if (this.getCustomSerializer() != null && this.getCustomSerializer().serializeContent(this)) {
                    return;
                }
                this.setContent(SerializerManager.getSerializer(this.getSerializer()).serialize(this.responseObject));
            } catch (Exception e) {
                throw new SerializationException("序列化响应体时异常！", e);
            }
        }
    }

    @Override
    protected void deserializeClass() throws DeserializationException {
        if (this.getClass() != null && this.getResponseClass() == null) {
            try {
                this.setResponseClass(new String(this.getClazz(), Configs.DEFAULT_CHARSET));
            } catch (UnsupportedEncodingException e) {
                throw new DeserializationException("不支持的编码：" + Configs.DEFAULT_CHARSET);
            }
        }
    }

    @Override
    protected void deserializeHeader(InvokeContext invokeContext) throws DeserializationException {
        if (this.getHeader() != null && this.getResponseHeader() == null) {
            if (this.getCustomSerializer() != null) {
                try {
                    this.getCustomSerializer().deserializeHeader(this, invokeContext);
                } catch (Exception e) {
                    throw new DeserializationException("发序列化头时异常！", e);
                }
            }
        }
    }

    @Override
    protected void deserializeContent(InvokeContext invokeContext) throws DeserializationException {
        if (this.getResponseObject() == null) {
            try {
                if (this.getCustomSerializer() != null &&
                        this.getCustomSerializer().deserializeContent(this, invokeContext)) {
                    return;
                }
                if (this.getContent() != null) {
                    this.setResponseObject(SerializerManager.getSerializer(this.getSerializer())
                            .deserialize(this.getContent(), this.responseClass));
                }
            } catch (Exception e) {
                throw new DeserializationException("反序列化响应体异常！", e);
            }
        }
    }

    public Object getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(Object responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public String getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(String responseClass) {
        this.responseClass = responseClass;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public CustomSerializer getCustomSerializer() {
        if (customSerializer != null) {
            return customSerializer;
        }
        return customSerializer;
    }
}
