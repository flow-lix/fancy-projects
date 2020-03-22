package org.fancy.http.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConnectionManager {

    private PoolingNHttpClientConnectionManager pcm;

    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(10);
//        connectionManager.setMaxTotal(httpPoolProperties.getMaxTotal());
//        connectionManager.setDefaultMaxPerRoute(httpPoolProperties.getDefaultMaxPerRoute());
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(6000) //服务器返回数据(response)的时间，超过抛出read timeout
                .setConnectTimeout(6000) //连接上服务器(握手成功)的时间，超出抛出connect timeout
                .setConnectionRequestTimeout(3000)//从连接池中获取连接的超时时间，超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .build();

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }

    /**------------------------------------------ HTTP 异步客户端 -----------------------------------**/

    @Bean
    public CloseableHttpAsyncClient createHttpAsyncClient() {
        CloseableHttpAsyncClient httpclient = null;
        try {
            IOReactorConfig ioReactorConfig = IOReactorConfig
                    .custom()
                    .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                    .setConnectTimeout(10000)
                    .setSoTimeout(30000)
                    .build();
            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);

            // default maxTotal 20; default maxPerRoute 2
            PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor);
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(8000)
                    .build();
            httpclient = HttpAsyncClients
                    .custom()
                    .setConnectionManager(connManager)
                    .setDefaultRequestConfig(RequestConfig.custom().setLocalAddress(null).build())
                    .build();

            httpclient.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return httpclient;
    }

    @Bean
    public RestTemplate setRestTemplate() {
        HttpComponentsClientHttpRequestFactory httpClientFactory = new HttpComponentsClientHttpRequestFactory();
        httpClientFactory.setConnectTimeout(6000);
        httpClientFactory.setReadTimeout(8000);
        httpClientFactory.setHttpClient(httpClient());

        return new RestTemplate(httpClientFactory);
    }

}
