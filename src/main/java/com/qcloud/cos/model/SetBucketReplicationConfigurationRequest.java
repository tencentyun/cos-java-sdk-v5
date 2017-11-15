package com.qcloud.cos.model;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.utils.Jackson;


public class SetBucketReplicationConfigurationRequest extends CosServiceRequest
        implements Serializable {

    /**
     * The name of bucket to which the replication configuration is set.
     */
    private String bucketName;

    /**
     * Replication configuration for bucket.
     */
    private BucketReplicationConfiguration replicationConfiguration;

    /**
     * Creates a new SetReplicationConfigurationRequest.
     */
    public SetBucketReplicationConfigurationRequest() {}

    /**
     * Creates a new SetReplicationConfigurationRequest.
     *
     * @param bucketName The name of bucket to which the replication configuration is set.
     * @param replicationConfiguration Replication configuration for bucket.
     */
    public SetBucketReplicationConfigurationRequest(String bucketName,
            BucketReplicationConfiguration replicationConfiguration) {
        this.bucketName = bucketName;
        this.replicationConfiguration = replicationConfiguration;
    }

    /**
     * Returns the name of bucket.
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Sets the name of bucket for replication configuration.
     *
     * @param bucketName The name of bucket to which the replication configuration is set.
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Sets the name of bucket for replication configuration. Returns the updated object. .
     *
     * @param bucketName The name of bucket to which the replication configuration is set.
     * @return The updated {@link SetBucketReplicationConfigurationRequest} object.
     */
    public SetBucketReplicationConfigurationRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    /**
     * Returns the replication configuration of bucket.
     *
     * @return the replication configuration of bucket.
     */
    public BucketReplicationConfiguration getReplicationConfiguration() {
        return replicationConfiguration;
    }

    /**
     * Sets the replication configuration for bucket.
     *
     * @param replicationConfiguration the replication configuration for bucket.
     */
    public void setReplicationConfiguration(
            BucketReplicationConfiguration replicationConfiguration) {
        this.replicationConfiguration = replicationConfiguration;
    }

    /**
     * Sets the replication configuration for bucket. Returns the updated object. .
     *
     * @param replicationConfiguration the replication configuration for bucket.
     * @return The updated {@link SetBucketReplicationConfigurationRequest} object.
     */
    public SetBucketReplicationConfigurationRequest withReplicationConfiguration(
            BucketReplicationConfiguration replicationConfiguration) {
        setReplicationConfiguration(replicationConfiguration);
        return this;
    }

    @Override
    public String toString() {
        return Jackson.toJsonString(this);
    }
}
