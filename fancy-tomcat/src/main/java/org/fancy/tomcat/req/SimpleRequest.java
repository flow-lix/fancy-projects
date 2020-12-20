package org.fancy.tomcat.req;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class SimpleRequest implements Request {

    private String method;
    private String url;

    public SimpleRequest(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = reader.readLine();
            this.method = line.split(" ")[0];
            line = reader.readLine();
            this.url = line.split(" ")[1];
        } catch (IOException e) {
            log.error("解析HTTP请求头失败！");
        }
    }

    @Override
    public String getUri() {
        return url;
    }

    @Override
    public String getMethod() {
        return method;
    }
}
