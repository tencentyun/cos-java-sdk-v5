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

import java.io.File;
import java.io.InputStream;

/**
 * <p>
 * Uploads a new object to the specified Qcloud COS bucket. The PutObjectRequest
 * optionally uploads object metadata and applies a canned access control policy
 * to the new object.
 * </p>
 * <p>
 * Qcloud COS never stores partial objects; if during this call an exception
 * wasn't thrown, the entire object was stored.
 * </p>
 * <p>
 * Depending on whether a file or input stream is being uploaded, this request
 * has slightly different behavior.
 * </p>
 * <p>
 * When uploading a file:
 * </p>
 * <ul>
 * <li>
 * The client automatically computes a checksum of the file. Qcloud COS uses
 * checksums to validate the data in each file.</li>
 * <li>
 * Using the file extension, Qcloud COS attempts to determine the correct content
 * type and content disposition to use for the object.</li>
 * </ul>
 * <p>
 * When uploading directly from an input stream, content length <b>must</b> be
 * specified before data can be uploaded to Qcloud COS. If not provided, the
 * library will <b>have to</b> buffer the contents of the input stream in order
 * to calculate it. Qcloud COS explicitly requires that the content length be
 * sent in the request headers before any of the data is sent.</li>
 * <p>
 * Qcloud COS is a distributed system. If Qcloud COS receives multiple write
 * requests for the same object nearly simultaneously, all of the objects might
 * be stored. However, only one object will obtain the key.
 * </p>
 * <p>
 * Note: Qcloud COS does not provide object locking; if this is needed, make sure
 * to build it into the application layer.
 * </p>
 * <p>
 * If the caller specifies a location constraint when creating a bucket, all
 * objects added to the bucket are stored in the same region as the bucket. For
 * example, if specifying a Europe (EU) region constraint for a bucket, all of
 * that bucket's objects are stored in the EU region.
 * </p>
 * <p>
 * The specified bucket must already exist and the caller must have
 * {@link Permission#Write} permission to the bucket to upload an object.
 * </p>
 *
 * @see PutObjectRequest#PutObjectRequest(String, String, File)
 * @see PutObjectRequest#PutObjectRequest(String, String, InputStream,
 *      ObjectMetadata)
 */
public class PutObjectRequest extends AbstractPutObjectRequest implements Serializable {
    /**
     * Constructs a new
     * {@link PutObjectRequest} object to upload a file to the
     * specified bucket and key. After constructing the request,
     * users may optionally specify object metadata or a canned ACL as well.
     *
     * @param bucketName
     *            The name of an existing bucket to which the new object will be
     *            uploaded.
     * @param key
     *            The key under which to store the new object.
     * @param file
     *            The path of the file to upload to Qcloud COS.
     */
    public PutObjectRequest(String bucketName, String key, File file) {
        super(bucketName, key, file);
    }

    /**
     * Constructs a new
     * {@link PutObjectRequest} object to perform a redirect for the
     * specified bucket and key. After constructing the request,
     * users may optionally specify object metadata or a canned ACL as well.
     *
     * @param bucketName
     *            The name of an existing bucket to which the new object will be
     *            uploaded.
     * @param key
     *            The key under which to store the new object.
     * @param redirectLocation
     public PutObjectRequest(String bucketName, String key, String redirectLocation) {
        super(bucketName, key, redirectLocation);
     }
     */ 

    /**
     * Constructs a new
     * {@link PutObjectRequest} object to upload a stream of data to
     * the specified bucket and key. After constructing the request,
     * users may optionally specify object metadata or a canned ACL as well.
     * <p>
     * Content length for the data stream <b>must</b> be
     * specified in the object metadata parameter; Qcloud COS requires it
     * be passed in before the data is uploaded. Failure to specify a content
     * length will cause the entire contents of the input stream to be buffered
     * locally in memory so that the content length can be calculated, which can
     * result in negative performance problems.
     * </p>
     *
     * @param bucketName
     *            The name of an existing bucket to which the new object will be
     *            uploaded.
     * @param key
     *            The key under which to store the new object.
     * @param input
     *            The stream of data to upload to Qcloud COS.
     * @param metadata
     *            The object metadata. At minimum this specifies the
     *            content length for the stream of data being uploaded.
     */
    public PutObjectRequest(String bucketName, String key, InputStream input,
            ObjectMetadata metadata) {
        super(bucketName, key, input, metadata);
    }

    /**
     * Returns a clone (as deep as possible) of this request object.
     */
    @Override
    public PutObjectRequest clone() {
        return this.copyPutObjectBaseTo(new PutObjectRequest(
            getBucketName(), getKey(), getFile()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest withBucketName(String bucketName) {
        return super.withBucketName(bucketName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest withKey(String key) {
        return super.withKey(key);
    }


    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest withStorageClass(String storageClass) {
        return super.withStorageClass(storageClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest withStorageClass(StorageClass storageClass) {
        return super.withStorageClass(storageClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest  withFile(File file) {
        return super.withFile(file);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest withMetadata(ObjectMetadata metadata) {
        return super.withMetadata(metadata);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest  withCannedAcl(CannedAccessControlList cannedAcl) {
        return super.withCannedAcl(cannedAcl);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest withAccessControlList(
            AccessControlList accessControlList) {
        return super.withAccessControlList(accessControlList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest  withInputStream(InputStream inputStream) {
        return super.withInputStream(inputStream);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest withRedirectLocation(String redirectLocation) {
        return super.withRedirectLocation(redirectLocation);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest withSSECustomerKey(SSECustomerKey sseKey) {
        return super.withSSECustomerKey(sseKey);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public PutObjectRequest withSSECOSKeyManagementParams(
            SSECOSKeyManagementParams sseCOSKeyManagementParams) {
        return super.withSSECOSKeyManagementParams(sseCOSKeyManagementParams);
    }
    
    
}
