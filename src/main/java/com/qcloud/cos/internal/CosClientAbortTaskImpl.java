package com.qcloud.cos.internal;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CosClientAbortTaskImpl implements CosClientAbortTask {
    private volatile boolean hasTaskExecuted;
    private HttpRequestBase currentHttpRequest;
    private final Thread thread;
    private volatile boolean isCancelled;

    private static final Logger log = LoggerFactory.getLogger(CosClientAbortTaskImpl.class);

    private final Object lock = new Object();

    public CosClientAbortTaskImpl(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        synchronized (this.lock) {
            if (isCancelled) {
                return;
            }
            hasTaskExecuted = true;
            if (!thread.isInterrupted()) {
                log.debug("request timeout and current thread will be interrupted");
                thread.interrupt();
            }
            if (!currentHttpRequest.isAborted()) {
                log.debug("request timeout and current http request will be aborted");
                currentHttpRequest.abort();
            }
        }
    }

    @Override
    public void setCurrentHttpRequest(HttpRequestBase newRequest) {
        this.currentHttpRequest = newRequest;
    }

    @Override
    public boolean hasClientExecutionAborted() {
        synchronized (this.lock) {
            return hasTaskExecuted;
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void cancel() {
        synchronized (this.lock) {
            isCancelled = true;
        }
    }
}
