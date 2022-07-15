package com.qcloud.cos.model;

import com.qcloud.cos.internal.CosServiceRequest;

import java.io.Serializable;

public class PutSymlinkRequest extends CosServiceRequest implements Serializable {
    private static final long serialVersionUID = 2696244667516350971L;
    private String bucketName;
    private String symlink;
    private String target;

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
}
