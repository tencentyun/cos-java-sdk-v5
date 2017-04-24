package com.qcloud.cos.auth;

public interface COSCredentials {
   
    /**
     * Returns the COS AppId for this credentials object. 
     * 
     * @return The COS AppId for this credentials object. 
     */
    public String getCOSAppId();


    /**
     * Returns the COS access key ID for this credentials object. 
     * 
     * @return The COS access key ID for this credentials object. 
     */
    public String getCOSAccessKeyId();

    /**
     * Returns the COS secret access key for this credentials object.
     * 
     * @return The COS secret access key for this credentials object.
     */
    public String getCOSSecretKey();
}
