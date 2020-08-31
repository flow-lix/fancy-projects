/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.util;

import com.sun.corba.se.impl.orbutil.closure.Future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 记录运行方法是否被调用
 * @param <V>
 */
public class RunStateRecordFutureTask<V> extends FutureTask<V> {

    public RunStateRecordFutureTask(Callable<V> callable) {
        super(callable);
    }
}
