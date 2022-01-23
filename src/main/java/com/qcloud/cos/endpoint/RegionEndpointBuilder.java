/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 
 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos.endpoint;

import com.qcloud.cos.internal.BucketNameUtils;
import com.qcloud.cos.region.Region;

public class RegionEndpointBuilder implements EndpointBuilder {
    private Region region;
    private Boolean enableInternalDomain = true;
    private Boolean enableOldDomain = false;

    public RegionEndpointBuilder(Region region) {
        super();
        this.region = region;
    }

    public void setEnableInternalDomain(Boolean enableInternalDomain) {
        this.enableInternalDomain = enableInternalDomain;
    }

    public void setEnableOldDomain(Boolean enableOldDomain) {
        this.enableOldDomain = enableOldDomain;
    }

    @Override
    public String buildGeneralApiEndpoint(String bucketName) {
        if (this.region == null) {
            throw new IllegalArgumentException("region is null");
        }
        BucketNameUtils.validateBucketName(bucketName);

        if (this.enableOldDomain) {
            return String.format("%s.%s.myqcloud.com", bucketName, Region.formatRegion(this.region, false));
        }

        if (this.enableInternalDomain) {
            return String.format("%s.%s.tencentcos.cn", bucketName, Region.formatRegion(this.region, true));
        }

        return String.format("%s.%s.tencentcos.cn", bucketName, Region.formatRegion(this.region, false));
    }

    @Override
    public String buildGetServiceApiEndpoint() {
        if (this.enableOldDomain) {
            return "service.cos.myqcloud.com";
        }
        return "service.cos.tencentcos.cn";
    }
}
