package com.qcloud.cos.model;

import java.io.Serializable;

public class GetSymlinkResult implements Serializable {
    private static final long serialVersionUID = 3524820691299905321L;
    private String requestId;
    private long contentLength;
    private String contentType;
    private long lastModified;
    private String versionId;
    private String ETag;
    private String target;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public void setETag(String ETag) {
        this.ETag = ETag;
    }

    public String getETag() {
        return ETag;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
