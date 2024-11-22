package com.qcloud.cos.internal;

import org.apache.http.client.methods.HttpRequestBase;

import java.util.concurrent.ScheduledFuture;

public class CosClientAbortTaskMonitorImpl implements CosClientAbortTaskMonitor {
    private final CosClientAbortTask abortTask;
    private final ScheduledFuture<?> scheduledFuture;

    public CosClientAbortTaskMonitorImpl(final CosClientAbortTask task, final ScheduledFuture<?> future) {
        if (task == null) {
            throw new IllegalArgumentException("CosClientAbortTask should not be null");
        }
        abortTask = task;

        if (future == null) {
            throw new IllegalArgumentException("ScheduledFuture should not be null");
        }
        scheduledFuture = future;
    }

    @Override
    public void setCurrentHttpRequest(HttpRequestBase newRequest) {
        abortTask.setCurrentHttpRequest(newRequest);
    }

    @Override
    public boolean hasTimeoutExpired() {
        return abortTask.hasClientExecutionAborted();
    }

    @Override
    public boolean isEnabled() {
        return abortTask.isEnabled();
    }

    @Override
    public void cancelTask() {
        scheduledFuture.cancel(false);
        abortTask.cancel();
    }
}
