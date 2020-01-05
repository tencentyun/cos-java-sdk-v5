package com.qcloud.cos.auth;

public class InstanceProfileCredentials extends BasicSessionCredentials {
    private static final long DEFAULT_EXPIRATION_DURATION_SECONDS = 1800;

    private final long expiredTime;             // Unit is second
    private long expirationDurationSeconds = DEFAULT_EXPIRATION_DURATION_SECONDS;

    public InstanceProfileCredentials(String accessKey, String secretKey, String sessionToken, long expiredTime) {
        super(accessKey, secretKey, sessionToken);
        this.expiredTime = expiredTime;
        this.expirationDurationSeconds = this.expiredTime - (System.currentTimeMillis() / 1000);
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public boolean isExpired() {
        //The 10 seconds before the expired time is defined as expired.
        return System.currentTimeMillis() >= ((this.expiredTime * 1000) - (30 * 1000));
    }

    public boolean willSoonExpire() {
        // The
        return (this.expirationDurationSeconds * 0.2) >= (this.expiredTime - (System.currentTimeMillis() / 1000.0));
    }
}
