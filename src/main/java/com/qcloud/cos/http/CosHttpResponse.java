package com.qcloud.cos.http;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpRequestBase;

public class CosHttpResponse {

    private final CosHttpRequest<?> request;

    private final HttpRequestBase httpRequest;

    private String statusText;
    private int statusCode;
    private InputStream content;
    private Map<String, String> headers = new HashMap<String, String>();

    public CosHttpResponse(CosHttpRequest<?> request, HttpRequestBase httpRequest) {
        super();
        this.request = request;
        this.httpRequest = httpRequest;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public CosHttpRequest<?> getRequest() {
        return request;
    }

    public HttpRequestBase getHttpRequest() {
        return httpRequest;
    }

}
