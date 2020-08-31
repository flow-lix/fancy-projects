/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.ConfigurableRemoting;
import org.fancy.remoting.codec.CodecFactory;

import java.util.Objects;

@Slf4j
public class DefaultConnectionFactory implements ConnectionFactory {

    private final ConfigurableRemoting remoting;
    private final CodecFactory codec;
    private final ChannelHandler heartbeatHandler;
    private final ChannelHandler handler;

    protected Bootstrap bootstrap;

    public DefaultConnectionFactory(ConfigurableRemoting remoting, CodecFactory codec,
                                    ChannelHandler heartbeatHandler, ChannelHandler handler) {
        Objects.requireNonNull(codec);
        Objects.requireNonNull(handler);
        this.remoting = remoting;
        this.codec = codec;
        this.heartbeatHandler = heartbeatHandler;
        this.handler = handler;
    }


}
