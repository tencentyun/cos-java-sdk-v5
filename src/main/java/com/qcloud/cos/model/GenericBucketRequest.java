package com.qcloud.cos.model;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * Generic request container for web service requests on buckets.
 */
public class GenericBucketRequest extends CosServiceRequest implements Serializable {

    private String bucketName;

    /**
     * Create a generic request for a bucket operation.
     * 
     * @param bucketName
     *            The name of the target bucket.
     */
    public GenericBucketRequest(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * @deprecated Use getBucketName() instead.
     */
    @Deprecated
    public String getBucket() {
        return bucketName;
    }

    /**
     * Returns the name of the target bucket.
     * 
     * @return The name of the target bucket
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Sets the name of the target bucket.
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Sets the name of the target bucket, and returns this updated request
     * object so that additional method calls can be chained together.
     * 
     * @return This updated request object so that additional method calls can
     *         be chained together.
     */
    public GenericBucketRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

}