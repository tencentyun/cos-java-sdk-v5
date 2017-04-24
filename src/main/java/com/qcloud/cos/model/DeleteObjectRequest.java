package com.qcloud.cos.model;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

public class DeleteObjectRequest extends CosServiceRequest implements Serializable {
    /**
     * The name of the Qcloud COS bucket containing the object to delete.
     */
    private String bucketName;

    /**
     * The key of the object to delete.
     */
    private String key;


    /**
     * Constructs a new {@link DeleteObjectRequest}, specifying the object's bucket name and key.
     * 
     * @param bucketName The name of the Qcloud COS bucket containing the object to delete.
     * @param key The key of the object to delete.
     */
    public DeleteObjectRequest(String bucketName, String key) {
        setBucketName(bucketName);
        setKey(key);
    }


    /**
     * Gets the name of the Qcloud COS bucket containing the object to delete.
     * 
     * @return The name of the Qcloud COS bucket containing the object to delete.
     * 
     * @see DeleteObjectRequest#setBucketName(String)
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Sets the name of the Qcloud COS bucket containing the object to delete.
     * 
     * @param bucketName The name of the Qcloud COS bucket containing the object to delete.
     * @see DeleteObjectRequest#getBucketName()
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Sets the name of the Qcloud COS bucket containing the object to delete and returns this
     * object, enabling additional method calls to be chained together.
     * 
     * @param bucketName The name of the Qcloud COS bucket containing the object to delete.
     * 
     * @return The updated {@link DeleteObjectRequest} object, enabling additional method calls to
     *         be chained together.
     */
    public DeleteObjectRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    /**
     * Gets the key of the object to delete.
     * 
     * @return The key of the object to delete.
     * 
     * @see DeleteObjectRequest#setKey(String)
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key of the object to delete.
     * 
     * @param key The key of the object to delete.
     * 
     * @see DeleteObjectRequest#getKey()
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets the key of the object to delete and returns this object, enabling additional method
     * calls to be chained together.
     * 
     * @param key The key of the object to delete.
     * 
     * @return The updated {@link DeleteObjectRequest} object, enabling additional method calls to
     *         chained together.
     */
    public DeleteObjectRequest withKey(String key) {
        setKey(key);
        return this;
    }
}
