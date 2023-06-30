package com.qcloud.cos.model.Policy;

import com.qcloud.cos.internal.CosServiceRequest;

import java.util.List;

public class SetBucketPolicyStatementRequest extends CosServiceRequest {
    private String bucketName;
    private String ownerUin;
    private String subUin;
    private List<String> resourcePath;
    private String actionTemplate;

    public SetBucketPolicyStatementRequest(String bucketName, String subUin, List<String> resourcePath, String actionTemplate) {
        this.bucketName = bucketName;
        this.subUin = subUin;
        this.resourcePath = resourcePath;
        this.actionTemplate = actionTemplate;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setOwnerUin(String ownerUin) {
        this.ownerUin = ownerUin;
    }

    public String getOwnerUin() {
        return ownerUin;
    }

    public void setSubUin(String subUin) {
        this.subUin = subUin;
    }

    public String getSubUin() {
        return subUin;
    }

    public void setResourcePath(List<String> resourcePath) {
        this.resourcePath = resourcePath;
    }

    public List<String> getResourcePath() {
        return resourcePath;
    }

    public void setActionTemplate(String actionTemplate) {
        this.actionTemplate = actionTemplate;
    }

    public String getActionTemplate() {
        return actionTemplate;
    }
}
