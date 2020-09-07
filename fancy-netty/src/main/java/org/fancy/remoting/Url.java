/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import java.util.Properties;

/**
 * URL definition
 */
public class Url {

    private String originUrl;
    private String ip;
    private int port;

    private String uniqueKey;

    private int connectTimeout;

    private byte protocol;

    private int connNum;

    private boolean connWarmup;

    private Properties properties;

    public Url(String addr, String ip, int port) {
        this.originUrl = addr;
        this.ip = ip;
        this.port = port;
        this.uniqueKey = ip + ":" + port;
    }

    public Url(String addr, String ip, int port, Properties properties) {
        this(addr, ip, port);
        this.properties = properties;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public byte getProtocol() {
        return protocol;
    }

    public void setProtocol(byte protocol) {
        this.protocol = protocol;
    }

    public int getConnNum() {
        return connNum;
    }

    public void setConnNum(int connNum) {
        this.connNum = connNum;
    }

    public boolean isConnWarmup() {
        return connWarmup;
    }

    public void setConnWarmup(boolean connWarmup) {
        this.connWarmup = connWarmup;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Url{" +
                "originUrl='" + originUrl + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", uniqueKey='" + uniqueKey + '\'' +
                ", connectTimeout=" + connectTimeout +
                ", protocol=" + protocol +
                ", connNum=" + connNum +
                ", connWarmup=" + connWarmup +
                ", properties=" + properties +
                '}';
    }
}
