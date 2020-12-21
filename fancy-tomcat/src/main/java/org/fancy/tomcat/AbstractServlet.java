package org.fancy.tomcat;

import org.fancy.tomcat.req.Request;
import org.fancy.tomcat.resp.Response;

import java.io.IOException;

public abstract class AbstractServlet {

    public static final String GET = "GET";
    public static final String POST = "POST";

    public void service(Request request, Response response) throws IOException {
        // service 方法决定调用Get还是Post
        if (GET.equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else if (POST.equalsIgnoreCase(request.getMethod())) {
            doPost(request, response);
        } else {
            throw new UnsupportedOperationException("Unknown http request method!");
        }
    }

    protected abstract void doGet(Request request, Response response) throws IOException;

    protected abstract void doPost(Request request, Response response) throws IOException ;

}
