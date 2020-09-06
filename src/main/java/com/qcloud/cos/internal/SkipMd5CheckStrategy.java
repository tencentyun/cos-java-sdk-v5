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

import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadPartRequest;

/**
 * Logic for determining whether MD5 checksum validation should be performed or not.
 */
public class SkipMd5CheckStrategy {

    /**
     * System property to disable MD5 validation for GetObject. Any value set for this property will
     * disable validation.
     */
    public static final String DISABLE_GET_OBJECT_MD5_VALIDATION_PROPERTY =
            "com.Qcloud.services.cos.disableGetObjectMD5Validation";

    /**
     * System property to disable MD5 validation for both PutObject and UploadPart. Any value set
     * for this property will disable validation.
     */
    public static final String DISABLE_PUT_OBJECT_MD5_VALIDATION_PROPERTY =
            "com.Qcloud.services.cos.disablePutObjectMD5Validation";

    public static final SkipMd5CheckStrategy INSTANCE = new SkipMd5CheckStrategy();

    // Singleton
    private SkipMd5CheckStrategy() {}

    /**
     * Determines whether the client should use the {@link Headers#ETAG} header returned by COS to
     * validate the integrity of the message client side based on the server response. We skip the
     * client side check if any of the following conditions are true:
     * <ol>
     * <li>The system property {@value #DISABLE_GET_OBJECT_MD5_VALIDATION_PROPERTY} is set</li>
     * <li>The request involves SSE-C or SSE-KMS</li>
     * <li>The Etag header is missing</li>
     * <li>The Etag indicates that the object was created by a MultiPart Upload</li>
     * </ol>
     * 
     * @return True if client side validation should be skipped, false otherwise.
     */
    public boolean skipClientSideValidationPerGetResponse(ObjectMetadata metadata) {
        if (isGetObjectMd5ValidationDisabledByProperty()) {
            return true;
        }
        return skipClientSideValidationPerResponse(metadata);
    }

    /**
     * Determines whether the client should use the {@link Headers#ETAG} header returned by COS to
     * validate the integrity of the message client side based on the server response. We skip the
     * client side check if any of the following conditions are true:
     * <ol>
     * <li>The system property {@value #DISABLE_PUT_OBJECT_MD5_VALIDATION_PROPERTY} is set</li>
     * <li>The request involves SSE-C or SSE-KMS</li>
     * <li>The Etag header is missing</li>
     * </ol>
     * 
     * @return True if client side validation should be skipped, false otherwise.
     */
    public boolean skipClientSideValidationPerPutResponse(ObjectMetadata metadata) {
        if (isPutObjectMd5ValidationDisabledByProperty()) {
            return true;
        }
        return skipClientSideValidationPerResponse(metadata);
    }

    /**
     * Determines whether the client should use the {@link Headers#ETAG} header returned by COS to
     * validate the integrity of the message client side based on the server response. We skip the
     * client side check if any of the following conditions are true:
     * <ol>
     * <li>The system property {@value #DISABLE_PUT_OBJECT_MD5_VALIDATION_PROPERTY} is set</li>
     * <li>The request involves SSE-C or SSE-KMS</li>
     * <li>The Etag header is missing</li>
     * </ol>
     * 
     * @return True if client side validation should be skipped, false otherwise.
     */
    public boolean skipClientSideValidationPerUploadPartResponse(ObjectMetadata metadata) {
        return skipClientSideValidationPerPutResponse(metadata);
    }

    /**
     * Conveience method to determine whether to do client side validation of a GetObject call based
     * on both the request and the response. See
     * {@link #skipClientSideValidationPerRequest(GetObjectRequest)} and
     * {@link #skipClientSideValidationPerGetResponse(ObjectMetadata)} for more details on the
     * criterion.
     * 
     * @param request Original {@link GetObjectRequest}
     * @param returnedMetadata Metadata returned in {@link COSObject}
     * @return True if client side validation should be skipped, false otherwise.
     */

    public boolean skipClientSideValidation(GetObjectRequest request,
            ObjectMetadata returnedMetadata) {
        return skipClientSideValidationPerRequest(request)
                || skipClientSideValidationPerGetResponse(returnedMetadata);
    }


    /**
     * Determines whether the client should use the {@link Headers#ETAG} header returned by COS to
     * validate the integrity of the message client side. We skip the client side check if any of
     * the following conditions are true:
     * <ol>
     * <li>The system property {@value #DISABLE_PUT_OBJECT_MD5_VALIDATION_PROPERTY} is set</li>
     * <li>The request involves SSE-C or SSE-KMS</li>
     * </ol>
     * 
     * @return True if client side validation should be skipped, false otherwise.
     */
    public boolean skipClientSideValidationPerRequest(PutObjectRequest request) {
        if (isPutObjectMd5ValidationDisabledByProperty()) {
            return true;
        }
        return putRequestInvolvesSse(request) || metadataInvolvesSse(request.getMetadata());
    }

    /**
     * Determines whether the client should use the {@link Headers#ETAG} header returned by COS to
     * validate the integrity of the message client side. We skip the client side check if any of
     * the following conditions are true:
     * <ol>
     * <li>The system property {@value #DISABLE_PUT_OBJECT_MD5_VALIDATION_PROPERTY} is set</li>
     * <li>The request involves SSE-C or SSE-KMS</li>
     * </ol>
     * 
     * @return True if client side validation should be skipped, false otherwise.
     */
    public boolean skipClientSideValidationPerRequest(UploadPartRequest request) {
        if (isPutObjectMd5ValidationDisabledByProperty()) {
            return true;
        }
        return request.getSSECustomerKey() != null;
    }

