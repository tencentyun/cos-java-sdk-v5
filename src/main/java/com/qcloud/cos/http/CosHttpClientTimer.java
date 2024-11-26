package com.qcloud.cos.http;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.internal.CosClientAbortTask;
import com.qcloud.cos.internal.CosClientAbortTaskMonitor;
import com.qcloud.cos.internal.DefaultClientAbortTaskImpl;
import com.qcloud.cos.internal.CosClientAbortTaskImpl;
import com.qcloud.cos.internal.CosClientAbortTaskMonitorImpl;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class CosHttpClientTimer {
    private volatile ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    private static ThreadFactory getThreadFactory(final String name) {
        return new ThreadFactory() {
            private int threadCount = 1;

            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                if (name != null) {
                    thread.setName(name + "-" + threadCount++);
                }
                thread.setPriority(Thread.MAX_PRIORITY);
                return thread;
            }
        };
    }

    private synchronized void initializeExecutor() {
        if (scheduledThreadPoolExecutor == null) {
            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5, getThreadFactory("COSClientRequestTimeOutThread"));
            try {
                executor.getClass().getMethod("setRemoveOnCancelPolicy", boolean.class).invoke(executor, Boolean.TRUE);
            } catch (NoSuchMethodException e) {
                throw new CosClientException("The request timeout feature is only available for Java 1.7 and above.");
            } catch (SecurityException e) {
                throw new CosClientException("The request timeout feature needs additional permissions to function.", e);
            } catch (Exception e) {
                throw new CosClientException(e);
            }

            executor.setKeepAliveTime(5, TimeUnit.SECONDS);
            executor.allowCoreThreadTimeOut(true);

            scheduledThreadPoolExecutor = executor;
        }
    }

    public CosClientAbortTaskMonitor startTimer(int requestTimeout) {
        if (requestTimeout <= 0) {
            return DefaultClientAbortTaskImpl.INSTANCE;
        } else if (scheduledThreadPoolExecutor == null) {
            initializeExecutor();
        }

        CosClientAbortTask task = new CosClientAbortTaskImpl(Thread.currentThread());
        ScheduledFuture<?> timerTaskFuture = scheduledThreadPoolExecutor.schedule(task, requestTimeout, TimeUnit.MILLISECONDS);
        return new CosClientAbortTaskMonitorImpl(task, timerTaskFuture);
    }

    public synchronized void shutdown() {
        if (scheduledThreadPoolExecutor != null) {
            scheduledThreadPoolExecutor.shutdown();
        }
    }
}
