package com.qcloud.cos.model.Policy;

import java.util.ArrayList;
import java.util.List;

public class DelBucketPolicyStatementRequest {
    private String bucketName;
    private String ownerUin;
    private String subUin;
    private String actionTemplate;
    private List<String> paths2delete = new ArrayList<>();

    private String accountName;

    public DelBucketPolicyStatementRequest(String bucketName, String subUin, String actionTemplate) {
        this.bucketName = bucketName;
        this.subUin = subUin;
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

    public void setActionTemplate(String actionTemplate) {
        this.actionTemplate = actionTemplate;
    }

    public String getActionTemplate() {
        return actionTemplate;
    }

    public List<String> getPath2Delete() {
        return paths2delete;
    }

    public void setPath2Delete(List<String> path2delete) {
        this.paths2delete = path2delete;
    }

    public void addPath2Delete(String path) {
        paths2delete.add(path);
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
