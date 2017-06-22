package com.qcloud.cos.internal;

import com.qcloud.cos.Headers;
import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.internal.XmlResponsesSaxParser.CopyObjectResultHandler;

/**
 * Header handler to pull the S3_VERSION_ID header out of the response. This
 * header is required for the copyPart and copyObject api methods.
 */
public class COSVersionHeaderHandler implements HeaderHandler<CopyObjectResultHandler> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.amazonaws.services.s3.internal.HeaderHandler#handle(java.lang.Object,
     * com.amazonaws.http.HttpResponse)
     */
    @Override
    public void handle(CopyObjectResultHandler result, CosHttpResponse response) {
        result.setVersionId(response.getHeaders().get(Headers.COS_VERSION_ID));
    }
}