package com.qcloud.cos.internal.cihandler;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

public  class HttpEntityEnclosingDelete extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";

    public HttpEntityEnclosingDelete() {

    }
    public HttpEntityEnclosingDelete(URI uri) {
        this.setURI(uri);
    }

    public HttpEntityEnclosingDelete(String uri) {
        this.setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}
