package com.qcloud.cos.internal;

import org.apache.http.client.methods.HttpRequestBase;

public class DefaultClientAbortTaskImpl implements CosClientAbortTaskMonitor {
    public static final DefaultClientAbortTaskImpl INSTANCE = new DefaultClientAbortTaskImpl();

    private DefaultClientAbortTaskImpl() {}

    @Override
    public void setCurrentHttpRequest(HttpRequestBase newRequest) {
    }

    @Override
    public boolean hasTimeoutExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void cancelTask() {
    }
}
