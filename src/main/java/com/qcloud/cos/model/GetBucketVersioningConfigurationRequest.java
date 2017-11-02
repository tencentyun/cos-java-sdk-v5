package com.qcloud.cos.model;

import java.io.Serializable;

/**
 * Request object for the parameters to get a bucket's versioning configuration.
 *
 */
public class GetBucketVersioningConfigurationRequest extends GenericBucketRequest implements Serializable {

    /**
     * Creates a request object, ready to be executed to fetch the versioning
     * configuration for the specified bucket.
     *
     * @param bucketName
     *            The name of the bucket whose versioning configuration is being
     *            fetched.
     */
    public GetBucketVersioningConfigurationRequest(String bucketName) {
        super(bucketName);
    }

}