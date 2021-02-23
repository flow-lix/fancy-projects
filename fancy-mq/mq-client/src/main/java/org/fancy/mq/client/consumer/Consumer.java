package org.fancy.mq.client.consumer;

import java.util.Collection;

public interface Consumer {

    void subscribe(Collection<String> topics);

    void unsubscribe();
}
