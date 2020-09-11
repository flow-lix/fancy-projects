package org.fancy.remoting;

/**
 * 监听回调响应
 */
public interface InvokeCallbackListener {

    /**
     * 响应到达
     * @param future
     */
    void onResponse(final InvokeFuture future);

    String getRemoteAddress();
}
