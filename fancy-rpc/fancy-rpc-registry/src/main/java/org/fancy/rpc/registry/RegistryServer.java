package org.fancy.rpc.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import org.fancy.rpc.api.Operation;
import org.fancy.rpc.remoting.RpcProtocol;
import org.fancy.rpc.service.ArithmeticOperation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RegistryServer {

    private int port;

    public RegistryServer(int port) {
        this.port = port;
    }

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                            0, 4, 0, 4))
                                    .addLast(new LengthFieldPrepender(4))
                                    .addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE,
                                            ClassResolvers.cacheDisabled(null)))
                                    .addLast("encoder", new ObjectEncoder())
                                    .addLast("handler", new RegistryHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("Registry center started on :{}", port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Registry center was terminated unexpectedly!", e);
            Thread.currentThread().interrupt();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    private static class RegistryHandler extends ChannelInboundHandlerAdapter {

        private static Map<String, Object> providerMap = new HashMap<>();

        static {
            try {
                providerMap.putIfAbsent(Operation.class.getName(), ArithmeticOperation.class.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            RpcProtocol request = (RpcProtocol) msg;
            Object clazz = providerMap.get(request.getClassName());
            Method method = clazz.getClass().getMethod(request.getMethodName(), request.getParamsType());
            Object ret = method.invoke(clazz, request.getParams());
            ctx.writeAndFlush(ret);
            ctx.close();
        }
    }

}
