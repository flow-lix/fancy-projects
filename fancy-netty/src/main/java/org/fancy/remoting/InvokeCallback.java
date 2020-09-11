/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

/**
 * 远程调用回调
 */
public interface InvokeCallback {

    void onResponse(final Object result);

    void onException(final Throwable e);



}
