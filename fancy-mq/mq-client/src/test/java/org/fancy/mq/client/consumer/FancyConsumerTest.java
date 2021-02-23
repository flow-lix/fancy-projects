package org.fancy.mq.client.consumer;

import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class FancyConsumerTest {

    private static volatile boolean running = true;

    @Test
    public void testConsumer() {
        // 1.
        Properties props = new Properties();
        props.put("bootstrap.server", "localhost:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("client_id", "fancy_1");
        props.put("group_id", "group_1");
        FancyConsumer consumer = new FancyConsumer(props);

        // 2.
        consumer.subscribe(Arrays.asList("topic"));

        try {
            while (running) {
                // 3
                ConsumerRecords consumerRecords = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord record : consumerRecords) {
                    System.out.println("Record topic = " + record.topic() +
                            ", partition = " + record.partition() +
                            ", offset = " + record.offset());
                    System.out.println("Record key = " + record.key() +
                            ", value = " + record.value());
                }
            }
        } catch (Exception e) {
            System.err.println("Occur exception: " + e);
        } finally {
            consumer.close();
        }
    }

}
