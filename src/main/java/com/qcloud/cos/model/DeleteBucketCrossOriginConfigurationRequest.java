package com.qcloud.cos.model;

import java.io.Serializable;

/**
 * Request object for the parameters to delete a bucket's cross origin configuration.
 *
 */
public class DeleteBucketCrossOriginConfigurationRequest extends GenericBucketRequest implements Serializable {

    /**
     * Creates a new request object, ready to be executed to delete the cross origin
     * configuration for the specified bucket.
     *
     * @param bucketName
     *            The name of the bucket whose cross origin configuration is being
     *            deleted.
     */
    public DeleteBucketCrossOriginConfigurationRequest(String bucketName) {
        super(bucketName);
    }
}