package com.qcloud.cos.model;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EncryptedPutObjectRequest extends PutObjectRequest
        implements MaterialsDescriptionProvider, Serializable {
    /**
     * description of encryption materials to be used with this request.
     */
    private Map<String, String> materialsDescription;

    public EncryptedPutObjectRequest(String bucketName, String key, File file) {
        super(bucketName, key, file);
    }

    public EncryptedPutObjectRequest(String bucketName, String key, String redirectLocation) {
        super(bucketName, key, redirectLocation);
    }

    public EncryptedPutObjectRequest(String bucketName, String key, InputStream input,
            ObjectMetadata metadata) {
        super(bucketName, key, input, metadata);
    }

    @Override
    public Map<String, String> getMaterialsDescription() {
        return materialsDescription;
    }

    /**
     * sets the materials description for the encryption materials to be used with the current
     * PutObjectRequest.
     * 
     * @param materialsDescription the materialsDescription to set
     */
    public void setMaterialsDescription(Map<String, String> materialsDescription) {
        this.materialsDescription = materialsDescription == null ? null
                : Collections.unmodifiableMap(new HashMap<String, String>(materialsDescription));
    }

    /**
     * sets the materials description for the encryption materials to be used with the current
     * PutObjectRequest.
     * 
     * @param materialsDescription the materialsDescription to set
     */
    public EncryptedPutObjectRequest withMaterialsDescription(
            Map<String, String> materialsDescription) {
        setMaterialsDescription(materialsDescription);
        return this;
    }

    /**
     * Returns a clone (as deep as possible) of this request object.
     */
    @Override
    public EncryptedPutObjectRequest clone() {
        final EncryptedPutObjectRequest cloned =
                new EncryptedPutObjectRequest(getBucketName(), getKey(), getFile());
        super.copyPutObjectBaseTo(cloned);
        final Map<String, String> materialsDescription = getMaterialsDescription();
        cloned.withMaterialsDescription(materialsDescription == null ? null
                : new HashMap<String, String>(materialsDescription));
        return cloned;
    }
}
