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


package com.qcloud.cos.internal;

import java.io.InputStream;

import com.qcloud.cos.COSClient;

/**
 * This wrapper input stream holds a reference to the service client. This is
 * mainly done to avoid the COSClient object being garbage
 * collected when the client reads data from the input stream.
 * 
 */
public class ServiceClientHolderInputStream extends SdkFilterInputStream {

    
    @SuppressWarnings("unused")
    private COSClient client;

    public ServiceClientHolderInputStream(InputStream in,
            COSClient client) {
        super(in);
        this.client = client;
    }   
}
