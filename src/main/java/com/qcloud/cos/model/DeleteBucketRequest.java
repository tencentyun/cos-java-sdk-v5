package com.qcloud.cos.model;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

public class DeleteBucketRequest extends CosServiceRequest implements Serializable {
    /**
     * The name of the Qcloud COS bucket to delete.
     */
    private String bucketName;

    /**
     * Constructs a new {@link DeleteBucketRequest}, ready to be executed to delete the specified
     * bucket.
     * 
     * @param bucketName The name of the Qcloud COS bucket to delete.
     */
    public DeleteBucketRequest(String bucketName) {
        setBucketName(bucketName);
    }

    /**
     * Sets the name of the Qcloud COS bucket to delete.
     * 
     * @param bucketName The name of the Qcloud COS bucket to delete.
     * 
     * @see DeleteBucketRequest#getBucketName()
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Gets the name of the Qcloud COS bucket to delete.
     * 
     * @return The name of the Qcloud COS bucket to delete.
     * 
     * @see DeleteBucketRequest#setBucketName(String)
     */
    public String getBucketName() {
        return bucketName;
    }
}
