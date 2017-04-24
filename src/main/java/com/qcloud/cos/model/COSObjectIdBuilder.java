package com.qcloud.cos.model;

import java.io.Serializable;


/**
 * Convenient builder for {@link COSObjectId}.
 */
public final class COSObjectIdBuilder implements Serializable {
    private String bucket;
    private String key;
    private String versionId;

    public COSObjectIdBuilder() {}

    /**
     * @param id COS object id, which must not be null.
     */
    public COSObjectIdBuilder(COSObjectId id) {
        this.bucket = id.getBucket();
        this.key = id.getKey();
        this.versionId = id.getVersionId();
    }

    public String getBucket() {
        return bucket;
    }

    public String getKey() {
        return key;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public COSObjectIdBuilder withBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public COSObjectIdBuilder withKey(String key) {
        this.key = key;
        return this;
    }

    public COSObjectIdBuilder withVersionId(String versionId) {
        this.versionId = versionId;
        return this;
    }

    public COSObjectId build() {
        return new COSObjectId(bucket, key, versionId);
    }
}
