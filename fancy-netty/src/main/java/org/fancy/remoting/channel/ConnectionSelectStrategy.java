/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.channel;

import org.fancy.remoting.Connection;

import java.util.List;

/**
 * 从连接池选择一个连接
 */
public interface ConnectionSelectStrategy {

    /**
     * 选择策略
     * @param connections source connections
     * @return selected connection
     */
    Connection select(List<Connection> connections);
}
