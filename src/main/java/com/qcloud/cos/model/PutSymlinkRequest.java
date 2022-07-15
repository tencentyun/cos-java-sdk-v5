package com.qcloud.cos.model;

import com.qcloud.cos.internal.CosServiceRequest;

import java.io.Serializable;

public class PutSymlinkRequest extends CosServiceRequest implements Serializable {
    private static final long serialVersionUID = 2696244667516350971L;
    private String bucketName;
    private String symlink;
    private String target;
    private String versionId;

    private AccessControlList accessControlList;
    private CannedAccessControlList cannedAccessControlList;

    public PutSymlinkRequest() {
    }

    public PutSymlinkRequest(String bucketName, String symlink, String target) {
        this.bucketName = bucketName;
        this.symlink = symlink;
        this.target = target;
    }

    public PutSymlinkRequest withBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public PutSymlinkRequest withSymlink(String symlink) {
        this.symlink = symlink;
        return this;
    }

    public PutSymlinkRequest withTarget(String target) {
        this.target = target;
        return this;
    }

    public PutSymlinkRequest withAccessControlList(
            AccessControlList accessControlList) {
        this.accessControlList = accessControlList;
        return this;
    }

    public PutSymlinkRequest withCannedAccessControlList(
            CannedAccessControlList cannedAccessControlList) {
        this.cannedAccessControlList = cannedAccessControlList;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public AccessControlList getAccessControlList() {
        return accessControlList;
    }

    public void setAccessControlList(AccessControlList accessControlList) {
        this.accessControlList = accessControlList;
    }

    public CannedAccessControlList getCannedAccessControlList() {
        return cannedAccessControlList;
    }

    public void setCannedAccessControlList(CannedAccessControlList cannedAccessControlList) {
        this.cannedAccessControlList = cannedAccessControlList;
    }
}
