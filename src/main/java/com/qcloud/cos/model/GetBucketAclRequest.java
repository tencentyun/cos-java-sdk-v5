package com.qcloud.cos.model;

/**
 * Request object containing all the options for requesting a bucket's Access Control List (ACL).
 */
public class GetBucketAclRequest extends GenericBucketRequest {

    public GetBucketAclRequest(String bucketName) {
        super(bucketName);
    }

}
