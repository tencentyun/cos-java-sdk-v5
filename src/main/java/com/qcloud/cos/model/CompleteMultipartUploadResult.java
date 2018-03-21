package com.qcloud.cos.model;

import java.io.Serializable;
import java.util.Date;

import com.qcloud.cos.internal.ObjectExpirationResult;
import com.qcloud.cos.internal.SSEResultBase;

public class CompleteMultipartUploadResult extends SSEResultBase
        implements Serializable, ObjectExpirationResult {

    /** x-cos-requestid **/
    private String requestId;

    /** date **/
    private String dateStr;

    /** The name of the bucket containing the completed multipart upload. */
    private String bucketName;

    /** The key by which the object is stored. */
    private String key;

    /** The URL identifying the new multipart object. */
    private String location;

    /**
     * The entity tag identifying the new object. An entity tag is an opaque string that changes if
     * and only if an object's data changes.
     */
    private String eTag;

    /**
     * The version ID of the new object, only present if versioning has been enabled for the bucket.
     */
    private String versionId;

    /** The time this object expires, or null if it has no expiration */
    private Date expirationTime;

    /** The expiration rule for this object */
    private String expirationTimeRuleId;

    /**
     * get requestid for this upload
     * 
     * @return requestid
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * set requestId for this upload
     * 
     * @param requestId the requestId for the upload
     */

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * get date header for this upload
     * 
     * @return date str
     */
    public String getDateStr() {
        return dateStr;
    }

    /**
     * set date str for this upload
     * 
     * @param dateStr date str header
     */
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    /**
     * Returns the URL identifying the new multipart object.
     *
     * @return The URL identifying the new multipart object.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the URL identifying the new multipart object.
     *
     * @param location The URL identifying the new multipart object.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the name of the bucket containing the completed multipart object.
     *
     * @return The name of the bucket containing the completed multipart object.
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Sets the name of the bucket containing the completed multipart object.
     *
     * @param bucketName The name of the bucket containing the completed multipart object.
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Gets the key by which the newly created object is stored.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key of the newly created object.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns the entity tag identifying the new object. An entity tag is an opaque string that
     * changes if and only if an object's data changes.
     *
     * @return An opaque string that changes if and only if an object's data changes.
     */
    public String getETag() {
        return eTag;
    }

    /**
     * Sets the entity tag identifying the new object. An entity tag is an opaque string that
     * changes if and only if an object's data changes.
     *
     * @param etag The entity tag.
     */
    public void setETag(String etag) {
        this.eTag = etag;
    }

    /**
     * Returns the version ID of the new object, only present if versioning has been enabled for the
     * bucket.
     *
     * @return The version ID of the new object, only present if versioning has been enabled for the
     *         bucket.
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * Sets the version ID of the new object, only present if versioning has been enabled for the
     * bucket.
     *
     * @param versionId The version ID of the new object, only present if versioning has been
     *        enabled for the bucket.
     */
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    /**
     * Returns the expiration time for this object, or null if it doesn't expire.
     */
    public Date getExpirationTime() {
        return expirationTime;
    }

    /**
     * Sets the expiration time for the object.
     *
     * @param expirationTime The expiration time for the object.
     */
    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * Returns the {@link BucketLifecycleConfiguration} rule ID for this object's expiration, or
     * null if it doesn't expire.
     */
    public String getExpirationTimeRuleId() {
        return expirationTimeRuleId;
    }

    /**
     * Sets the {@link BucketLifecycleConfiguration} rule ID for this object's expiration
     *
     * @param expirationTimeRuleId The rule ID for this object's expiration
     */
    public void setExpirationTimeRuleId(String expirationTimeRuleId) {
        this.expirationTimeRuleId = expirationTimeRuleId;
    }

}
