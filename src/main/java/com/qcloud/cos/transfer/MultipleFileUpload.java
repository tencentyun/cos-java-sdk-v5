package com.qcloud.cos.transfer;

import java.util.Collection;

/**
 * Multiple file download of an entire virtual directory.
 */
public interface  MultipleFileUpload extends Transfer {

    /**
     * Returns the key prefix of the virtual directory being uploaded.
     */
    public String getKeyPrefix();

    /**
     * Returns the name of the bucket to which files are uploaded.
     */
    public String getBucketName();

    /**
     * Returns a collection of sub transfers associated with the multi file upload.
     */
    public Collection<? extends Upload> getSubTransfers();

}