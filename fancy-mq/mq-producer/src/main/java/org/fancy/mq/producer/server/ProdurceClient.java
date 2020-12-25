/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.mq.producer.server;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.fancy.mq.common.PushRequest;

import java.nio.charset.StandardCharsets;

import static org.fancy.mq.common.MqConstant.DELIMITER;
import static org.fancy.mq.common.MqConstant.PUSH_REQUEST;

@Slf4j
public class ProdurceClient {

    public static void main(String[] args) {
        ProdurceClient client = new ProdurceClient();
        client.produceMsg("localhost", 8000);
    }

    private void produceMsg(String ip, int port) {
        Bootstrap client = new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            client.group(worker)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new StringEncoder(StandardCharsets.UTF_8))
                                    .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(DELIMITER.getBytes())))
                                    .addLast(new StringDecoder(StandardCharsets.UTF_8))
                                    .addLast("producer-handler", new ProducerHandler());
                        }
                    });
            ChannelFuture future = client.connect(ip, port).sync();
            Channel channel = future.channel();

            PushRequest push = new PushRequest();
            push.setCode(PUSH_REQUEST);
            push.setName("dog");
            push.setTimestamp(System.currentTimeMillis());
            channel.writeAndFlush(JSON.toJSONString(push) + DELIMITER);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.info("Produce server was terminated unexpected!", e);
            Thread.currentThread().interrupt();
        } finally {
            worker.shutdownGracefully();
        }
    }

    static class ProducerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
        }
    }
}
