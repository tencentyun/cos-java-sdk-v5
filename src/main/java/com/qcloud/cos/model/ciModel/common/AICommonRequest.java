package com.qcloud.cos.model.ciModel.common;


import com.qcloud.cos.http.HttpMethodName;

import java.util.Map;

public class AICommonRequest {
    private String bucketName;

    private String objectKey;

    private String ciProcess;
    private HttpMethodName method;

    private Map<String, String> queryParams;

    private Map<String, String> headers;

    private byte[] content;

    private String contentType;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getCiProcess() {
        return ciProcess;
    }

    public void setCiProcess(String ciProcess) {
        this.ciProcess = ciProcess;
    }

    public HttpMethodName getMethod() {
        return method;
    }

    public void setMethod(HttpMethodName method) {
        this.method = method;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
