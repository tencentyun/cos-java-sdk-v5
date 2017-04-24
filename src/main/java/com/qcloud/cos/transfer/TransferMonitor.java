package com.qcloud.cos.transfer;

import java.util.concurrent.Future;

/**
 * Monitors long-running transfers.
 */
public interface TransferMonitor {

    /**
     * Returns a Future to wait on. Calling get() on this future will block
     * while the transfer progress is checked, but its return does not guarantee
     * the transfer is complete. Call isDone() to check for completion.  Repeated
     * calls to getFuture() can return different objects.
     */
    public Future<?> getFuture();

    /**
     * Returns whether the transfer is completed. A failure or cancellation
     * counts as completion as well; to gather any exceptions thrown, call
     * getFuture().get()
     */
    public boolean isDone();

}
