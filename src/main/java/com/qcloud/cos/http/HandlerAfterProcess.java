package com.qcloud.cos.http;

public interface HandlerAfterProcess {
    public void handle(int status, long time_cost);
}