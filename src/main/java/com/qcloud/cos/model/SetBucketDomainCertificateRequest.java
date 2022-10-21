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

import com.qcloud.cos.internal.CosServiceRequest;

import java.io.Serializable;


/**
 * Request object for the parameters to set a bucket's domain certificate.
 */
public class SetBucketDomainCertificateRequest extends CosServiceRequest implements Serializable {

    /** The name of the bucket whose domain certificate is being set. */
    private String BucketName;

    /** The domain certificate for the specified bucket. */
    private BucketPutDomainCertificate bucketPutDomainCertificate;

    public SetBucketDomainCertificateRequest(){}

    /**
     * Creates a new request object, ready to be executed to set the specified
     * bucket's domain certificate.
     *
     * @param bucketName
     *            The name of the bucket whose domain certificate is being set.
     * @param domainCertificate
     *            The new domain certificate for the specified bucket.
     */
    public SetBucketDomainCertificateRequest(String bucketName, BucketPutDomainCertificate domainCertificate) {
        this.BucketName = bucketName;
        this.bucketPutDomainCertificate = domainCertificate;
    }

    /**
     * Sets the name of the bucket whose domain certificate is to be updated.
     *
     * @param bucketName
     *            The name of the bucket whose domain certificate is being set.
     */
    public void setBucketName(String bucketName) {
        this.BucketName = bucketName;
    }

    /**
     * Returns the name of the bucket whose domain certificate is being set.
     *
     * @return The name of the bucket whose domain certificate is being set.
     */
    public String getBucketName() {
        return this.BucketName;
    }

    /**
     * Sets the name of the bucket whose domain certificate is being set and
     * returns this updated request object so that additional method calls can
     * be chained together.
     *
     * @param bucketName
     *            The name of the bucket whose domain configuration is being
     *            set.
     * @return This updated request object so that additional method calls can
     *         be chained together.
     */
    public SetBucketDomainCertificateRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    /**
     * Sets the domain certificate to send as part of this request.
     *
     * @param domainCertificate
     *            The domain certificate to set for the specified bucket.
     */
    public void setBucketPutDomainCertificate(BucketPutDomainCertificate domainCertificate) {
        this.bucketPutDomainCertificate = domainCertificate;
    }

    /**
     * Returns the domain certificate to send as part of this request.
     *
     * @return The domain certificate to set for the specified bucket.
     */
    public BucketPutDomainCertificate getBucketPutDomainCertificate() {
        return this.bucketPutDomainCertificate;
    }

    /**
     * Sets the domain certificate to send as part of this request, and
     * returns this updated request object so that additional method calls can
     * be chained together.
     *
     * @param domainCertificate
     *            The domain certificate to set for the specified bucket.
     *
     * @return This updated request object so that additional method calls can
     *         be chained together.
     */
    public SetBucketDomainCertificateRequest withDomainCertificate(BucketPutDomainCertificate domainCertificate) {
        setBucketPutDomainCertificate(domainCertificate);
        return this;
    }
}
