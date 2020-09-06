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
import java.util.LinkedList;
import java.util.List;

import com.qcloud.cos.internal.CosServiceRequest;

public class AbortMultipartUploadRequest extends CosServiceRequest implements Serializable {
    /** The name of the bucket containing the multipart upload to abort */
    private String bucketName;

    /** The key of the multipart upload to abort */
    private String key;

    /** The ID of the multipart upload to abort */
    private String uploadId;
    

    /**
     * Constructs a new request to abort a multipart upload.
     *
     * @param bucketName
     *            The name of the bucket containing the multipart upload to
     *            abort.
     * @param key
     *            The key of the multipart upload to abort.
     * @param uploadId
     *            The ID of the multipart upload to abort.
     */
    public AbortMultipartUploadRequest(String bucketName, String key, String uploadId) {
        this.bucketName = bucketName;
        this.key = key;
        this.uploadId = uploadId;
    }


    /**
     * Returns the name of the bucket containing the multipart upload to abort.
     */
    public String getBucketName() {
        return this.bucketName;
    }

    /**
     * Sets the name of the bucket containing the multipart upload to abort.
     */
    public void setBucketName(String value) {
        this.bucketName = value;
    }

    /**
     * Sets the name of the bucket containing the multipart upload to abort and
     * returns the updated object so that additional calls can be chained
     * together.
     *
     * @param bucketName
     *            The name of the bucket containing the multipart upload to
     *            abort.
     *
     * @return The updated AbortMultipartUploadRequest object.
     */
    public AbortMultipartUploadRequest withBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    /**
     * Returns the key of the multipart upload to abort.
     *
     * @return The key of the multipart upload to abort.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key of the multipart upload to abort.
     *
     * @param key
     *            The key of the multipart upload to abort.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets the key of the multipart upload to abort and returns the updated
     * AbortMultipartUploadRequest object so that additional method calls can be
     * chained together.
     *
     * @param key
     *            The key of the multipart upload to abort.
     *
     * @return The updated AbortMultipartUploadRequest.
     */
    public AbortMultipartUploadRequest withKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * Returns the ID of the upload to abort.
     *
     * @return the ID of the upload to abort.
     */
    public String getUploadId() {
        return uploadId;
    }

    /**
     * Sets the ID of the upload to abort.
     */
    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    /**
     * Sets the ID of the multipart upload to abort, and returns this updated
     * AbortMultipartUploadRequest object so that additional method calls can be
     * chained together.
     *
     * @param uploadId
     *            The ID of the multipart upload to abort.
     *
     * @return The updated AbortMultipartUploadRequest object.
     */
    public AbortMultipartUploadRequest withUploadId(String uploadId) {
        this.uploadId = uploadId;
        return this;
    }
}
