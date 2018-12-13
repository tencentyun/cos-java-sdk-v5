package com.qcloud.cos.endpoint;

import com.qcloud.cos.internal.BucketNameUtils;
import com.qcloud.cos.internal.UrlComponentUtils;

public class SuffixEndpointBuilder  implements EndpointBuilder {
    private String endpointSuffix;
    public SuffixEndpointBuilder(String endpointSuffix) {
        super();
        if (endpointSuffix == null) {
            throw new IllegalArgumentException("endpointSuffix must not be null");
        }
        while(endpointSuffix.startsWith(".")) {
            endpointSuffix = endpointSuffix.substring(1);
        }
        UrlComponentUtils.validateEndPointSuffix(endpointSuffix);
        this.endpointSuffix = endpointSuffix.trim();
    }

    @Override
    public String buildGeneralApiEndpoint(String bucketName) {
        BucketNameUtils.validateBucketName(bucketName);
        return String.format("%s.%s", bucketName, this.endpointSuffix);
    }

    @Override
    public String buildGetServiceApiEndpoint() {
        return this.endpointSuffix;
    }
    
    
}
