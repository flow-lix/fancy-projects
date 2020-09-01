/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.protocol;

import org.fancy.remoting.BizContext;
import org.fancy.remoting.LifeCycle;
import org.fancy.remoting.exception.RemotingException;

public interface UserProcessor<T> extends LifeCycle {


    /**
     * 处理同步请求
     */
    Object handlerSyncRequest(BizContext bizContext, T request) throws RemotingException;

    /**
     * 处理异步请求
     */
    void handlerAsyncRequest();

}
