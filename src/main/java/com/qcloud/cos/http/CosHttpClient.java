package com.qcloud.cos.http;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.internal.CosServiceResponse;

public interface CosHttpClient {
    public <X, Y extends CosServiceRequest> X exeute(CosHttpRequest<Y> request,
            HttpResponseHandler<CosServiceResponse<X>> responseHandler)
                    throws CosClientException, CosServiceException;

    public void shutdown();
}
