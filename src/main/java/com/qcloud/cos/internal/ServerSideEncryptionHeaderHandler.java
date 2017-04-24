package com.qcloud.cos.internal;

import com.qcloud.cos.Headers;
import com.qcloud.cos.http.CosHttpResponse;

/**
 * Base request handler for responses that include a server-side encryption
 * header
 */
public class ServerSideEncryptionHeaderHandler <T extends ServerSideEncryptionResult> implements HeaderHandler<T>{

    @Override
    public void handle(T result, CosHttpResponse response) {
        result.setSSEAlgorithm(response.getHeaders().get(Headers.SERVER_SIDE_ENCRYPTION));
        result.setSSECustomerAlgorithm(response.getHeaders().get(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM));
        result.setSSECustomerKeyMd5(response.getHeaders().get(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5));
    }
}

