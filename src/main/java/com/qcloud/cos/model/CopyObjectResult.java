package com.qcloud.cos.model;

import java.io.Serializable;
import java.util.Date;

import com.qcloud.cos.internal.ObjectExpirationResult;
import com.qcloud.cos.internal.SSEResultBase;

public class CopyObjectResult extends SSEResultBase
        implements ObjectExpirationResult, Serializable {
    /** x-cos-requestid **/
    private String requestId;

    /** date **/
    private String dateStr;

    /** The ETag value of the new object */
    private String etag;

    /** The last modified date for the new object */
    private Date lastModifiedDate;

    /**
     * The version ID of the new, copied object. This field will only be present if object
     * versioning has been enabled for the bucket to which the object was copied.
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
     * Gets the ETag value for the new object that was created in the associated
     * {@link CopyObjectRequest}.
     *
     * @return The ETag value for the new object.
     *
     * @see CopyObjectResult#setETag(String)
     */
    public String getETag() {
        return etag;
    }

    /**
     * Sets the ETag value for the new object that was created from the associated copy object
     * request.
     *
     * @param etag The ETag value for the new object.
     *
     * @see CopyObjectResult#getETag()
     */
    public void setETag(String etag) {
        this.etag = etag;
    }

    /**
     * Gets the date the newly copied object was last modified.
     *
     * @return The date the newly copied object was last modified.
     *
     * @see CopyObjectResult#setLastModifiedDate(Date)
     */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the date the newly copied object was last modified.
     *
     * @param lastModifiedDate The date the new, copied object was last modified.
     *
     * @see CopyObjectResult#getLastModifiedDate()
     */
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Gets the version ID of the newly copied object. This field is only present if object
     * versioning has been enabled for the bucket the object was copied to.
     *
     * @return The version ID of the newly copied object.
     *
     * @see CopyObjectResult#setVersionId(String)
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * Sets the version ID of the newly copied object.
     *
     * @param versionId The version ID of the newly copied object.
     *
     * @see CopyObjectResult#getVersionId()
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
