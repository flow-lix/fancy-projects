package org.fancy.tomcat.http;

import org.fancy.tomcat.AbstractServlet;
import org.fancy.tomcat.req.Request;
import org.fancy.tomcat.resp.Response;

import java.io.IOException;

public class SecondServlet extends AbstractServlet {

    @Override
    protected void doGet(Request request, Response response) throws IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(Request request, Response response) throws IOException {
        response.write("This is second servlet.");
    }
}
