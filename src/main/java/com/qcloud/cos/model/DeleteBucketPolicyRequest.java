package com.qcloud.cos.model;

import java.io.Serializable;

public class DeleteBucketPolicyRequest extends GenericBucketRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new request object, ready to be executed to delete the policy for the specified
     * bucket.
     *
     * @param bucketName The name of the bucket
     */
    public DeleteBucketPolicyRequest(String bucketName) {
        super(bucketName);
    }

}
