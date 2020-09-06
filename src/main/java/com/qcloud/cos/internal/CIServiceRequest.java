/*
 * Copyright 2020 腾讯云, Inc. or its affiliates. All Rights Reserved.
 *
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

 * According to ci feature, we modify some class，comment, field name, etc.
 */

package com.qcloud.cos.internal;

import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.event.ProgressListener;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.*;


public class CIServiceRequest extends CosServiceRequest implements Cloneable, ReadLimitInfo {
    /**
     * BucketName e.g：bucketName-1234567
     */
    @XStreamOmitField
    private String bucketName;

    private String requestId;

    @XStreamOmitField
    private ProgressListener progressListener = ProgressListener.NOOP;

    @XStreamOmitField
    private final RequestClientOptions requestClientOptions = new RequestClientOptions();

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
