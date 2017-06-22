package com.qcloud.cos.internal;

import com.qcloud.cos.Headers;
import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.internal.XmlResponsesSaxParser.CopyObjectResultHandler;

/**
 * Header handler to pull the COS_VERSION_ID header out of the response. This
 * header is required for the copyPart and copyObject api methods.
 */
public class COSVersionHeaderHandler implements HeaderHandler<CopyObjectResultHandler> {

    @Override
    public void handle(CopyObjectResultHandler result, CosHttpResponse response) {
        result.setVersionId(response.getHeaders().get(Headers.COS_VERSION_ID));
    }
}