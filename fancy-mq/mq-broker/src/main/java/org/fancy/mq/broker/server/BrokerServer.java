package org.fancy.mq.broker.server;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
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
import lombok.extern.slf4j.Slf4j;
import org.fancy.mq.broker.queue.BrokerQueue;
import org.fancy.mq.common.PushRequest;
import org.fancy.mq.core.req.PullRequest;
import org.fancy.mq.core.resp.PullResponse;
import org.fancy.mq.core.compressor.CompressorType;
import org.fancy.mq.core.protocol.ProtocolDecoder;
import org.fancy.mq.core.protocol.ProtocolEncoder;
import org.fancy.mq.core.protocol.RpcMessage;
import org.fancy.mq.core.serializer.SerializerType;

import static org.fancy.mq.common.MqConstant.PULL_REQUEST;
import static org.fancy.mq.common.MqConstant.PUSH_REQUEST;
import static org.fancy.mq.common.MqConstant.PUSH_RESPONSE;

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
                                    .addLast(new ProtocolDecoder())
                                    .addLast(new ProtocolEncoder())
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
    static class BrokerHandler extends SimpleChannelInboundHandler<RpcMessage> {
        /**
         * Broker read msg
         */
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RpcMessage msg) throws Exception {
            short msgType = msg.getMsgType();
            if (msgType == PUSH_REQUEST) {
                // 1.接收并存储生产者的数据；
                if (BrokerQueue.add((PushRequest) msg.getBody())) {
                    log.info("Produce new message!");
                } else {
                    log.error("Broker full!");
                }
            } else if (msgType == PULL_REQUEST) {
                log.info("consume message...");
                // 2.给消费者发送数据
                long offset = ((PullRequest) msg.getBody()).getOffset();
                PushRequest req = BrokerQueue.pull((int) offset);
                PullResponse resp = new PullResponse();
                resp.setName(req.getName());
                resp.setTimestamp(req.getTimestamp());

                RpcMessage response = new RpcMessage();
                response.setMsgType(PUSH_RESPONSE);
                response.setCompressor(CompressorType.NONE.getCode());
                response.setSerializer(SerializerType.JSON.getCode());
                response.setBody(resp);
                ctx.writeAndFlush(response) ;
            } else {
                log.warn("Unexpected msg: {}", msg);
            }
        }
    }
}
