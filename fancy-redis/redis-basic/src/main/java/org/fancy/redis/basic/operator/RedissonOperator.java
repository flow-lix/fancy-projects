/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.redis.basic.operator;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedissonOperator {

    public static final Logger log = LoggerFactory.getLogger(RedissonOperator.class);

    @Resource
    private RedissonClient redissonClient;

    private static final String STR_KEY = "redis_str";

    public void simple() {
        RBucket<String> bucket = redissonClient.getBucket(STR_KEY);
        bucket.set("redis");
        log.info(bucket.get());

        bucket.isExists();

        bucket.delete();

        log.info(bucket.get());
    }

}
