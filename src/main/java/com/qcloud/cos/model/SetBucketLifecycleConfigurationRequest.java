package com.qcloud.cos.model;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

/*
 * Contains options for setting the lifecycle configuration for a bucket.
 */

public class SetBucketLifecycleConfigurationRequest extends CosServiceRequest
        implements Serializable {
    /**
     * The bucket whose lifecycle configuration is being set.
     */
    private String bucketName;

    /**
     * The new lifecycle configuration for the specified bucket.
     */
    private BucketLifecycleConfiguration lifecycleConfiguration;

    public SetBucketLifecycleConfigurationRequest(String bucketName,
            BucketLifecycleConfiguration lifecycleConfiguration) {
        super();
        this.bucketName = bucketName;
        this.lifecycleConfiguration = lifecycleConfiguration;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public SetBucketLifecycleConfigurationRequest withBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public BucketLifecycleConfiguration getLifecycleConfiguration() {
        return lifecycleConfiguration;
    }

    public void setLifecycleConfiguration(BucketLifecycleConfiguration lifecycleConfiguration) {
        this.lifecycleConfiguration = lifecycleConfiguration;
    }

    public SetBucketLifecycleConfigurationRequest withLifecycleConfiguration(
            BucketLifecycleConfiguration lifecycleConfiguration) {
        this.lifecycleConfiguration = lifecycleConfiguration;
        return this;
    }

}
