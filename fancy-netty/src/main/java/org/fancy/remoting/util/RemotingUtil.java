package org.fancy.remoting.util;

import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;

public class RemotingUtil {

    public static String parseLocalIP(Channel channel) {
        if (channel == null) {
            return StringUtils.EMPTY;
        }
        InetSocketAddress socketAddress = (InetSocketAddress) channel.localAddress();
        return socketAddress != null ? socketAddress.getAddress().getHostAddress() : StringUtils.EMPTY;
    }

    public static int parseLocalPort(Channel channel) {
        if (channel == null) {
            return -1;
        }
        InetSocketAddress socketAddress = (InetSocketAddress) channel.localAddress();
        return socketAddress != null ? socketAddress.getPort() : -1;
    }

    public static String parseRemoteIP(Channel channel) {
        if (channel == null) {
            return StringUtils.EMPTY;
        }
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        return socketAddress != null ? socketAddress.getAddress().getHostAddress() : StringUtils.EMPTY;
    }

    public static int parseRemotePort(Channel channel) {
        if (channel == null) {
            return -1;
        }
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        return socketAddress != null ? socketAddress.getPort() : -1;
    }

    public static String parseRemoteAddress(Channel channel) {
        if (null == channel) {
            return StringUtils.EMPTY;
        }
        String addr = channel.remoteAddress().toString();
        return addr.replace("/", "");
    }
}
