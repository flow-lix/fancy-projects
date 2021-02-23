package org.fancy.mq.client.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.fancy.mq.client.consumer.internals.SubscriptioState;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

/**
 * Kafka消费者
 *
 *
 * 1. 应用程序可以通过kakfaConsumer订阅topic， 并从订阅的topic中拉取消息进行消费，
 * 在使用Kafka消费者消费消息之前，需要了解消费者与消费组之间的关系，不然就无法理解如何使用消费者。
 *
 * 2. 消费者大于topic分区时会出现有的消费者消费不到消息的情况。
 *
 * 3. 同一个消费组的消费者只能消费不同的消息，不同消费组的消费者能够消费相同的消息。
 *
 * 4. 消息投递方式： 点对点（发送到消费组的消息只能由其中的一个消费组消费）； 发布订阅（发送到多个消费组的消息可以被多个消费者消费）
 *
 *  * 分区分配策略
 *
 *  消费者的消费过程：
 *  1. 配置并创建消费者实例；
 *  2. 订阅topic；
 *  3. 拉取消息并消费；
 *  4. 提交消费位移；
 *  5. 关闭消费者实例
 *
 *  */
@Slf4j
public class FancyConsumer implements Consumer {

    private final Properties props;

    private SubscriptioState subscription;

    public FancyConsumer(Properties props) {
        this.props = props;
    }

    @Override
    public void subscribe(Collection<String> topics) {
        if (topics == null) {
            throw new IllegalArgumentException("Topic collection cannot be null.");
        }
        if (topics.isEmpty()) {
            this.unsubscribe();
        } else {
            log.info("Subscribe topics: {}", StringUtils.join(topics, ","));
            this.subscription.subscribe(new HashSet<>(topics));
        }
    }

    @Override
    public void unsubscribe() {
        this.subscription.unsubscribe();
    }
}
