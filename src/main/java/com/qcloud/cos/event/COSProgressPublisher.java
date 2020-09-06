/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 
 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos.event;

import java.util.concurrent.Future;

import org.slf4j.LoggerFactory;

import com.qcloud.cos.transfer.PersistableTransfer;

/**
 * Used to publish transfer events.
 */
public class COSProgressPublisher extends SDKProgressPublisher {
    /**
     * Used to deliver a persistable transfer to the given cos listener.
     * 
     * @param listener only listener of type {@link COSProgressListener} will be
     * notified.
     * 
     * @return the future of a submitted task; or null if the delivery is
     * synchronous with no future task involved.  Note a listener should never
     * block, and therefore returning null is the typical case.
     */
    public static Future<?> publishTransferPersistable(
            final ProgressListener listener,
            final PersistableTransfer persistableTransfer) {
        if (persistableTransfer == null 
        || !(listener instanceof COSProgressListener))
            return null;
        final COSProgressListener coslistener = (COSProgressListener)listener;
        return deliverEvent(coslistener, persistableTransfer);
    }

    private static Future<?> deliverEvent(final COSProgressListener listener,
            final PersistableTransfer persistableTransfer) {
        if (SYNC) { // forces all callbacks to be made synchronously
            return quietlyCallListener(listener, persistableTransfer);
        }
        if (!ASYNC) { // forces all callbacks to be made asynchronously
            if (listener instanceof DeliveryMode) {
                DeliveryMode mode = (DeliveryMode) listener;
                if (mode.isSyncCallSafe()) {
                    // Safe to call the listener directly
                    return quietlyCallListener(listener, persistableTransfer);
                }
            }
        }
        // Not safe to call the listener directly; so submit an async task.
        // This is unfortunate as the listener should never block in the first
        // place, but such task submission is necessary to remain backward
        // compatible.
        return setLatestFutureTask(getExecutorService().submit(new Runnable() {
            @Override public void run() {
                listener.onPersistableTransfer(persistableTransfer);
            }
        }));
    }

    private static Future<?> quietlyCallListener(
            final COSProgressListener listener,
            final PersistableTransfer persistableTransfer) {
        try {
            listener.onPersistableTransfer(persistableTransfer);
        } catch(Throwable t) {
            // That's right, we need to suppress all errors so as to be on par
            // with the async mode where all failures will be ignored.
            LoggerFactory.getLogger(COSProgressPublisher.class)
                .debug("Failure from the event listener", t);
        }
        return null;
    }
}
