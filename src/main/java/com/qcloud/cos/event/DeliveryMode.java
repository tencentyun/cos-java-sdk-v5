package com.qcloud.cos.event;

/**
 * Used to indicate whether it is safe to deliver progress events to the
 * listener synchronously. In general, a progress listener should never block,
 * which is a necessary condition for the safety for synchronous delivery.
 * 
 * @see SyncProgressListener
 */
public interface DeliveryMode {
    /**
     * Returns true if it is safe to make a synchronous callback to the
     * implementing listener without the risk of incurring undue latency; false
     * otherwise.
     */
    public boolean isSyncCallSafe();

    /**
     * Provides convenient method to check if a listener is safe to be invoked
     * synchronously.
     */
    public static class Check {
        public static boolean isSyncCallSafe(ProgressListener listener) {
            if (listener instanceof DeliveryMode) {
                DeliveryMode mode = (DeliveryMode) listener;
                return mode.isSyncCallSafe();
            }
            return listener == null;
        }
    }
}
