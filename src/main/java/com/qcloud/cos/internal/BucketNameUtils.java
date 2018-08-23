package com.qcloud.cos.internal;

public enum BucketNameUtils {
    ;
    private static final int MIN_BUCKET_NAME_LENGTH = 1;
    private static final int MAX_BUCKET_NAME_LENGTH = 40;

    public static void validateBucketName(final String bucketName) throws IllegalArgumentException {
        if (bucketName == null) {
            throw new IllegalArgumentException("Bucket Name cannot be null");
        }
        String bucketNameNotContainAppid = bucketName;
        if (bucketName.contains("-") && bucketName.lastIndexOf("-") != 0) {
            bucketNameNotContainAppid = bucketName.substring(0, bucketName.lastIndexOf("-"));
        }
        if (bucketNameNotContainAppid.length() < MIN_BUCKET_NAME_LENGTH
                || bucketNameNotContainAppid.length() > MAX_BUCKET_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "bucketName length must between 1 and 40 characters long");
        }

        for (int i = 0; i < bucketNameNotContainAppid.length(); ++i) {
            char next = bucketNameNotContainAppid.charAt(i);
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
