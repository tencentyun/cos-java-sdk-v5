package com.qcloud.cos.model;

import java.io.Serializable;

public class PutSymlinkResult implements Serializable {
    private static final long serialVersionUID = -934810592067265549L;

    // x-cos-storage-class
    private String storageClass;

    // x-cos-request-id
    private String requestId;

    // ETag
    private String ETag;

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getETag() {
        return ETag;
    }

    public void setETag(String ETag) {
        this.ETag = ETag;
    }
}
