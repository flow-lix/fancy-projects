package org.fancy.remoting.channel;

import org.fancy.remoting.Connection;
import org.fancy.remoting.Url;
import org.fancy.remoting.exception.RemotingException;
import org.fancy.remoting.handler.ConnectionEventHandler;

public interface ConnectionFactory {

    /**
     * 初始化连接工厂
     * @param connectionEventHandler
     */
    void init(final ConnectionEventHandler connectionEventHandler);

    /**
     * 创建一个连接
     * @param url
     * @return
     */
    Connection createConnection(Url url) throws RemotingException;
}
