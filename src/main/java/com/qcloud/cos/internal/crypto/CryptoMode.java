package com.qcloud.cos.internal.crypto;

/**
 * Denotes the different cryptographic modes available for securing an COS object via client-side
 * encryption. Crypto mode can be configured via {@link CryptoConfiguration} when the COS encryption
 * client is constructed.
 */
public enum CryptoMode {
    /**
     * Authenticated encryption mode using AES/GCM, AESWrap, etc. Please note the limitation on the
     * maximum message size in bytes that can be encrypted under this mode is 2^36-32, or ~64G, due
     * to the security limitation of AES/GCM as recommended by NIST.
     */
    AuthenticatedEncryption,
    /**
     * Strictly enforce the use of authenticated encryption via AES/GCM, AESWrap, etc., and will
     * throw security exception if an COS object retrieved is found to be not protected using
     * authenticated encryption. This means range-get operation is not supported in this mode, since
     * range-get is not authenticated.
     * <p>
     * Please note the limitation on the maximum message size in bytes that can be encrypted under
     * this mode is 2^36-32, or ~64G, due to the security limitation of AES/GCM as recommended by
     * NIST.
     */
    StrictAuthenticatedEncryption,;
}
