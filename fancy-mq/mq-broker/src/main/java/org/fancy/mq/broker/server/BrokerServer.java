/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.mq.broker.server;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.fancy.mq.broker.queue.BrokerQueue;
import org.fancy.mq.common.AbstractMessage;
import org.fancy.mq.common.PushRequest;
import org.fancy.mq.common.req.PullRequest;
import org.fancy.mq.common.resp.PushResponse;

import java.nio.charset.StandardCharsets;

import static org.fancy.mq.common.MqConstant.DELIMITER;

/**
 * Broker服务端
 */
@Slf4j
public class BrokerServer {

    public static void main(String[] args) {
        BrokerServer server = new BrokerServer();
        server.init(8000);
    }

    private void init(int port) {
        ServerBootstrap server = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            server.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new StringEncoder(StandardCharsets.UTF_8))
                                    .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("$_".getBytes())))
                                    // 给定长度里找不到分隔符就抛出异常，防止异常码流缺失分隔符导致内存溢出;  分隔符缓存对象
                                    .addLast(new StringDecoder(StandardCharsets.UTF_8))
                                    .addLast("handler", new BrokerHandler());
                        }
                    });
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.info("Broker server was terminated unexpected!");
            Thread.currentThread().interrupt();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    /**
     * Broker handler
     */
    @ChannelHandler.Sharable
    static class BrokerHandler extends SimpleChannelInboundHandler<String> {
        /**
         * Broker read msg
         */
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            AbstractMessage req = JSON.parseObject(msg, AbstractMessage.class);
            if (req instanceof PushRequest) {
                // 1.接收并存储生产者的数据；
                if (BrokerQueue.offer((PushRequest) req)) {
                    ctx.writeAndFlush(JSON.toJSONString(PushResponse.SUCCESS) + DELIMITER);
                } else {
                    ctx.writeAndFlush(JSON.toJSONString(PushResponse.failOf("Broker full!")) + DELIMITER);
                }
            } else if (req instanceof PullRequest) {
                // 2.给消费者发送数据
                long offset = ((PullRequest) req).getOffset();
                PushRequest message = BrokerQueue.pull((int) offset);
                ctx.writeAndFlush(JSON.toJSONString(message) + DELIMITER) ;
            } else {
                log.warn("Unexpected msg: {}", msg);
            }
        }
    }
}
