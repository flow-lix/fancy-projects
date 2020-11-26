/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.redis.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.BatchResult;
import org.redisson.api.RBatch;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {

    @Resource
    private RedissonClient redissonClient;

    @Test
    public void testString() {
        RBucket<String> bucket = redissonClient.getBucket("strBasic");
        bucket.set("test-basic");
        Assert.assertEquals("test-basic", bucket.get());
    }

    private static final String STR_KEY = "redis_str";

    @Test
    public void testSimple() {
        RBucket<String> bucket = redissonClient.getBucket(STR_KEY);
        bucket.set("redis");
        Assert.assertEquals("redis", bucket.get());
        log.info("val: {}", bucket.get());
        Assert.assertTrue(bucket.isExists());
        Assert.assertTrue(bucket.delete());
        Assert.assertNull(bucket.get());
        Assert.assertFalse(bucket.isExists());
    }

    /**
     * 对多个字符串进行批量操作，降低网络开销
     */
    @Test
    public void testBatch() {
        String key1 = "key1", key2 = "key2", key3 = "key3";
        RBatch rBatch = redissonClient.createBatch();
        rBatch.getBucket(key1).setAsync("val1");
        rBatch.getBucket(key2).setAsync("val2");
        rBatch.getBucket(key3).setAsync("val3");
        rBatch.execute();

        rBatch = redissonClient.createBatch();
        rBatch.getBucket(key1).getAsync();
        rBatch.getBucket(key2).getAsync();
        rBatch.getBucket(key3).getAsync();
        BatchResult<?> result = rBatch.execute();
        result.getResponses().forEach(e -> log.info(e.toString()));
    }

    /**
     * 字符串过期
     */
    @Test
    public void testExpire() throws InterruptedException {
        final String key = "key";
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set("redis", 3000, TimeUnit.MILLISECONDS);

        Assert.assertNotNull(bucket.get());
        Thread.sleep(3000);
        Assert.assertNull(bucket.get());
    }

    @Test
    public void testQueue() {
        RBlockingQueue<String> queue = redissonClient.getBlockingQueue("redis_queue");
        RList<String> rList = redissonClient.getList("redis_list");
        // 队列
    }
}