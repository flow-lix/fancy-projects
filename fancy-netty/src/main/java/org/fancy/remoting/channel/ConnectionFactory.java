package org.fancy.remoting.channel;

import org.fancy.remoting.Connection;
import org.fancy.remoting.Url;
import org.fancy.remoting.exception.RemotingException;

public interface ConnectionFactory {

    /**
     * 创建一个连接
     * @param url
     * @return
     */
    Connection createConnection(Url url) throws RemotingException;
}
