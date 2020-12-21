package org.fancy.tomcat.resp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.IOException;

public class NettyResponse implements Response {

    private ChannelHandlerContext ctx;

    public NettyResponse(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void write(String content) throws IOException {
        try {
            if (content == null || content.isEmpty()) {
                return;
            }
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
            response.headers().set("Content-type", "text/html;");
            ctx.writeAndFlush(response);
        } finally {
            ctx.flush();
            ctx.close();
        }
    }
}
