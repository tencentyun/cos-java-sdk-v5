package com.qcloud.cos.internal;

import com.qcloud.cos.http.CosHttpResponse;

/**
 * Assistant response handler that can pull an HTTP header out of the response
 * and apply it to a response object.
 */
public interface HeaderHandler<T> {

    /**
     * Applies one or more headers to the response object given.
     * 
     * @param result
     *            The response object to be returned to the client.
     * @param response
     *            The HTTP response from cos.
     */
    public void handle(T result, CosHttpResponse response);
}
