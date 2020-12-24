/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.mq.consumer.server;

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
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.fancy.mq.common.Message;
import org.fancy.mq.common.req.PullRequest;
import org.fancy.mq.common.resp.PullResponse;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.fancy.mq.common.MqConstant.DELIMITER;

@Slf4j
public class ConsumerServer {

    public static void main(String[] args) throws InterruptedException {
        ConsumerServer server = new ConsumerServer();
        System.out.println("Received :" + server.consumeMessage("localhost", 8000));
    }

    private Message consumeMessage(String ip, int port) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        PullResponse response = new PullResponse();
        Channel channel = getChannel(ip, port, response);
        if (channel != null) {
            PullRequest request = new PullRequest();
            request.setOffset(1);
            channel.writeAndFlush(JSON.toJSONString(request) + DELIMITER);
            if (!latch.await(3000, TimeUnit.MILLISECONDS)) {
                log.warn("No available msg!");
            }
        }
        return response.getMessage();
    }

    private Channel getChannel(String ip, int port, PullResponse pullResponse) {
        Bootstrap client = new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        Channel channel = null;
        ConsumerHandler consumerHandler = new ConsumerHandler(pullResponse);
        try {
            client.group(worker)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new StringEncoder())
                                    .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("$_".getBytes())))
                                    .addLast(new ObjectDecoder(1024, ClassResolvers.cacheDisabled(null)))
                                    .addLast("producer-handler", consumerHandler);
                        }
                    });
            ChannelFuture future = client.connect(ip, port).sync();
            channel = future.channel();
        } catch (InterruptedException e) {
            log.info("Produce server was terminated unexpected!", e);
            Thread.currentThread().interrupt();
        } finally {
            worker.shutdownGracefully();
        }
        return channel;
    }

    class ConsumerHandler extends ChannelInboundHandlerAdapter {

        private PullResponse pullResponse;

        ConsumerHandler(PullResponse pullResponse) {
            this.pullResponse = pullResponse;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof PullResponse) {
                setPullResponse((PullResponse) msg);
                pullResponse.countDown();
            } else {
                log.warn("Unexpected msg: {}", msg);
            }
        }

        public PullResponse getPullResponse() {
            return pullResponse;
        }

        public void setPullResponse(PullResponse pullResponse) {
            this.pullResponse = pullResponse;
        }
    }
}
