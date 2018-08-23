package com.qcloud.cos.internal;

import java.io.InputStream;

import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.utils.StringUtils;

public class COSStringResponseHandler extends AbstractCosResponseHandler<String> {

    @Override
    public CosServiceResponse<String> handle(CosHttpResponse response) throws Exception {
        CosServiceResponse<String> cosResponse = parseResponseMetadata(response);

        int bytesRead;
        byte[] buffer = new byte[1024];
        StringBuilder builder = new StringBuilder();
        InputStream content = response.getContent();
        while ((bytesRead = content.read(buffer)) > 0) {
            builder.append(new String(buffer, 0, bytesRead, StringUtils.UTF8));
        }
        cosResponse.setResult(builder.toString());

        return cosResponse;
    }

}
