package com.qcloud.cos.model.ciModel.common;


import com.qcloud.cos.http.HttpMethodName;

import java.util.Map;

public class CICommonRequest {
    /**
     * The name of the Qcloud COS bucket containing the object to image process
     */
    private String bucketName;

    /**
     * The key of the object to image process.
     */
    private String key;

    private HttpMethodName method;

    private Map<String, String> queryParams;

    private Map<String, String> headers;

    private byte[] content;

    private String contentType;
}
