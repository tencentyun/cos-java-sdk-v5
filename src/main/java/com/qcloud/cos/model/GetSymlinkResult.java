package com.qcloud.cos.model;

import java.io.Serializable;

public class GetSymlinkResult implements Serializable {
    private static final long serialVersionUID = 3524820691299905321L;

    private String requestId;
    private String ETag;
    private String target;

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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
