package com.qcloud.cos.model;

/**
 * Server-side Encryption Algorithm.
 */
public enum SSEAlgorithm {
    AES256("AES256"),
    KMS("COS:kms"),
    ;

    private final String algorithm;

    public String getAlgorithm() {
        return algorithm;
    }

    private SSEAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String toString() {
        return algorithm;
    }

    /**
     * Returns the SSEAlgorithm enum corresponding to the given string;
     * or null if and only if the given algorithm is null.
     *
     * @throws IllegalArgumentException if the specified algorithm is not
     * supported.
     */
    public static SSEAlgorithm fromString(String algorithm) {
        if (algorithm == null)
            return null;
        for (SSEAlgorithm e: values()) {
            if (e.getAlgorithm().equals(algorithm))
                return e;
        }
        throw new IllegalArgumentException("Unsupported algorithm " + algorithm);
    }

    /**
     * Returns the default server side encryption algorithm, which is AES256.
     */
    public static SSEAlgorithm getDefault() {
        return AES256;
    }
}
