package org.fancy.remoting;

import lombok.extern.slf4j.Slf4j;
import org.fancy.remoting.channel.ConnectionSelectStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class ConnectionPool implements Scannable {

    private CopyOnWriteArrayList<Connection> connections;
    private ConnectionSelectStrategy selectStrategy;

    private volatile boolean asyncCreationDone;
    private volatile long lastAccessTimestamp;

    public ConnectionPool(ConnectionSelectStrategy selectStrategy) {
        this.selectStrategy = selectStrategy;
        this.connections = new CopyOnWriteArrayList<>();
    }

    @Override
    public void scan() {
        if (connections != null && !connections.isEmpty()) {
            for (Connection conn : connections) {
                if (!conn.isFine()) {
                    log.warn("从连接池中移除连接 - {}:{}", conn.getRemoteIp(), conn.getRemotePort());
                    conn.close();
                    removeAndTryClose(conn);
                }
            }
        }
    }

    private void removeAndTryClose(Connection conn) {
    }

    public Connection get() {
        markAccess();
        if (connections != null && !connections.isEmpty()) {
            List<Connection> snapshot = new ArrayList<>(connections);
            return selectStrategy.select(snapshot);
        }
        return null;
    }

    public void makeAsyncCreateStart() {
        asyncCreationDone = false;
    }

    public void markAsyncCreateDone() {
        asyncCreationDone = true;
    }

    public void add(Connection connection) {
        markAccess();
        if (null == connection) {
            return;
        }
        if (connections.addIfAbsent(connection)) {
            connection.increaseRef();
        }
    }

    private void markAccess() {
        this.lastAccessTimestamp = System.currentTimeMillis();
    }

    public int size() {
        return connections.size();
    }
}
