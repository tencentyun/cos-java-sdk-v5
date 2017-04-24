package com.qcloud.cos.internal;

import java.io.InputStream;

import com.qcloud.cos.COSClient;

/**
 * This wrapper input stream holds a reference to the service client. This is
 * mainly done to avoid the COSClient object being garbage
 * collected when the client reads data from the input stream.
 * 
 */
public class ServiceClientHolderInputStream extends SdkFilterInputStream {

    
    @SuppressWarnings("unused")
    private COSClient client;

    public ServiceClientHolderInputStream(InputStream in,
            COSClient client) {
        super(in);
        this.client = client;
    }   
}
