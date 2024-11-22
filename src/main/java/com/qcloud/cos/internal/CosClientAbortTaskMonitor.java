package com.qcloud.cos.internal;

import org.apache.http.client.methods.HttpRequestBase;

public interface CosClientAbortTaskMonitor {
    void setCurrentHttpRequest(HttpRequestBase newRequest);

    boolean hasTimeoutExpired();

    boolean isEnabled();

    void cancelTask();
}
