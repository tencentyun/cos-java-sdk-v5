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


package com.qcloud.cos.transfer;

import java.util.Collection;

/**
 * Multiple file download of an entire virtual directory.
 */
public interface  MultipleFileUpload extends Transfer {

    /**
     * Returns the key prefix of the virtual directory being uploaded.
     */
    public String getKeyPrefix();

    /**
     * Returns the name of the bucket to which files are uploaded.
     */
    public String getBucketName();

    /**
     * Returns a collection of sub transfers associated with the multi file upload.
     */
    public Collection<? extends Upload> getSubTransfers();

}