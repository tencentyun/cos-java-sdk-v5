package com.qcloud.cos.endpoint;

import com.qcloud.cos.internal.BucketNameUtils;
import com.qcloud.cos.region.Region;

public class RegionEndpointBuilder implements EndpointBuilder {
    private Region region;

    public RegionEndpointBuilder(Region region) {
        super();
        this.region = region;
    }

    @Override
    public String buildGeneralApiEndpoint(String bucketName) {
        if (this.region == null) {
            throw new IllegalArgumentException("region is null");
        }
        BucketNameUtils.validateBucketName(bucketName);
        return String.format("%s.%s.myqcloud.com", bucketName, Region.formatRegion(this.region));
    }

    @Override
    public String buildGetServiceApiEndpoint() {
        return "service.cos.myqcloud.com";
    }
}
