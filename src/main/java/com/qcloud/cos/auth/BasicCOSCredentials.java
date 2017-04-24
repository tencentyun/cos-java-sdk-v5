package com.qcloud.cos.auth;

public class BasicCOSCredentials implements COSCredentials {
    private final String appId;
    private final String accessKey;
    private final String secretKey;

    public BasicCOSCredentials(String appId, String accessKey, String secretKey) {
        super();
        if (appId == null) {
            throw new IllegalArgumentException("Appid cannot be null.");
        }
        try {
            Long.valueOf(appId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Appid is invalid num str.");
        }

        if (accessKey == null) {
            throw new IllegalArgumentException("Access key cannot be null.");
        }
        if (secretKey == null) {
            throw new IllegalArgumentException("Secret key cannot be null.");
        }
        this.appId = appId;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Override
    public String getCOSAppId() {
        return appId;
    }

    @Override
    public String getCOSAccessKeyId() {
        return accessKey;
    }

    @Override
    public String getCOSSecretKey() {
        return secretKey;
    }

}
