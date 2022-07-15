package com.qcloud.cos.model;

import com.qcloud.cos.Headers;
import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.internal.AbstractCosResponseHandler;
import com.qcloud.cos.internal.CosServiceResponse;

import java.util.Map;

public class GetSymlinkResultHandler extends AbstractCosResponseHandler<GetSymlinkResult> {
    @Override
    public CosServiceResponse<GetSymlinkResult> handle(CosHttpResponse response) throws Exception {
        GetSymlinkResult getSymlinkResult = new GetSymlinkResult();
        final CosServiceResponse<GetSymlinkResult> cosServiceResponse = new CosServiceResponse<>();
        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
            String key = header.getKey();
            String value = header.getValue();

            if (key.compareToIgnoreCase(Headers.REQUEST_ID) == 0) {
                getSymlinkResult.setRequestId(value);
                continue;
            }

            if (key.compareToIgnoreCase(Headers.ETAG) == 0) {
                getSymlinkResult.setETag(value);
                continue;
            }

            if (key.compareToIgnoreCase(Headers.SYMLINK_TARGET) == 0) {
                getSymlinkResult.setTarget(value);
            }
        }

        cosServiceResponse.setResult(getSymlinkResult);
        return cosServiceResponse;
    }
}
