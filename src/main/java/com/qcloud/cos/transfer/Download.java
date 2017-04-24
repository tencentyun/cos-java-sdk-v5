package com.qcloud.cos.transfer;

import java.io.IOException;

import com.qcloud.cos.exception.PauseException;
import com.qcloud.cos.model.ObjectMetadata;

/**
 * Represents an asynchronous download from Qcloud COS.
 * <p>
 * See {@link TransferManager} for more information about creating transfers.
 * </p>
 * 
 * @see TransferManager#download(com.qcloud.cos.model.GetObjectRequest,
 *      java.io.File)
 */
public interface Download extends Transfer {

    /**
     * Returns the ObjectMetadata for the object being downloaded.
     *
     * @return The ObjectMetadata for the object being downloaded.
     */
    public ObjectMetadata getObjectMetadata();

    /**
     * The name of the bucket where the object is being downloaded from.
     *
     * @return The name of the bucket where the object is being downloaded from.
     */
    public String getBucketName();

    /**
     * The key under which this object was stored in Qcloud COS.
     *
     * @return The key under which this object was stored in Qcloud COS.
     */
    public String getKey();

    /**
     * Cancels this download.
     *
     * @throws IOException
     */
    public void abort() throws IOException;

    /**
     * Pause the current download operation and returns the information that can
     * be used to resume the download at a later time.
     *
     * Resuming a download would not perform ETag check as range get is
     * performed for downloading the object's remaining contents.
     *
     * Resuming a download for an object encrypted using
     * {@link CryptoMode#StrictAuthenticatedEncryption} would result in
     * CosClientException as authenticity cannot be guaranteed for a range
     * get operation.
     *
     * @throws PauseException
     *             If any errors were encountered while trying to pause the
     *             download.
     */
    public PersistableDownload pause() throws PauseException;
}