package com.qcloud.cos.model.Policy;

import java.util.ArrayList;
import java.util.List;

public class GetBucketPolicyStatementRequest {
    private String bucketName;
    private List<String> subUins = new ArrayList<>();
    private List<String> resourcePaths = new ArrayList<>();
    private List<String> actionTemplates = new ArrayList<>();
    private List<String> effects = new ArrayList<>();

    public GetBucketPolicyStatementRequest(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setSubUins(List<String> subUin) {
        this.subUins = subUin;
    }

    public List<String> getSubUins() {
        return subUins;
    }

    public void setResourcePaths(List<String> resourcePaths) {
        this.resourcePaths = resourcePaths;
    }

    public List<String> getResourcePaths() {
        return resourcePaths;
    }

    public void setActionTemplates(List<String> actionTemplates) {
        this.actionTemplates = actionTemplates;
    }

    public List<String> getActionTemplates() {
        return actionTemplates;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setEffects(List<String> effects) {
        this.effects = effects;
    }

    public List<String> getEffects() {
        return effects;
    }
}
