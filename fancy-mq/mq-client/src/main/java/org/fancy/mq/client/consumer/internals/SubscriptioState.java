package org.fancy.mq.client.consumer.internals;

import java.util.Collections;
import java.util.Set;

public final class SubscriptioState {

    private Set<String> topics;

    public boolean subscribe(Set<String> topicToSubscribe) {
        if (this.topics.equals(topicToSubscribe)) {
            return false;
        }
        this.topics = topicToSubscribe;
        return true;
    }

    public void unsubscribe() {
        this.topics = Collections.emptySet();
    }
}
