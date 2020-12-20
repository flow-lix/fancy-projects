package org.fancy.tomcat.resp;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleResponse implements Response {

    private OutputStream out;

    public SimpleResponse(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(String content) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("HTTP/1.1 200 OK \n")
                .append("Content-Type: text/html; \n")
                .append("\r\n")
                .append(content);
        this.out.write(builder.toString().getBytes());
    }
}
