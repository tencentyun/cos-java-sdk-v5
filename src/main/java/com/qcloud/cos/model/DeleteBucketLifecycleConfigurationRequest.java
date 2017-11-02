package com.qcloud.cos.model;

import java.io.Serializable;

/**
 * Request object for the parameters to delete a bucket's lifecycle configuration.
 *
 */
public class DeleteBucketLifecycleConfigurationRequest extends GenericBucketRequest implements Serializable {

    /**
     * Creates a new request object, ready to be executed to delete the lifecycle
     * configuration for the specified bucket.
     * 
     * @param bucketName
     *            The name of the bucket whose lifecycle configuration is being
     *            deleted.
     */
    public DeleteBucketLifecycleConfigurationRequest(String bucketName) {
        super(bucketName);
    }
}