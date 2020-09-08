/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.util;

import org.fancy.remoting.Url;
import org.fancy.remoting.config.configs.Configs;

import java.lang.ref.SoftReference;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class AddressParserUtil {

    private static final ConcurrentMap<String, SoftReference<Url>> PARSED_URLS = new ConcurrentHashMap<>();

    private AddressParserUtil() {
    }

    public static Url parse(String addr) {
        Objects.requireNonNull(addr);
        Url parsedUrl = tryGet(addr);
        if (parsedUrl != null) {
            return parsedUrl;
        }
        int prev = addr.indexOf(':');
        if (prev == -1) {
            throw new IllegalArgumentException("非法的路径，必须有一个':'");
        }
        String ip = addr.substring(0, prev);
        prev++;
        int last = addr.indexOf('?', prev);
        int port;
        if (last == -1) {
            port = Integer.parseInt(addr.substring(prev));
            parsedUrl = new Url(addr, ip, port);
        } else {
            port = Integer.parseInt(addr.substring(prev, last));
            String propStr = addr.substring(last + 1);
            Properties properties = new Properties();
            String[] props = propStr.split("\\?");
            for (String s : props) {
                String[] prop = s.split("=");
                if (prop.length == 2) {
                    properties.setProperty(prop[0], prop[1]);
                }
            }
            parsedUrl = new Url(addr, ip, port, properties);
        }
        int connNum = Configs.DEFAULT_CONN_NUM_PER_URL;
        parsedUrl.setConnNum(connNum);

        PARSED_URLS.putIfAbsent(addr, new SoftReference<>(parsedUrl));
        return parsedUrl;
    }

    private static Url tryGet(String addr) {
        SoftReference<Url> softReference = PARSED_URLS.get(addr);
        return softReference == null ? null : softReference.get();
    }
}
