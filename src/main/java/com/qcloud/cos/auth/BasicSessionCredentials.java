package com.qcloud.cos.auth;

public class BasicSessionCredentials implements COSSessionCredentials {

    /**
     * @param appId the appid which your resource belong to.
     * @param accessKey your accessKey(SecretId). you can get it by https://console.qcloud.com/capi
     * @param secretKey your secretKey. you can get it by https://console.qcloud.com/capi
     * @param sessionToken the sessionToken you get from cam.
     * @deprecated appid should be included in bucket name. for example if your appid is 125123123,
     *             previous bucket is ott. you should set bucket as ott-125123123. use
     *             {@link BasicSessionCredentials#BasicSessionCredentials(String, String, String)}
     */
    @Deprecated
    public BasicSessionCredentials(String appId, String accessKey, String secretKey,
            String sessionToken) {
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
        if (sessionToken == null) {
            throw new IllegalArgumentException("Session Token cannot be null.");
        }

        this.appId = appId;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.sessionToken = sessionToken;
    }

    /**
     * @param appId the appid which your resource belong to.
     * @param accessKey your accessKey(SecretId). you can get it by https://console.qcloud.com/capi
     * @param secretKey your secretKey. you can get it by https://console.qcloud.com/capi
     * @param sessionToken the sessionToken you get from cam.
     */
    public BasicSessionCredentials(String accessKey, String secretKey, String sessionToken) {
        super();

        if (accessKey == null) {
            throw new IllegalArgumentException("Access key cannot be null.");
        }
        if (secretKey == null) {
            throw new IllegalArgumentException("Secret key cannot be null.");
        }
        if (sessionToken == null) {
            throw new IllegalArgumentException("Session Token cannot be null.");
        }

        this.appId = null;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.sessionToken = sessionToken;
    }

    @Override
    public String getCOSAppId() {
        return appId;
    };

    @Override
    public String getCOSAccessKeyId() {
        return accessKey;
    }

    @Override
    public String getCOSSecretKey() {
        return secretKey;
    }

    @Override
    public String getSessionToken() {
        return sessionToken;
    }

    private final String appId;
    private final String accessKey;
    private final String secretKey;
    private final String sessionToken;


}
