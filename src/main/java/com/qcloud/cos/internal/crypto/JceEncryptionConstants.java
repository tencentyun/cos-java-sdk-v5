package com.qcloud.cos.internal.crypto;

/**
 * Contains constants required by the JCE encryption library.
 */
public class JceEncryptionConstants {

    /** Name of the symmetric encryption algorithm */
    public static final String SYMMETRIC_KEY_ALGORITHM = "AES";

    /** Name of the algorithm, mode, and padding we will use in the symmetric cipher for encryption */
    public static final String SYMMETRIC_CIPHER_METHOD = "AES/CBC/PKCS5Padding";

    /** Minimum length of the generated symmetric key */
    public static final int SYMMETRIC_KEY_LENGTH = 256;

    /** AES cipher block size */
    public static final int SYMMETRIC_CIPHER_BLOCK_SIZE = 16;

}