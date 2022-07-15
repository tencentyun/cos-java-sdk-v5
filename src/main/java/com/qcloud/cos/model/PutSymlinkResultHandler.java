package com.qcloud.cos.model;

import com.qcloud.cos.Headers;
import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.internal.AbstractCosResponseHandler;
import com.qcloud.cos.internal.CosServiceResponse;

import java.util.Map;

public class PutSymlinkResultHandler extends AbstractCosResponseHandler<PutSymlinkResult> {
    @Override
    public CosServiceResponse<PutSymlinkResult> handle(CosHttpResponse response) throws Exception {
        PutSymlinkResult putSymlinkResult = new PutSymlinkResult();
        final CosServiceResponse<PutSymlinkResult> cosServiceResponse = new CosServiceResponse<>();
        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
            String key = header.getKey();
            String value = header.getValue();

            if (key.compareToIgnoreCase(Headers.REQUEST_ID) == 0) {
                putSymlinkResult.setRequestId(value);
                continue;
            }

            if (key.compareToIgnoreCase(Headers.ETAG) == 0) {
                putSymlinkResult.setETag(value);
                continue;
            }

            if (key.compareToIgnoreCase(Headers.STORAGE_CLASS) == 0) {
                putSymlinkResult.setStorageClass(value);
            }
        }

        cosServiceResponse.setResult(putSymlinkResult);
        return cosServiceResponse;
    }
}
