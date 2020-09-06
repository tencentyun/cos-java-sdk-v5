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

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

public class DeleteBucketRequest extends CosServiceRequest implements Serializable {
    /**
     * The name of the Qcloud COS bucket to delete.
     */
    private String bucketName;

    /**
     * Constructs a new {@link DeleteBucketRequest}, ready to be executed to delete the specified
     * bucket.
     * 
     * @param bucketName The name of the Qcloud COS bucket to delete.
     */
    public DeleteBucketRequest(String bucketName) {
        setBucketName(bucketName);
    }

    /**
     * Sets the name of the Qcloud COS bucket to delete.
     * 
     * @param bucketName The name of the Qcloud COS bucket to delete.
     * 
     * @see DeleteBucketRequest#getBucketName()
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Gets the name of the Qcloud COS bucket to delete.
     * 
     * @return The name of the Qcloud COS bucket to delete.
     * 
     * @see DeleteBucketRequest#setBucketName(String)
     */
    public String getBucketName() {
        return bucketName;
    }
}
