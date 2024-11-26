package com.qcloud.cos.internal;

import org.apache.http.client.methods.HttpRequestBase;

public interface CosClientAbortTask extends Runnable {
    void setCurrentHttpRequest(HttpRequestBase newRequest);

    boolean hasClientExecutionAborted();

    boolean isEnabled();

    void cancel();
}
