package com.qcloud.cos.auth;

public class AnonymousCOSCredentials implements COSCredentials {
    private String appId;

    /**
     * 
     * @param appId           the appid which your resource belong to.
     * @deprecated appid should be included in bucket name. for example if your appid
     * is 125123123, previous bucket is ott. you should set bucket as ott-125123123.
     * 
     * use {@link AnonymousCOSCredentials#AnonymousCOSCredentials()}
     */
    @Deprecated
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
    
    public AnonymousCOSCredentials() {
        super();
        this.appId = null;
    }

    @Override
    public String getCOSAppId() {
        return appId;
    }

    @Override
    public String getCOSAccessKeyId() {
        return null;
    }

    @Override
    public String getCOSSecretKey() {
        return null;
    }

}
