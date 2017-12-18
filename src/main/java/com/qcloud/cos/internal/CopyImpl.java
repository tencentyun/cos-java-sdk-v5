package com.qcloud.cos.internal;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.qcloud.cos.event.ProgressListenerChain;
import com.qcloud.cos.event.TransferStateChangeListener;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.CopyResult;
import com.qcloud.cos.transfer.AbstractTransfer;
import com.qcloud.cos.transfer.Copy;
import com.qcloud.cos.transfer.TransferProgress;

/**
 * An implementation of the Copy Interface that helps in retrieving the result
 * of the copy operation.
 */
public class CopyImpl extends AbstractTransfer implements Copy {

    public CopyImpl(String description, TransferProgress transferProgress,
            ProgressListenerChain progressListenerChain,
            TransferStateChangeListener stateChangeListener) {
        super(description, transferProgress, progressListenerChain,
                stateChangeListener);
    }

    /**
     * Waits for this copy operation to complete and returns the result of the
     * operation. Be prepared to handle errors when calling this method. Any
     * errors that occurred during the asynchronous transfer will be re-thrown
     * through this method.
     *
     * @return The result of this transfer.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     *         
     * @throws CosServiceException If any errors occurred in COS while processing the request.
     * 
     * @throws InterruptedException
     *             If this thread is interrupted while waiting for the upload to
     *             complete.
     */
    public CopyResult waitForCopyResult() throws CosClientException,
            CosServiceException, InterruptedException {
        try {
            CopyResult result = null;
            while (!monitor.isDone() || result == null) {
                Future<?> f = monitor.getFuture();
                result = (CopyResult) f.get();
            }
            return result;
        } catch (ExecutionException e) {
            rethrowExecutionException(e);
            return null;
        }
    }
}
