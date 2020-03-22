package org.fancy.http.hutools;

import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.params.HttpParams;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
public class HutoolsTest {

    @Test
    public void test() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        String listContent = HttpUtil.get("https://www.oschina.net/news/widgets/_news_index_generic_list?p=3&type=ajax");

        List<String> titles = ReUtil.findAll("title=(.*?)>",  listContent, 1);
        for (String title : titles) {
            System.out.println(title);
        }

        System.out.println("Finished! cost: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Resource
    private RestTemplate restTemplate;

    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(0, 20,
            60000, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(), new NamedThreadFactory("multi-http-task", false),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Test
    void testWithoutPool() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < 7000; i++) {
            THREAD_POOL.submit(() -> {
                String ret = restTemplate.getForObject("https://www.zhihu.com/", String.class);
                LOGGER.info("Result size: " + ret.length());
            });
        }

        System.out.println("Finished testWithoutPool! cost: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HutoolsTest.class);

    @Test
    void testWithoutPool2() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < 7000; i++) {
            THREAD_POOL.submit(() -> {
                RestTemplate template = new RestTemplate();
                String ret = template.getForObject("https://www.zhihu.com/", String.class);
                LOGGER.info("Result size: " + ret.length());
            });
        }

        System.out.println("Finished testWithoutPool2! cost: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private HttpClient client;

    @Resource
    CloseableHttpAsyncClient asyncClient;

    @Test
    void testWithPool() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        CountDownLatch countDownLatch = new CountDownLatch(7000);

        for (int i = 0; i < 7000; i++) {
            THREAD_POOL.submit(() -> {
                asyncClient.execute(new HttpGet("https://www.zhihu.com/"), new FutureCallback<HttpResponse>() {
                            @Override
                            public void completed(HttpResponse result) {
                                LOGGER.info("Result size: " + result.getStatusLine().getStatusCode());
                                countDownLatch.countDown();
                            }

                            @Override
                            public void failed(Exception ex) {
                                LOGGER.error("failed!", ex);
                            }

                            @Override
                            public void cancelled() {
                                LOGGER.error("cancelled!");
                            }
                        });
            });
        }
        System.out.println("Finished! cost: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Finished! cost: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
