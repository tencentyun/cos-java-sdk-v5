package com.qcloud.cos.model.ciModel.common;


import com.qcloud.cos.internal.CIPicServiceRequest;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;

import java.util.Map;

public class CImageProcessRequest extends CIPicServiceRequest {
    /**
     * The name of the Qcloud COS bucket containing the object to image process
     */
    private String bucketName;

    /**
     * The key of the object to image process.
     */
    private String key;
    /**
     * pic operations
     */
    private PicOperations picOperations;

    private Map<String, String> queryParams;

    /**
     * Constructs a new {@link CImageProcessRequest}, specifying the object's bucket name and key.
     *
     * @param bucketName The name of the Qcloud COS bucket containing the object to image process
     * @param key        The key of the object to image process
     */
    public CImageProcessRequest(String bucketName, String key) {
        setBucketName(bucketName);
        setKey(key);
    }


    /**
     * Gets the name of the Qcloud COS bucket containing the object to image process.
     *
     * @return The name of the Qcloud COS bucket containing the object to image process.
     * @see DeleteObjectRequest#setBucketName(String)
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Sets the name of the Qcloud COS bucket containing the object to image process
     *
     * @param bucketName The name of the Qcloud COS bucket containing the object to image process
     * @see DeleteObjectRequest#getBucketName()
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Sets the name of the Qcloud COS bucket containing the object to image process and returns this
     * object, enabling additional method calls to be chained together.
     *
     * @param bucketName The name of the Qcloud COS bucket containing the object to image process
     * @return The updated {@link DeleteObjectRequest} object, enabling additional method calls to
     * be chained together.
     */
    public CImageProcessRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    /**
     * Gets the key of the object to image process.
     *
     * @return The key of the object to image process.
     * @see CImageProcessRequest#setKey(String)
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key of the object to image process.
     *
     * @param key The key of the object to image process.
     * @see CImageProcessRequest#getKey()
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets the key of the object to image process and returns this object, enabling additional method
     * calls to be chained together.
     *
     * @param key The key of the object to image process.
     * @return The updated {@link CImageProcessRequest} object, enabling additional method calls to
     * chained together.
     */
    public CImageProcessRequest withKey(String key) {
        setKey(key);
        return this;
    }

    public PicOperations getPicOperations() {
        if (picOperations == null) {
            picOperations = new PicOperations();
        }
        return picOperations;
    }

    public void setPicOperations(PicOperations picOperations) {
        this.picOperations = picOperations;
    }

    /**
     * Sets the pic operations of the object to image process and returns this object, enabling additional method
     * calls to be chained together.
     *
     * @param picOperations The pic operations of the object to image process.
     * @return The updated {@link CImageProcessRequest} object, enabling additional method calls to
     * chained together.
     */
    public CImageProcessRequest withKey(PicOperations picOperations) {
        setPicOperations(picOperations);
        return this;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }
}
