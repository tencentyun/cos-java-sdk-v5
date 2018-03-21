package com.qcloud.cos.model;

/**
 * Contains information returned by Qcloud COS for a completed upload.
 * <p>
 * See {@link TransferManager} for more information about creating transfers.
 * 
 * @see TransferManager#upload(String, String, java.io.File)
 * @see TransferManager#upload(com.qcloud.cos.model.PutObjectRequest)
 */
public class UploadResult {

    /** x-cos-requestid **/
    private String requestId;

    /** date **/
    private String dateStr;

    /** The name of the bucket containing the completed upload. */
    private String bucketName;

    /** The key by which the object is stored. */
    private String key;

    /**
     * The entity tag identifying the new object. An entity tag is an opaque string that changes if
     * and only if an object's data changes.
     */
    private String eTag;

    /**
     * The version ID of the new object, only present if versioning has been enabled for the bucket.
     */
    private String versionId;

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
     * Returns the name of the bucket containing the uploaded object.
     *
     * @return The name of the bucket containing the uploaded object.
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Sets the name of the bucket containing the uploaded object.
     * 
     * @param bucketName The name of the bucket containing the uploaded object.
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Returns the key by which the newly created object is stored.
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
     * Returns the version ID of the new object. The version ID is only set if versioning has been
     * enabled for the bucket.
     *
     * @return The version ID of the new object. The version ID is only set if versioning has been
     *         enabled for the bucket.
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
}
