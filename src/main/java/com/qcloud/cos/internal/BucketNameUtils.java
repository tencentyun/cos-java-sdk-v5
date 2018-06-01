package com.qcloud.cos.internal;

public enum BucketNameUtils {
    ;
    private static final int MIN_BUCKET_NAME_LENGTH = 1;
    private static final int MAX_BUCKET_NAME_LENGTH = 40;

    public static void validateBucketName(final String bucketName) throws IllegalArgumentException {
        if (bucketName == null) {
            throw new IllegalArgumentException("Bucket Name cannot be null");
        }
        if (bucketName.length() < MIN_BUCKET_NAME_LENGTH
                || bucketName.length() > MAX_BUCKET_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "bucketName length must between 1 and 40 characters long");
        }

        for (int i = 0; i < bucketName.length(); ++i) {
            char next = bucketName.charAt(i);
            if (i == 0 && next == '-') { 
                throw new IllegalArgumentException("Bucket name can not start with -");
            }
            if (next == '-') 
                continue;
            if (next >= 'a' && next <= 'z')
                continue;

            if (next >= '0' && next <= '9')
                continue;

            if (next >= 'A' && next <= 'Z') {
                throw new IllegalArgumentException(
                        "Bucket name should not contain uppercase characters");
            }

            if (next == ' ' || next == '\t' || next == '\r' || next == '\n') {
                throw new IllegalArgumentException("Bucket name should not contain whitespace");
            }
            
            throw new IllegalArgumentException(
                    "Bucket name only should contain lowercase characters, num and -");
        }
    }
}
