package com.qcloud.cos.internal.crypto;

import java.util.Map;

final class KMSSecuredCEK extends SecuredCEK {
    static final String KEY_PROTECTION_MECHANISM = "kms";

    KMSSecuredCEK(byte[] encryptedKeyBlob, Map<String, String> matdesc) {
        super(encryptedKeyBlob, KEY_PROTECTION_MECHANISM, matdesc);
    }

    /**
     * Returns true if the specified key wrapping algorithm is
     * {@value #KEY_PROTECTION_MECHANISM}; false otherwise.
     */
    public static boolean isKMSKeyWrapped(String keyWrapAlgo) {
        return KEY_PROTECTION_MECHANISM.equals(keyWrapAlgo);
    }
}