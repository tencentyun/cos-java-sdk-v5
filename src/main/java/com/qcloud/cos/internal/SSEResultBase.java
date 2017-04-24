package com.qcloud.cos.internal;

/**
 * Common abstract base class for result that contains server side encryption
 * (SSE) information.
 */
public abstract class SSEResultBase implements ServerSideEncryptionResult {
    private String sseAlgorithm;
    private String sseCustomerAlgorithm;
    private String sseCustomerKeyMD5;

    @Override
    public final String getSSEAlgorithm() {
        return sseAlgorithm;
    }

    @Override
    public final void setSSEAlgorithm(String algorithm) {
        this.sseAlgorithm = algorithm;
    }

    @Override
    public final String getSSECustomerAlgorithm() {
        return sseCustomerAlgorithm;
    }

    @Override
    public final void setSSECustomerAlgorithm(String algorithm) {
        this.sseCustomerAlgorithm = algorithm;
    }

    @Override
    public final String getSSECustomerKeyMd5() {
        return sseCustomerKeyMD5;
    }

    @Override
    public final void setSSECustomerKeyMd5(String md5) {
        this.sseCustomerKeyMD5 = md5;
    }

    /**
     * @deprecated Replaced by {@link #getSSEAlgorithm()}
     */
    @Deprecated
    public final String getServerSideEncryption() {
        return sseAlgorithm;
    }
}