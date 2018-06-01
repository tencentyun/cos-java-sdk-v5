package com.qcloud.cos.internal;

/**
 * Package private abstract base for all abstract handler that has server side
 * encryption (SSE) information.
 */
abstract class AbstractSSEHandler extends AbstractHandler implements ServerSideEncryptionResult {
    /**
     * Used to get access to the specific server side encryption (SSE) result
     * from the subclass.
     */
    protected abstract ServerSideEncryptionResult sseResult();

    @Override
    public final String getSSEAlgorithm() {
        ServerSideEncryptionResult result = sseResult();
        return result == null ? null : result.getSSEAlgorithm();
    }

    @Override
    public final void setSSEAlgorithm(String serverSideEncryption) {
        ServerSideEncryptionResult result = sseResult();
        if ( result != null )
            result.setSSEAlgorithm(serverSideEncryption);
    }

    @Override
    public final String getSSECustomerAlgorithm() {
        ServerSideEncryptionResult result = sseResult();
        return result == null ? null : result.getSSECustomerAlgorithm();
    }

    @Override
    public final void setSSECustomerAlgorithm(String algorithm) {
        ServerSideEncryptionResult result = sseResult();
        if (result != null) {
            result.setSSECustomerAlgorithm(algorithm);
        }
    }

    @Override
    public final String getSSECustomerKeyMd5() {
        ServerSideEncryptionResult result = sseResult();
        return result == null ? null : result.getSSECustomerKeyMd5();
    }

    @Override
    public final void setSSECustomerKeyMd5(String md5Digest) {
        ServerSideEncryptionResult result = sseResult();
        if (result != null) {
            result.setSSECustomerKeyMd5(md5Digest);
        }
    }
}