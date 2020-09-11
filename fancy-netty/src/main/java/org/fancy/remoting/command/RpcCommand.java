package org.fancy.remoting.command;

import org.fancy.remoting.InvokeContext;
import org.fancy.remoting.exception.DeserializationException;
import org.fancy.remoting.exception.SerializationException;
import org.fancy.remoting.config.configs.ConfigManager;
import org.fancy.remoting.config.switchs.ProtocolSwitch;
import org.fancy.remoting.protocol.ProtocolCode;

/**
 * Rpc 命令
 */
public abstract class RpcCommand implements RemotingCommand {

    private static final long serialVersionUID = -68623518572906844L;

    private int id;
    private int requestType;
    private CommandCode commandCode;

    private byte serializer = ConfigManager.serializer;
    private ProtocolSwitch pSwitch = new ProtocolSwitch();
    private short headerLength;
    private int contentLength;

    private byte[] clazz;
    private byte[] header;
    private byte[] content;

    /**
     * 每个rpc命令的调用上下文
     */
    private InvokeContext invokeContext;

    private ProtocolSwitch protocolSwitch = new ProtocolSwitch();

    public RpcCommand() {
    }

    public RpcCommand(int requestType, CommandCode commandCode) {
        this.requestType = requestType;
        this.commandCode = commandCode;
    }

    @Override
    public ProtocolCode getProtocolCode() {
        return null;
    }

    @Override
    public CommandCode getCommandCode() {
        return commandCode;
    }

    @Override
    public int getId() {
        return id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getRequestType() {
        return requestType;
    }

    public byte getSerializer() {
        return serializer;
    }

    public ProtocolSwitch getpSwitch() {
        return pSwitch;
    }

    public short getHeaderLength() {
        return headerLength;
    }

    public int getContentLength() {
        return contentLength;
    }

    public byte[] getHeader() {
        return header;
    }

    public byte[] getContent() {
        return content;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public void setCommandCode(CommandCode commandCode) {
        this.commandCode = commandCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSerializer(byte serializer) {
        this.serializer = serializer;
    }

    public void setpSwitch(ProtocolSwitch pSwitch) {
        this.pSwitch = pSwitch;
    }

    public void setHeaderLength(short headerLength) {
        this.headerLength = headerLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getClazz() {
        return clazz;
    }

    public void setClazz(byte[] clazz) {
        this.clazz = clazz;
    }

    @Override
    public void serialize() throws SerializationException {
        this.serializeClazz();
        this.serializeHeader();
        this.serializeContent();
    }

    @Override
    public void deserialize() throws DeserializationException {
        this.deserializeClass();
        this.deserializeHeader(null);
        this.deserializeContent(null);
    }

    protected void serializeClazz() throws SerializationException {

    }

    protected void serializeHeader() throws SerializationException {

    }

    protected void serializeContent() throws SerializationException {

    }

    protected void deserializeClass() throws DeserializationException {
    }

    protected void deserializeHeader(InvokeContext invokeContext) throws DeserializationException {

    }

    protected void deserializeContent(InvokeContext invokeContext) throws DeserializationException {

    }

    public void setProtocolSwitch(ProtocolSwitch protocolSwitch) {
        this.protocolSwitch = protocolSwitch;
    }

    public ProtocolSwitch getProtocolSwitch() {
        return protocolSwitch;
    }

    public void setInvokeContext(InvokeContext invokeContext) {
        this.invokeContext = invokeContext;
    }
}
