package com.qcloud.cos.model;

import java.io.Serializable;

public class GetBucketPolicyRequest extends GenericBucketRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new request object, ready to be executed to get  the policy for the specified
     * bucket.
     *
     * @param bucketName The name of the bucket 
     */
    public GetBucketPolicyRequest(String bucketName) {
        super(bucketName);
    }

}
