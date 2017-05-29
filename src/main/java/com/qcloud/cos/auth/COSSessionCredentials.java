package com.qcloud.cos.auth;

public interface COSSessionCredentials extends COSCredentials {

    /**
     * Returns the session token for this session.
     */
    public String getSessionToken();
}
