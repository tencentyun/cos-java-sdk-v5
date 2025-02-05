package com.qcloud.cos.http;

import org.apache.http.conn.HttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class IdleConnectionMonitor extends Thread {
    private static final Logger log = LoggerFactory.getLogger(IdleConnectionMonitor.class);
    private static final Map<HttpClientConnectionManager, Long> connectionManagers = new ConcurrentHashMap<HttpClientConnectionManager, Long>();
    private static final int PERIOD_MILLISECONDS = 1000 * 60;
    private static volatile IdleConnectionMonitor instance;

    private volatile boolean shuttingDown;

    private IdleConnectionMonitor() {
        super("cos-java-sdk-http-connection-monitor");
        setDaemon(true);
    }

    private void markShuttingDown() {
        shuttingDown = true;
    }

    public static boolean registerConnectionManager(HttpClientConnectionManager connectionManager, long maxIdleInMs) {
        if (instance == null) {
            synchronized (IdleConnectionMonitor.class) {
                if (instance == null) {
                    instance = new IdleConnectionMonitor();
                    instance.start();
                }
            }
        }
        return connectionManagers.put(connectionManager, maxIdleInMs) == null;
    }

    public static boolean removeConnectionManager(HttpClientConnectionManager connectionManager) {
        boolean wasRemoved = connectionManagers.remove(connectionManager) != null;
        if (connectionManagers.isEmpty()) {
            shutdown();
        }
        return wasRemoved;
    }

    public static List<HttpClientConnectionManager> getRegisteredConnectionManagers() {
        return new ArrayList<HttpClientConnectionManager>(connectionManagers.keySet());
    }

    public static synchronized boolean shutdown() {
        if (instance != null) {
            instance.markShuttingDown();
            instance.interrupt();
            connectionManagers.clear();
            instance = null;
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while (!shuttingDown) {
            try {
                for (Map.Entry<HttpClientConnectionManager, Long> entry : connectionManagers.entrySet()) {
                    // When we release connections, the connection manager leaves them
                    // open so they can be reused.  We want to close out any idle
                    // connections so that they don't sit around in CLOSE_WAIT.
                    try {
                        entry.getKey().closeExpiredConnections();
                        entry.getKey().closeIdleConnections(entry.getValue(), TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.warn("Unable to closeExpiredConnections and closeIdleConnections, ", e);
                    }
                }

                Thread.sleep(PERIOD_MILLISECONDS);
            } catch (Throwable t) {
                log.error("error occurred when closeExpiredConnections and closeIdleConnections, err:", t);
            }
        }

        log.debug("Shutting down reaper thread.");
    }
}
