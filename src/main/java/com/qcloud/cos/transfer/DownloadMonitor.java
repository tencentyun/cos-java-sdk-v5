package com.qcloud.cos.transfer;

import java.util.concurrent.Future;

public class DownloadMonitor implements TransferMonitor {

    private final Future<?> future;
    private final DownloadImpl download;

    public DownloadMonitor(DownloadImpl download, Future<?> future) {
        this.download = download;
        this.future = future;
    }

    @Override
    public Future<?> getFuture() {
        return future;
    }

    @Override
    public boolean isDone() {
        return download.isDone();
    }
}