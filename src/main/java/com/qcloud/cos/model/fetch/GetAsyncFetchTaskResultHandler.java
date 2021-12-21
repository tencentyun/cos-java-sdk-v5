package com.qcloud.cos.model.fetch;

import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.internal.AbstractCosResponseHandler;
import com.qcloud.cos.internal.CosServiceResponse;
import com.qcloud.cos.utils.Jackson;

public class GetAsyncFetchTaskResultHandler extends AbstractCosResponseHandler<GetAsyncFetchTaskResult> {
    
    @Override
    public CosServiceResponse<GetAsyncFetchTaskResult> handle(CosHttpResponse response) throws Exception {
        String requestId = response.getHeaders().get("x-cos-request-id");

        byte[] bytes = new byte[0];
        bytes = new byte[response.getContent().available()];
        response.getContent().read(bytes);

        GetAsyncFetchTaskResult result = Jackson.fromJsonString(new String(bytes), GetAsyncFetchTaskResult.class);
        result.setCosRequestId(requestId);

        CosServiceResponse<GetAsyncFetchTaskResult> cosResponse = new CosServiceResponse<GetAsyncFetchTaskResult>();
        cosResponse.setResult(result);
        return cosResponse;
    }
}
