package org.fancy.tomcat.server;

import lombok.extern.slf4j.Slf4j;
import org.fancy.tomcat.AbstractTomcatServer;
import org.fancy.tomcat.req.SimpleRequest;
import org.fancy.tomcat.resp.SimpleResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SimpleTomcatServer extends AbstractTomcatServer {

    private ServerSocket serverSocket;
    private volatile boolean running;

    @Override
    protected void doStart(int port) throws IOException {
        running = true;
        this.serverSocket = new ServerSocket(port);
        log.info("Tomcat server start on: {}", port);
        while (running) {
            Socket client = serverSocket.accept();
            process(client);
        }
        running = false;
    }

    private void process(Socket client) throws IOException {
        InputStream in = client.getInputStream();
        SimpleRequest request = new SimpleRequest(in);

        OutputStream out = client.getOutputStream();
        SimpleResponse response = new SimpleResponse(out);

        if (getServletMap().containsKey(request.getUri())) {
            getServletMap().get(request.getUri()).service(request, response);
        } else {
            response.write("404 - Not Found!");
        }
        out.flush();
        out.close();
        in.close();
        client.close();
    }
}
