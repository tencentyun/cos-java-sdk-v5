package com.qcloud.cos.model;

import com.qcloud.cos.Headers;
import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.internal.AbstractCosResponseHandler;
import com.qcloud.cos.internal.CosServiceResponse;

public class HeadBucketResultHandler extends AbstractCosResponseHandler<HeadBucketResult> {

    @Override
    public CosServiceResponse<HeadBucketResult> handle(CosHttpResponse response)
            throws Exception {
        final CosServiceResponse<HeadBucketResult> cosResponse = new CosServiceResponse<HeadBucketResult>();
        final HeadBucketResult result = new HeadBucketResult();
        result.setBucketRegion(response.getHeaders().get(Headers.COS_BUCKET_REGION));
        cosResponse.setResult(result);
        return cosResponse;
    }
}