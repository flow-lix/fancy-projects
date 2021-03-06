/**
 * @Copyright (c) 2019, Denali System Co., Ltd. All Rights Reserved.
 * Website: www.denalisystem.com | Email: marketing@denalisystem.com
 */
package org.fancy.remoting;

import org.fancy.remoting.exception.RemotingException;

public interface ConnectionManger {

    void check(Connection connection) throws RemotingException;

    Connection get(String poolKey);

    void add(Connection connection, String poolKey);
}
