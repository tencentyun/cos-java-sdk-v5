/*
 * Copyright 2015-2019 Amazon Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qcloud.cos.model;
import java.io.Serializable;

/**
 * Request object for the parameters to get a bucket's tagging configuration.
 *
 * @see com.qcloud.cos.COSClient#getBucketTaggingConfiguration(GetBucketTaggingConfigurationRequest)
 */
public class GetBucketTaggingConfigurationRequest extends GenericBucketRequest implements Serializable {

    /**
     * Creates request object, ready to be executed to fetch the tagging
     * configuration for the specified bucket.
     *
     * @param bucketName
     *            The name of the bucket whose tagging configuration is being
     *            fetched.
     */
    public GetBucketTaggingConfigurationRequest(String bucketName) {
        super(bucketName);
    }

}