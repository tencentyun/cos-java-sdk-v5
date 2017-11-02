package com.qcloud.cos.model;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

public class GetObjectAclRequest extends CosServiceRequest implements Serializable {
    /** The name of the bucket containing the object whose ACL is being set. */
    private String bucketName;

    /** The name of the object whose ACL is being set. */
    private String key;

    /** The version ID of the object version whose ACL is being set. */
    private String versionId;



    public GetObjectAclRequest(String bucketName, String key) {
        this(bucketName, key, null);
    }

    private GetObjectAclRequest(String bucketName, String key, String versionId) {
        super();
        this.bucketName = bucketName;
        this.key = key;
        this.versionId = versionId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public GetObjectAclRequest withBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public GetObjectAclRequest withKey(String key) {
        this.key = key;
        return this;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public GetObjectAclRequest withVersionId(String versionId) {
        this.versionId = versionId;
        return this;
    }

}
