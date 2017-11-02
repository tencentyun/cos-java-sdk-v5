package com.qcloud.cos.model;

import java.io.Serializable;

/**
 * Result object for Head Bucket request.
 */
public class HeadBucketResult implements Serializable {

    private String bucketRegion;

    /**
     * Returns the region where the bucket is located.
     */
    public String getBucketRegion() {
        return bucketRegion;
    }

    public void setBucketRegion(String bucketRegion) {
        this.bucketRegion = bucketRegion;
    }

    public HeadBucketResult withBucketRegion(String bucketRegion) {
        setBucketRegion(bucketRegion);
        return this;
    }
}

