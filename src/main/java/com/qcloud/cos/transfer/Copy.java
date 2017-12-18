package com.qcloud.cos.transfer;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.CopyResult;

/**
 * Represents an asynchronous copy request from one COS location another.
 * <p>
 * See {@link TransferManager} for more information about creating transfers.
 * </p>
 *
 */
public interface Copy extends Transfer {

    /**
     * Waits for the copy request to complete and returns the result of this
     * request. Be prepared to handle errors when calling this method. Any
     * errors that occurred during the asynchronous transfer will be re-thrown
     * through this method.
     *
     * @return The result of this transfer.
     *
     * @throws CosClientException If any errors are encountered in the client while making the
     *         request or handling the response.
     * @throws CosServiceException If any errors occurred in while processing the request.
     * @throws InterruptedException
     *             If this thread is interrupted while waiting for the upload to
     *             complete.
     */
    public CopyResult waitForCopyResult() throws CosClientException,
            CosServiceException, InterruptedException;
}