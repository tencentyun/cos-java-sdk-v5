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
import com.qcloud.cos.internal.UrlComponentUtils;

public class StringEndpointBuilder implements EndpointBuilder {
    private String endpoint; 

    public StringEndpointBuilder(String endpoint) {
        super();

        if (endpoint == null) {
            throw new IllegalArgumentException("endpoint must not be null");
        }

        UrlComponentUtils.validateEndPointSuffix(endpoint);

        this.endpoint = endpoint;
    }

    @Override
    public String buildGeneralApiEndpoint(String bucketName) {
        BucketNameUtils.validateBucketName(bucketName);
        return String.format("%s.%s", bucketName, this.endpoint);
    }

    @Override
    public String buildGetServiceApiEndpoint() {
        return "service.cos.tencentcos.cn";
    }
}
