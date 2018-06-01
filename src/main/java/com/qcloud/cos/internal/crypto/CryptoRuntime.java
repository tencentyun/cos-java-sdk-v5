package com.qcloud.cos.internal.crypto;

import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;

import org.slf4j.LoggerFactory;

import static com.qcloud.cos.internal.crypto.COSKeyWrapScheme.RSA_ECB_OAEPWithSHA256AndMGF1Padding;

public class CryptoRuntime {
    static final String BOUNCY_CASTLE_PROVIDER = "BC";
    private static final String BC_PROVIDER_FQCN =
            "org.bouncycastle.jce.provider.BouncyCastleProvider";

    public static synchronized boolean isBouncyCastleAvailable() {
        return Security.getProvider(BOUNCY_CASTLE_PROVIDER) != null;
    }

    public static synchronized void enableBouncyCastle() {
        if (isBouncyCastleAvailable()) {
            return;
        }
        try {
            @SuppressWarnings("unchecked")
            Class<Provider> c = (Class<Provider>) Class.forName(BC_PROVIDER_FQCN);
            Provider provider = c.newInstance();
            Security.addProvider(provider);
        } catch (Exception e) {
            LoggerFactory.getLogger(CryptoRuntime.class).debug("Bouncy Castle not available", e);
        }
    }

    /**
     * Used only for unit test when the same class loader is used across multiple unit tests.
     */
    static void recheck() {
        recheckAesGcmAvailablility();
        recheckRsaKeyWrapAvailablility();
    }

    public static boolean isAesGcmAvailable() {
        return AesGcm.isAvailable;
    }

    public static void recheckAesGcmAvailablility() {
        AesGcm.recheck();
    }

    static boolean isRsaKeyWrapAvailable() {
        return RsaEcbOaepWithSHA256AndMGF1Padding.isAvailable;
    }

    private static void recheckRsaKeyWrapAvailablility() {
        RsaEcbOaepWithSHA256AndMGF1Padding.recheck();
    }

    private static final class AesGcm {
        static volatile boolean isAvailable = check();

        static boolean recheck() {
            return isAvailable = check();
        }

        private static boolean check() {
            try {
                Cipher.getInstance(ContentCryptoScheme.AES_GCM.getCipherAlgorithm(),
                        BOUNCY_CASTLE_PROVIDER);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
    private static final class RsaEcbOaepWithSHA256AndMGF1Padding {
        static volatile boolean isAvailable = check();

        static boolean recheck() {
            return isAvailable = check();
        }

        private static boolean check() {
            try {
                Cipher.getInstance(RSA_ECB_OAEPWithSHA256AndMGF1Padding, BOUNCY_CASTLE_PROVIDER);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
