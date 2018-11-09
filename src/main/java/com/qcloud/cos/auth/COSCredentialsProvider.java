package com.qcloud.cos.auth;

public interface COSCredentialsProvider {
    /**
     * Returns COSCredentials which the caller can use to authorize an COS request. Each
     * implementation of COSCredentialsProvider can chose its own strategy for get credentials.
     *
     * @return COSCredentials which the caller can use to sign request.
     */
    public COSCredentials getCredentials();
}
