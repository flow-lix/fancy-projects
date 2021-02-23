package org.fancy.mq.consumer.server;

import io.netty.bootstrap.Bootstrap;
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
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.fancy.mq.core.req.PullRequest;
import org.fancy.mq.core.resp.PullResponse;
import org.fancy.mq.consumer.AsyncResponse;
import org.fancy.mq.core.compressor.CompressorType;
import org.fancy.mq.core.protocol.ProtocolDecoder;
import org.fancy.mq.core.protocol.ProtocolEncoder;
import org.fancy.mq.core.protocol.RpcMessage;
import org.fancy.mq.core.serializer.SerializerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.fancy.mq.core.MqConstant.PULL_REQUEST;

@Slf4j
public class ConsumerServer {

    public static void main(String[] args) throws InterruptedException {
        ConsumerServer server = new ConsumerServer();
        System.out.println("Received :" + server.consumeMessage("localhost", 8000));
    }

    private PullResponse consumeMessage(String ip, int port) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AsyncResponse response = new AsyncResponse(latch);
        Channel channel = getChannel(ip, port, response);
        if (channel != null) {
            PullRequest request = new PullRequest();
            request.setOffset(1);
            RpcMessage req = new RpcMessage();
            req.setMsgType(PULL_REQUEST);
            req.setCompressor(CompressorType.NONE.getCode());
            req.setSerializer(SerializerType.JSON.getCode());
            req.setBody(request);
            channel.writeAndFlush(req) ;
            if (!latch.await(5000, TimeUnit.MILLISECONDS)) {
                log.warn("No available msg!");
            }
        }
        return response.getResponse();
    }

    private Channel getChannel(String ip, int port, AsyncResponse pullResponse) {
        Bootstrap client = new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup(new DefaultThreadFactory("Consumer", true));
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
                                    .addLast(new ProtocolDecoder())
                                    .addLast(new ProtocolEncoder())
                                    .addLast("producer-handler", consumerHandler);
                        }
                    });
            ChannelFuture future = client.connect(ip, port).sync();
            channel = future.channel();
        } catch (InterruptedException e) {
            log.info("Produce server was terminated unexpected!", e);
            Thread.currentThread().interrupt();
        } finally {
//            worker.shutdownGracefully();
        }
        return channel;
    }

    class ConsumerHandler extends ChannelInboundHandlerAdapter {

        private AsyncResponse response;

        ConsumerHandler(AsyncResponse pullResponse) {
            this.response = pullResponse;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof RpcMessage) {
                response.setResponse((PullResponse) ((RpcMessage) msg).getBody());
                response.countDown();
            } else {
                log.warn("Unexpected msg: {}", msg);
            }
        }
    }
}