    /**
     * Determines whether the client should calculate and send the {@link Headers#CONTENT_MD5}
     * header to be validated by COS per the request.
     * <p>
     * Currently we always try and do server side validation unless it's been explicitly disabled by
     * the {@value #DISABLE_PUT_OBJECT_MD5_VALIDATION_PROPERTY} property. Whether or not we actually
     * calculate the MD5 header is determined in the client based on the source of the data (i.e. if
     * it's a file we calculate, if not then we don't)
     * </p>
     */
    public boolean skipServerSideValidation(PutObjectRequest request) {
        if (isPutObjectMd5ValidationDisabledByProperty()) {
            return true;
        }
        return false;
    }

    /**
     * Determines whether the client should calculate and send the {@link Headers#CONTENT_MD5}
     * header to be validated by COS per the request.
     * <p>
     * Currently we always try and do server side validation unless it's been explicitly disabled by
     * the {@value #DISABLE_PUT_OBJECT_MD5_VALIDATION_PROPERTY} property. Whether or not we actually
     * calculate the MD5 header is determined in the client based on the source of the data (i.e. if
     * it's a file we calculate, if not then we don't)
     * </p>
     */
    /*
     * public boolean skipServerSideValidation(UploadPartRequest request) { if
     * (isPutObjectMd5ValidationDisabledByProperty()) { return true; } return false; }
     */

    /**
     * Based on the given {@link GetObjectRequest}, returns whether the specified request should
     * skip MD5 check on the requested object content. Specifically, MD5 check should be skipped if
     * one of the following conditions are true:
     * <ol>
     * <li>The system property {@value #DISABLE_GET_OBJECT_MD5_VALIDATION_PROPERTY} is set.</li>
     * <li>The request is a range-get operation</li>
     * <li>The request is a GET object operation that involves SSE-C</li>
     * </ol>
     * Otherwise, MD5 check should not be skipped.
     */
    public boolean skipClientSideValidationPerRequest(GetObjectRequest request) {
        if (isGetObjectMd5ValidationDisabledByProperty()) {
            return true;
        }
        // Skip MD5 check for range get
        if (request.getRange() != null) {
            return true;
        }
        return false;
    }

    private boolean skipClientSideValidationPerResponse(ObjectMetadata metadata) {
        if (metadata == null) {
            return true;
        }
        // If Etag is not provided or was computed from a multipart upload then skip the check, the
        // etag won't be the MD5 of the original content
        if (metadata.getETag() == null || isMultipartUploadETag(metadata.getETag())
                || isV4ETag(metadata.getETag()) || isSSECustomerResponse(metadata)) {
            return true;
        }
        return false;
        // return metadataInvolvesSse(metadata);
    }

    private boolean isSSECustomerResponse(ObjectMetadata metadata) {
        return null != metadata.getSSECustomerAlgorithm() && null != metadata.getSSECustomerKeyMd5();
    }

    private boolean isGetObjectMd5ValidationDisabledByProperty() {
        String propertyValue = System.getProperty(DISABLE_GET_OBJECT_MD5_VALIDATION_PROPERTY);
        if (propertyValue != null && propertyValue.equalsIgnoreCase("false")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isPutObjectMd5ValidationDisabledByProperty() {
        return System.getProperty(DISABLE_PUT_OBJECT_MD5_VALIDATION_PROPERTY) != null;
    }

    /**
     * If SSE-C or SSE-KMS is involved then the Etag will be the MD5 of the ciphertext not the
     * plaintext so we can't validate it client side. Plain SSE with COS managed keys will return an
     * Etag that does match the MD5 of the plaintext so it's still eligible for client side
     * validation.
     * 
     * @param metadata Metadata of request or response
     * @return True if the metadata indicates that SSE-C or SSE-KMS is used. False otherwise
     */
    private boolean metadataInvolvesSse(ObjectMetadata metadata) {
        if (metadata == null) {
            return false;
        }
        return containsNonNull(metadata.getSSECustomerAlgorithm(), metadata.getSSECustomerKeyMd5(),
                metadata.getSSECOSKmsKeyId());
    }

    /**
     * @param request
     * @return True if {@link PutObjectRequest} has been configured to use SSE-C or SSE-KMS
     */
    private boolean putRequestInvolvesSse(PutObjectRequest request) {
        return containsNonNull(request.getSSECustomerKey(), request.getSSECOSKeyManagementParams());
    }

    /**
     * Returns true if the specified ETag was from a multipart upload.
     *
     * @param eTag The ETag to test.
     * @return True if the specified ETag was from a multipart upload, otherwise false it if belongs
     *         to an object that was uploaded in a single part.
     */
    private static boolean isMultipartUploadETag(String eTag) {
        return eTag.contains("-");
    }

    // 判断下etag的长度, V5的Etag长度是32(MD5), V4的是40(sha)
    // v4的etag则跳过校验
    private static boolean isV4ETag(String eTag) {
        if (eTag.length() != 32) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Helper method to avoid long chains of non null checks
     * 
     * @param items
     * @return True if any of the provided items is not null. False if all items are null.
     */
    private static boolean containsNonNull(Object... items) {
        for (Object item : items) {
            if (item != null) {
                return true;
            }
        }
        return false;
    }
}

