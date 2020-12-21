package org.fancy.tomcat.resp;

import java.io.IOException;

public interface Response {

    void write(String content) throws IOException;

}
