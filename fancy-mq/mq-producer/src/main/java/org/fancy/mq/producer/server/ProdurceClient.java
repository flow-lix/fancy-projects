package org.fancy.mq.producer.server;

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
import lombok.extern.slf4j.Slf4j;
import org.fancy.mq.core.PushRequest;
import org.fancy.mq.core.compressor.CompressorType;
import org.fancy.mq.core.protocol.ProtocolDecoder;
import org.fancy.mq.core.protocol.ProtocolEncoder;
import org.fancy.mq.core.protocol.RpcMessage;
import org.fancy.mq.core.serializer.SerializerType;
import static org.fancy.mq.core.MqConstant.PUSH_REQUEST;

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
                                    .addLast(new ProtocolDecoder())
                                    .addLast(new ProtocolEncoder())
                                    .addLast("producer-handler", new ProducerHandler());
                        }
                    });
            ChannelFuture future = client.connect(ip, port).sync();
            Channel channel = future.channel();

            RpcMessage response = new RpcMessage();
            response.setMsgType(PUSH_REQUEST);
            response.setCompressor(CompressorType.NONE.getCode());
            response.setSerializer(SerializerType.JSON.getCode());
            PushRequest push = new PushRequest();
            push.setName("dog");
            push.setTimestamp(System.currentTimeMillis());
            response.setBody(push);
            channel.writeAndFlush(response);

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
