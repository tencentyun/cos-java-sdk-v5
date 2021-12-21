package com.qcloud.cos.model.fetch;

import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.internal.AbstractCosResponseHandler;
import com.qcloud.cos.internal.CosServiceResponse;
import com.qcloud.cos.utils.Jackson;

public class PutAsyncFetchTaskResultHandler extends AbstractCosResponseHandler<PutAsyncFetchTaskResult> {
    
    @Override
    public CosServiceResponse<PutAsyncFetchTaskResult> handle(CosHttpResponse response) throws Exception {
        String requestId = response.getHeaders().get("x-cos-request-id");

        byte[] bytes = new byte[0];
        bytes = new byte[response.getContent().available()];
        response.getContent().read(bytes);

        PutAsyncFetchTaskResult result = Jackson.fromJsonString(new String(bytes), PutAsyncFetchTaskResult.class);
        result.setCosRequestId(requestId);

        CosServiceResponse<PutAsyncFetchTaskResult> cosResponse = new CosServiceResponse<PutAsyncFetchTaskResult>();
        cosResponse.setResult(result);
        return cosResponse;
    }
}
