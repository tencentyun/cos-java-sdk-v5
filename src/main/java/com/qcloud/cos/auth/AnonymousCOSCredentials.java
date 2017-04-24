package com.qcloud.cos.auth;

public class AnonymousCOSCredentials implements COSCredentials {
    private String appId;

    public AnonymousCOSCredentials(String appId) {
        super();
        if (appId == null) {
            throw new IllegalArgumentException("Appid cannot be null.");
        }
        try {
            Long.valueOf(appId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Appid is invalid num str.");
        }
        this.appId = appId;
    }

    @Override
    public String getCOSAppId() {
        return appId;
    }

    @Override
    public String getCOSAccessKeyId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCOSSecretKey() {
        // TODO Auto-generated method stub
        return null;
    }

}
