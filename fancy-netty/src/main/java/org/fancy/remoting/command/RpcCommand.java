package org.fancy.remoting.command;

import org.fancy.remoting.exception.SerializationException;
import org.fancy.remoting.config.configs.ConfigManager;
import org.fancy.remoting.config.switchs.ProtocolSwitch;
import org.fancy.remoting.protocol.ProtocolCode;

/**
 * Rpc 命令
 */
public abstract class RpcCommand implements RemotingCommand {

    private static final long serialVersionUID = -68623518572906844L;

    private int requestType;
    private CommandCode commandCode;
    private int id;

    private byte serializer = ConfigManager.serializer;
    private ProtocolSwitch pSwitch = new ProtocolSwitch();
    private short headerLength;
    private int contentLength;

    private byte[] header;
    private byte[] content;

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
        return null;
    }

    @Override
    public int getId() {
        return 0;
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

    @Override
    public void serialize() throws SerializationException {

    }

    @Override
    public void deserialize() throws SerializationException {

    }
}
