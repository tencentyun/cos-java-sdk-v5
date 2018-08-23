package com.qcloud.cos.model;

import java.io.Serializable;


public class BucketPolicy implements Serializable {
    private static final long serialVersionUID = 1L;
    /** The raw, policy JSON text, as returned by COS */
    private String policyText;

    /**
     * Gets the raw policy JSON text as returned by COS. If no policy has been applied to the
     * specified bucket, the policy text will be null.
     * 
     * @return The raw policy JSON text as returned by COS. If no policy has been applied to the
     *         specified bucket, this method returns null policy text.
     * 
     * @see BucketPolicy#setPolicyText(String)
     */
    public String getPolicyText() {
        return policyText;
    }

    /**
     * Sets the raw policy JSON text. A bucket will have no policy text unless the policy text is
     * explicitly provided through this method.
     *
     * @param policyText The raw policy JSON text.
     * 
     * @see BucketPolicy#getPolicyText()
     */
    public void setPolicyText(String policyText) {
        this.policyText = policyText;
    }
}
