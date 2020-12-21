package org.fancy.tomcat;

import lombok.extern.slf4j.Slf4j;
import org.fancy.tomcat.exception.TomcatException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public abstract class AbstractTomcatServer {

    private int port;
    private static Map<String, AbstractServlet> servletMap = new HashMap<>();

    private Properties webPros = new Properties();

    private void init() throws TomcatException {
        this.port = 8085;
        try {
            webPros.load(this.getClass().getResourceAsStream("/web.properties"));
            for (Object k : webPros.keySet()) {
                String key = (String) k;
                if (key.endsWith(".url")) {
                    String className = (String) webPros.get(key.replaceAll(".url", ".className"));
                    // 单例模式
                    AbstractServlet servlet = (AbstractServlet) Class.forName(className).newInstance();
                    servletMap.putIfAbsent(webPros.getProperty(key), servlet);
                }
            }
        }  catch (Exception e) {
            log.error("Tomcat server init error!", e);
            throw new TomcatException(e);
        }
    }

    public void start() throws TomcatException {
        init();
        try {
            doStart(port);
        } catch (Exception e) {
            log.error("Tomcat server run failed!", e);
        }
    }

    protected abstract void doStart(int port) throws IOException;

    protected static Map<String, AbstractServlet> getServletMap() {
        return servletMap;
    }
}
