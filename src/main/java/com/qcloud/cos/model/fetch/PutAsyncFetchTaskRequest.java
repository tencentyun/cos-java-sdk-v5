package com.qcloud.cos.model.fetch;

import com.qcloud.cos.internal.CosServiceRequest;

public class PutAsyncFetchTaskRequest extends CosServiceRequest {
    private String bucketName;

    private String Url;
    private String Key;
    private Boolean IgnoreSameKey = false;

    private String MD5 = null;
    private String SuccessCallbackUrl = null;
    private String FailureCallbackurl = null;
    private String OnKeyExist = null;

    public PutAsyncFetchTaskRequest() {};

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setUrl(String url) {
        this.Url = url;
    }

    public String getUrl() {
        return this.Url;
    }

    public void setMd5(String md5) {
        this.MD5 = md5;
    }

    public String getMd5() {
        return this.MD5;
    }

    public void setIgnoreSameKey(Boolean ignoreSameKey) {
        this.IgnoreSameKey = ignoreSameKey;
    }

    public Boolean getIgnoreSameKey() {
        return this.IgnoreSameKey;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public String getKey() {
        return this.Key;
    }

    public void setSuccessCallbackUrl(String successCallbackUrl) {
        this.SuccessCallbackUrl = successCallbackUrl;
    }

    public String getSuccessCallbackUrl() {
        return this.SuccessCallbackUrl;
    }

    public void setFailureCallbackUrl(String failureCallbackUrl) {
        this.FailureCallbackurl = failureCallbackUrl;
    }

    public String getFailureCallbackUrl() {
        return this.FailureCallbackurl;
    }

    public void setOnKeyExist(String onKeyExist) {
        this.OnKeyExist = onKeyExist;
    }

    public String getOnKeyExist() {
        return this.OnKeyExist;
    }
}
