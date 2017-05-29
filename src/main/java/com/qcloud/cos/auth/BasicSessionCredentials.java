package com.qcloud.cos.auth;

public class BasicSessionCredentials implements COSSessionCredentials{

	public BasicSessionCredentials(String appId, String accessKey, String secretKey, String sessionToken) {
		// TODO Auto-generated constructor stub
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

	@Override
	public String getCOSAppId() {
		// TODO Auto-generated method stub
		return appId;
	}

	@Override
	public String getCOSAccessKeyId() {
		// TODO Auto-generated method stub
		return accessKey;
	}

	@Override
	public String getCOSSecretKey() {
		// TODO Auto-generated method stub
		return secretKey;
	}

	@Override
	public String getSessionToken() {
		// TODO Auto-generated method stub
		return sessionToken;
	}

	private final String appId;
	private final String accessKey;
	private final String secretKey;
	private final String sessionToken;
}
