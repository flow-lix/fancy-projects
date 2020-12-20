package org.fancy.tomcat.req;

import io.netty.handler.codec.http.HttpRequest;

public class NettyRequest implements Request {

    private HttpRequest request;

    public NettyRequest(HttpRequest httpRequest) {
        this.request = httpRequest;
    }

    @Override
    public String getUri() {
        return request.uri();
    }

    @Override
    public String getMethod() {
        return request.method().name();
    }
}
