package com.qcloud.cos.model;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

public class SetBucketPolicyRequest extends CosServiceRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The name of the COS bucket whose policy is being set. */
    private String bucketName;

    /** The policy to apply to the specified bucket. */
    private String policyText;
    
    /**
     * Creates a new request object, ready to be executed to set COS
     * bucket's policy.
     *
     * @param bucketName
     *            The name of the COS bucket whose policy is being set.
     * @param policyText
     *            The policy to apply to the specified bucket.
     */
    public SetBucketPolicyRequest(String bucketName, String policyText) {
        this.bucketName = bucketName;
        this.policyText = policyText;
    }

    /**
     * Returns the name of the COS bucket whose policy is being set.
     *
     * @return The name of the COS bucket whose policy is being set.
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Sets the name of the COS bucket whose policy is being set.
     *
     * @param bucketName
     *            The name of the COS bucket whose policy is being set.
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Sets the name of the COS bucket whose policy is being set, and
     * returns the updated request object so that additional method calls can be
     * chained together.
     *
     * @param bucketName
     *            The name of the COS bucket whose policy is being set.
     *
     * @return The updated request object so that additional method calls can be
     *         chained together.
     */
    public SetBucketPolicyRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    /**
     * Returns the policy to apply to the specified bucket.
     *
     * @return The policy to apply to the specified bucket.
     */
    public String getPolicyText() {
        return policyText;
    }

    /**
     * Sets the policy to apply to the specified bucket.
     *
     * @param policyText
     *            The policy to apply to the specified bucket.
     */
    public void setPolicyText(String policyText) {
        this.policyText = policyText;
    }

    /**
     * Sets the policy to apply to the specified bucket, and returns the updated
     * request object so that additional method calls can be chained together.
     *
     * @param policyText
     *            The policy to apply to the specified bucket.
     *
     * @return The updated request object, so that additional method calls can
     *         be chained together.
     */
    public SetBucketPolicyRequest withPolicyText(String policyText) {
        setPolicyText(policyText);
        return this;
    }
}
