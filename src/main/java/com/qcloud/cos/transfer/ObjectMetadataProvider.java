package com.qcloud.cos.transfer;

import java.io.File;

import com.qcloud.cos.model.ObjectMetadata;

/**
 * This is the callback interface which is used by TransferManager.uploadDirectory and
 * TransferManager.uploadFileList. The callback is invoked for each file that is uploaded by
 * <code>TransferManager</code> and given an opportunity to specify the metadata for each file.
 */
public interface ObjectMetadataProvider {
    
    /*
     * This method is called for every file that is uploaded by <code>TransferManager</code>
     * and gives an opportunity to specify the metadata for the file.
     * 
     * @param file
     *          The file being uploaded. 
     * 
     * @param metadata
     *          The default metadata for the file. You can modify this object to specify
     * your own metadata.
     */
    public void provideObjectMetadata(final File file, final ObjectMetadata metadata);
    
}