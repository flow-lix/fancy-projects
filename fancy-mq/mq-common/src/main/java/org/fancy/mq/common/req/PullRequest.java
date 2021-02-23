package org.fancy.mq.core.req;

import org.fancy.mq.core.AbstractMessage;

/**
 * @author lxiang
 */
public class PullRequest extends AbstractMessage {

    private long offset;

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
