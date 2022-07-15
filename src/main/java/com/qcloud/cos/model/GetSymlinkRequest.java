package com.qcloud.cos.model;

import com.qcloud.cos.internal.CosServiceRequest;

import java.io.Serializable;

public class GetSymlinkRequest extends CosServiceRequest implements Serializable {
    private static final long serialVersionUID = -1413983172326934188L;

    private String bucketName;
    private String symlink;
    private String versionId;

    public GetSymlinkRequest() {
    }

    public GetSymlinkRequest(String bucketName, String symlink, String versionId) {
        this.bucketName = bucketName;
        this.symlink = symlink;
        this.versionId = versionId;
    }

    public GetSymlinkRequest withBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public GetSymlinkRequest withSymlink(String symlink) {
        this.symlink = symlink;
        return this;
    }

    public GetSymlinkRequest withVersionId(String versionId) {
        this.versionId = versionId;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getSymlink() {
        return symlink;
    }

    public void setSymlink(String symlink) {
        this.symlink = symlink;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }
}
