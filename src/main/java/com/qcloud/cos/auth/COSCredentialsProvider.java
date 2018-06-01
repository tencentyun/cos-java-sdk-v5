package com.qcloud.cos.auth;

public interface COSCredentialsProvider {
    public COSCredentials getCredentials();

    public void refresh();
}
