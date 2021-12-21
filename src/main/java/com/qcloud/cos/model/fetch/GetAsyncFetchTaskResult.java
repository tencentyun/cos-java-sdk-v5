package com.qcloud.cos.model.fetch;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class GetAsyncFetchTaskResult {
    @JsonIgnore
    private String cosRequestId;
    private String code;
    private String message;
    private String request_id;
    private Map<String, String> data;

    public GetAsyncFetchTaskResult() {};

    public void setCosRequestId(String requestId) {
        this.cosRequestId = requestId;
    }

    public String getCosRequestId() {
        return this.cosRequestId;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getRequestId() {
        return request_id;
    }

    public Map<String, String> getData() {
        return this.data;
    }
}
