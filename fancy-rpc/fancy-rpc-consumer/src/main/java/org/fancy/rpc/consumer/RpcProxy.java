package org.fancy.rpc.consumer;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import org.fancy.rpc.remoting.RpcProtocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcProxy {

    public static <T> T createProxy(Class<T> clazz) {
        Class<?>[] classes = clazz.isInterface() ? new Class[]{clazz} : clazz.getInterfaces();
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), classes,
                new RpcInvocationHandler());
    }

    @Slf4j
    static class RpcInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(proxy, args);
            }
            return rpcInvoke(method, args);
        }

        private Object rpcInvoke(Method method, Object[] args) {
            RpcProtocol protocol = new RpcProtocol();
            protocol.setClassName(method.getDeclaringClass().getName());
            protocol.setMethodName(method.getName());
            protocol.setParamsType(method.getParameterTypes());
            protocol.setParams(args);
            ConsumerHandler consumerHandler = new ConsumerHandler();
            Bootstrap bootstrap = new Bootstrap();
            EventLoopGroup worker = new NioEventLoopGroup();
            try {
                bootstrap.group(worker)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline()
                                        .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                                0, 4, 0, 4))
                                        .addLast(new LengthFieldPrepender(4))
                                        .addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE,
                                                ClassResolvers.cacheDisabled(null)))
                                        .addLast("encoder", new ObjectEncoder())
                                        .addLast("handler", consumerHandler);
                            }
                        });
                ChannelFuture future = bootstrap.connect("localhost", 9000).sync();
                log.info("Consumer proxy connected at: {}", 9000);
                future.channel().writeAndFlush(protocol).sync();
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("Consumer proxy was terminated unexpected!", e);
                Thread.currentThread().interrupt();
            } finally {
                worker.shutdownGracefully();
            }
            return consumerHandler.getResponse();
        }
    }
}
