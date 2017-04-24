package com.qcloud.cos.transfer;

import java.io.IOException;

/**
 * Multiple file download of an entire virtual directory.
 */
public interface  MultipleFileDownload extends Transfer {

    /**
     * Returns the key prefix of the virtual directory being downloaded.
     */
    public String getKeyPrefix();
    
    /**
     * Returns the name of the bucket from which files are downloaded.
     */
    public String getBucketName();   
    
    /**
     * Cancels this download.
     *
     * @throws IOException
     */
    public void abort() throws IOException;
}
