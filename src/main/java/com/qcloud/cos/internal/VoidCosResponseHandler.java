package com.qcloud.cos.internal;

import com.qcloud.cos.http.CosHttpResponse;

public class VoidCosResponseHandler extends AbstractCosResponseHandler<Void> {

    @Override
    public CosServiceResponse<Void> handle(CosHttpResponse response) throws Exception {
        CosServiceResponse<Void> csp = new CosServiceResponse<>();
        return csp;
    }

}
