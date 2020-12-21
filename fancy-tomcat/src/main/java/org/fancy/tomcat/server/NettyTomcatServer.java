package org.fancy.tomcat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.fancy.tomcat.AbstractTomcatServer;
import org.fancy.tomcat.req.NettyRequest;
import org.fancy.tomcat.resp.NettyResponse;

import java.io.IOException;

@Slf4j
public class NettyTomcatServer extends AbstractTomcatServer {

    @Override
    protected void doStart(int port) throws IOException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // 无锁化串行执行
                            channel.pipeline()
                                    .addLast("decoder", new HttpRequestDecoder())
                                    .addLast("encoder", new HttpResponseEncoder())
                                    .addLast("handler", new ServerHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(port).sync();
            log.info("Netty tomcat server started on: {}", port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Netty tomcat serve was terminated unexpectedly!", e);
            Thread.currentThread().interrupt();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @ChannelHandler.Sharable
    static class ServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpRequest) {
                log.info("Receive new msg");
                HttpRequest httpRequest = (HttpRequest) msg;
                NettyRequest request = new NettyRequest(httpRequest);
                NettyResponse response = new NettyResponse(ctx);
                if (getServletMap().containsKey(request.getUri())) {
                    getServletMap().get(request.getUri()).service(request, response);
                } else {
                    response.write("404 - Not found");
                }
            } else {
                log.warn("Unknown msg: {}", msg.getClass());
            }
        }
    }
}
