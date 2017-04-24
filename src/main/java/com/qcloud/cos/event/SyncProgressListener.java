package com.qcloud.cos.event;

/**
 * Abstract adapter class for a progress listener that is delivered with
 * progress event synchronously. 
 */
public abstract class SyncProgressListener
    implements ProgressListener, DeliveryMode {
    /**
     * Always returns true.
     */
    @Override public boolean isSyncCallSafe() { return true; }
}
