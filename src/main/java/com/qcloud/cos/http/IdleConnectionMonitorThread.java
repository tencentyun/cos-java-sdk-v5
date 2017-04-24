package com.qcloud.cos.http;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpClientConnectionManager;

//用于监控空闲的连接池连接
public final class IdleConnectionMonitorThread extends Thread {
 private final HttpClientConnectionManager connMgr;
 private volatile boolean shutdown;

 private static final int MONITOR_INTERVAL_MS = 2000;
 private static final int IDLE_ALIVE_MS = 5000;

 public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
     super();
     this.connMgr = connMgr;
     this.shutdown = false;
 }

 @Override
 public void run() {
     try {
         while (!shutdown) {
             synchronized (this) {
                 wait(MONITOR_INTERVAL_MS);
                 // 关闭无效的连接
                 connMgr.closeExpiredConnections();
                 // 关闭空闲时间超过IDLE_ALIVE_MS的连接
                 connMgr.closeIdleConnections(IDLE_ALIVE_MS, TimeUnit.MILLISECONDS);
             }
         }
     } catch (InterruptedException e) {

     }
 }

 // 关闭后台连接
 public void shutdown() {
     shutdown = true;
     synchronized (this) {
         notifyAll();
     }
 }

}
