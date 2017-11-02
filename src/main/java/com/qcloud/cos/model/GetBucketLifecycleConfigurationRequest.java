package com.qcloud.cos.model;

import java.io.Serializable;

/**
 * Request object for the parameters to get a bucket's lifecycle configuration.
 *
 */
public class GetBucketLifecycleConfigurationRequest extends GenericBucketRequest
        implements Serializable {

    /**
     * Creates a request object, ready to be executed to fetch the lifecycle configuration for the
     * specified bucket.
     *
     * @param bucketName The name of the bucket whose lifecycle configuration is being fetched.
     */
    public GetBucketLifecycleConfigurationRequest(String bucketName) {
        super(bucketName);
    }

}
