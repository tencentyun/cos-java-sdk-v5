package com.qcloud.cos.internal;

import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.model.ObjectMetadata;

public class CosMetadataResponseHandler extends AbstractCosResponseHandler<ObjectMetadata>{

    @Override
    public CosServiceResponse<ObjectMetadata> handle(CosHttpResponse response) throws Exception {
        ObjectMetadata metadata = new ObjectMetadata();
        populateObjectMetadata(response, metadata);

        CosServiceResponse<ObjectMetadata> cosResponse = parseResponseMetadata(response);
        cosResponse.setResult(metadata);
        return cosResponse;
    }

}
