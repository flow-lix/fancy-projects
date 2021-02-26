package org.fancy.mq.client.common.header;

public interface Headers extends Iterable<Header> {


    Headers add(Header header);

    Headers add(String key, byte[] values);
}
