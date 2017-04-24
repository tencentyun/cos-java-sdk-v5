package com.qcloud.cos.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * This class wraps a ProgressListener object, and manages all its callback
 * execution. Callbacks are executed sequentially in a separate single thread.
 */
public class ProgressListenerCallbackExecutor {

    /** The wrapped ProgressListener **/
    private final ProgressListener listener;
    
    /** A single thread pool for executing all ProgressListener callbacks. **/
    private static ExecutorService executor;
    
    public ProgressListenerCallbackExecutor(ProgressListener listener) {
        this.listener = listener;
    }
    
    public void progressChanged(final ProgressEvent progressEvent) {
        if (listener == null) return;
        
        synchronized (ProgressListenerCallbackExecutor.class) {
            if (executor == null) {
                executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setName("java-sdk-progress-listener-callback-thread");
                        t.setDaemon(true);
                        return t;
                    }
                });
            }
            executor.submit(new Runnable() {
                
                @Override
                public void run() {
                    listener.progressChanged(progressEvent);
                }
            });
        }
        
    }

    /**
     * Returns a new ProgressListenerCallbackExecutor instance that wraps the
     * specified ProgressListener if it is not null, otherwise directly returns
     * null.
     */
    public static ProgressListenerCallbackExecutor wrapListener(ProgressListener listener) {
        return listener == null ?
                null : new ProgressListenerCallbackExecutor(listener);
    }
}
