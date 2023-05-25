package com.qcloud.cos.model;

import com.qcloud.cos.Headers;
import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.internal.AbstractCosResponseHandler;
import com.qcloud.cos.internal.CosServiceResponse;

import java.sql.Date;
import java.util.Map;

public class GetSymlinkResultHandler extends AbstractCosResponseHandler<GetSymlinkResult> {
    @Override
    public CosServiceResponse<GetSymlinkResult> handle(CosHttpResponse response) throws Exception {
        GetSymlinkResult getSymlinkResult = new GetSymlinkResult();
        final CosServiceResponse<GetSymlinkResult> cosServiceResponse = new CosServiceResponse<>();
        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
            String key = header.getKey();
            String value = header.getValue();

            if (Headers.REQUEST_ID.compareToIgnoreCase(key) == 0) {
                getSymlinkResult.setRequestId(value);
                continue;
            }

            if (Headers.CONTENT_LENGTH.compareToIgnoreCase(key) == 0) {
                getSymlinkResult.setContentLength(Long.parseLong(value));
                continue;
            }

            if (Headers.CONTENT_TYPE.compareToIgnoreCase(key) == 0) {
                getSymlinkResult.setContentType(value);
                continue;
            }

            if (Headers.LAST_MODIFIED.compareToIgnoreCase(key) == 0) {
                getSymlinkResult.setLastModified(Date.parse(value));
                continue;
            }

            if (Headers.COS_VERSION_ID.compareToIgnoreCase(key) == 0) {
                getSymlinkResult.setVersionId(value);
                continue;
            }

            if (Headers.ETAG.compareToIgnoreCase(key) == 0) {
                getSymlinkResult.setETag(value);
                continue;
            }

            if (Headers.SYMLINK_TARGET.compareToIgnoreCase(key) == 0) {
                getSymlinkResult.setTarget(value);
            }
        }

        cosServiceResponse.setResult(getSymlinkResult);
        return cosServiceResponse;
    }
}
