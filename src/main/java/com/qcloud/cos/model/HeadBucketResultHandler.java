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


package com.qcloud.cos.model;

import com.qcloud.cos.Headers;
import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.internal.AbstractCosResponseHandler;
import com.qcloud.cos.internal.CosServiceResponse;
import com.qcloud.cos.internal.ResponseMetadata;

import java.util.Map;

import static com.qcloud.cos.internal.Constants.BUCKET_OFS_ARCH_TYPE;

public class HeadBucketResultHandler extends AbstractCosResponseHandler<HeadBucketResult> {

    @Override
    public CosServiceResponse<HeadBucketResult> handle(CosHttpResponse response)
            throws Exception {
        final CosServiceResponse<HeadBucketResult> cosResponse = new CosServiceResponse<HeadBucketResult>();
        boolean isMergeBucket = false;
        boolean isMazBucket = false;
        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
            String key = header.getKey();
            String value = header.getValue();
            if (key.equalsIgnoreCase(Headers.BUCKET_ARCH) &&
                    value.equalsIgnoreCase("OFS")) {
                isMergeBucket = true;
            } else if (key.equalsIgnoreCase(Headers.BUCKET_AZ_TYPE) &&
                    value.equalsIgnoreCase("MAZ")) {
                isMazBucket = true;
            }
        }
        final HeadBucketResult result = new HeadBucketResult();
        result.setBucketRegion(response.getHeaders().get(Headers.COS_BUCKET_REGION));
        result.setMergeBucket(isMergeBucket);
        result.setMazBucket(isMazBucket);
        cosResponse.setResult(result);
        return cosResponse;
    }
}