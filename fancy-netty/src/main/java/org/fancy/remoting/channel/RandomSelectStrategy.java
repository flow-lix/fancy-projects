/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting.channel;

import org.fancy.remoting.Connection;

import java.util.List;
import java.util.Random;

/**
 * 随机选择策略
 */
public class RandomSelectStrategy implements ConnectionSelectStrategy {

    private final Random random = new Random();

    @Override
    public Connection select(List<Connection> connections) {
        return connections.get(random.nextInt(connections.size()));
    }
}
