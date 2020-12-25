package org.fancy.mq.common.req;

import org.fancy.mq.common.AbstractMessage;

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
