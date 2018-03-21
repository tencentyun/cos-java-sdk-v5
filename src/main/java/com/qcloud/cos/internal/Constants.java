package com.qcloud.cos.internal;

public class Constants {
    /**
     * HTTP status code indicating that preconditions failed and thus the
     * request failed.
     */
    public static final int FAILED_PRECONDITION_STATUS_CODE = 412;

    /** Kilobytes */
    public static final int KB = 1024;

    /** Megabytes */
    public static final int MB = 1024 * KB;

    /** Gigabytes */
    public static final long GB = 1024 * MB;

    /** The maximum allowed parts in a multipart upload. */
    public static final int MAXIMUM_UPLOAD_PARTS = 10000;
    
    public static final int NO_SUCH_BUCKET_STATUS_CODE = 404;

    public static final int BUCKET_ACCESS_FORBIDDEN_STATUS_CODE = 403;

    public static final int BUCKET_REDIRECT_STATUS_CODE = 301;
    
    /** Represents a null version ID */
    public static final String NULL_VERSION_ID = "null";
    
    /** URL encoding for object keys when list object */
    public static final String URL_ENCODING = "url";
    
    /** Default encoding used for url encode */
    public static final String DEFAULT_ENCODING = "UTF-8";
}
