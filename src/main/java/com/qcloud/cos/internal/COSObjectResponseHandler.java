package com.qcloud.cos.internal;

import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.ObjectMetadata;

public class COSObjectResponseHandler extends AbstractCosResponseHandler<COSObject> {

    @Override
    public CosServiceResponse<COSObject> handle(CosHttpResponse response) throws Exception {
        COSObject object = new COSObject();
        CosServiceResponse<COSObject> cosResponse = parseResponseMetadata(response);

        ObjectMetadata metadata = object.getObjectMetadata();
        populateObjectMetadata(response, metadata);
        object.setObjectContent(
                new COSObjectInputStream(response.getContent(), response.getHttpRequest()));
        cosResponse.setResult(object);
        return cosResponse;
    }
    
    @Override
    public boolean needsConnectionLeftOpen() {
        return true;
    }

}
