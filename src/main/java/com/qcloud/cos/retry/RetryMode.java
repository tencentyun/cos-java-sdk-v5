package com.qcloud.cos.retry;

public enum RetryMode {
    LEGACY("legacy"),
    STANDARD("standard");

    private final String name;
    RetryMode(String name) {
        this.name = name;
    }
}
