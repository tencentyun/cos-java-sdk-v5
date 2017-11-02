package com.qcloud.cos.model;

import java.io.Serializable;

/**
 * Request object for the parameters to get a bucket's cross origin configuration.
 *
 */
public class GetBucketCrossOriginConfigurationRequest extends GenericBucketRequest
        implements Serializable {

    /**
     * Creates a request object, ready to be executed to fetch the cross origin configuration of the
     * specified bucket.
     *
     * @param bucketName The name of the bucket whose cross origin configuration is being fetched.
     */
    public GetBucketCrossOriginConfigurationRequest(String bucketName) {
        super(bucketName);
    }

}
