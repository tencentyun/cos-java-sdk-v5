package com.qcloud.cos.internal;

import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.internal.XmlResponsesSaxParser.PutObjectHandler;

public class ObjectMetadataHandler <T extends PutObjectHandler> extends AbstractCosResponseHandler <T> implements HeaderHandler<T>{

    @Override
    public void handle(T result, CosHttpResponse response) {
    	ObjectMetadata metadata = new ObjectMetadata();
        populateObjectMetadata(response, metadata);
        
        result.getPutObjectResult().setMetadata(metadata);
    }
    
    @Override
    public CosServiceResponse<T> handle(CosHttpResponse response) throws Exception {
        return null;
    }
}

