/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 
 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.qcloud.cos.Headers;
import com.qcloud.cos.internal.ObjectExpirationResult;
import com.qcloud.cos.internal.ObjectRestoreResult;
import com.qcloud.cos.internal.ServerSideEncryptionResult;


/**
 * Represents the object metadata that is stored with Qcloud COS. This includes custom user-supplied
 * metadata, as well as the standard HTTP headers that Qcloud COS sends and receives
 * (Content-Length, ETag, Content-MD5, etc.).
 */
public class ObjectMetadata implements ServerSideEncryptionResult, ObjectExpirationResult,
        ObjectRestoreResult, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Custom user metadata, represented in responses with the x-cos-meta- header prefix
     */
    private Map<String, String> userMetadata;

    /**
     * All other (non user custom) headers such as Content-Length, Content-Type, etc.
     */
    private Map<String, Object> metadata;

    /**
     * The date when the object is no longer cacheable.
     */
    private Date httpExpiresDate;

    /**
     * The time this object will expire and be completely removed from COS, or null if this object
     * will never expire.
     * <p>
     * This and the expiration time rule aren't stored in the metadata map because the header
     * contains both the time and the rule.
     */
    private Date expirationTime;

    /**
     * The expiration rule id for this object.
     */
    private String expirationTimeRuleId;

    /**
     * Boolean value indicating whether there is an ongoing request to restore an archived copy of
     * this object from CAS.
     */
    private Boolean ongoingRestore;

    /**
     * The time at which an object that has been temporarily restored from CAS will expire, and will
     * need to be restored again in order to be accessed. Null if this object has not been restored
     * from CAS.
     */
    private Date restoreExpirationTime;


    /** True if this object represents a delete marker */
    private boolean isDeleteMarker;

    /**
     * <p>
     * Gets the custom user-metadata for the associated object.
     * </p>
     * <p>
     * Qcloud COS can store additional metadata on objects by internally representing it as HTTP
     * headers prefixed with "x-cos-meta-". Use user-metadata to store arbitrary metadata alongside
     * their data in Qcloud COS. When setting user metadata, callers <i>should not</i> include the
     * internal "x-cos-meta-" prefix; this library will handle that for them. Likewise, when callers
     * retrieve custom user-metadata, they will not see the "x-cos-meta-" header prefix.
     * </p>
     * <p>
     * User-metadata keys are <b>case insensitive</b> and will be returned as lowercase strings,
     * even if they were originally specified with uppercase strings.
     * </p>
     * <p>
     * Note that user-metadata for an object is limited by the HTTP request header limit. All HTTP
     * headers included in a request (including user metadata headers and other standard HTTP
     * headers) must be less than 8KB.
     * </p>
     *
     * @return The custom user metadata for the associated object.
     *
     * @see ObjectMetadata#setUserMetadata(Map)
     * @see ObjectMetadata#addUserMetadata(String, String)
     */
    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    /**
     * <p>
     * Sets the custom user-metadata for the associated object.
     * </p>
     * <p>
     * Qcloud COS can store additional metadata on objects by internally representing it as HTTP
     * headers prefixed with "x-cos-meta-". Use user-metadata to store arbitrary metadata alongside
     * their data in Qcloud COS. When setting user metadata, callers <i>should not</i> include the
     * internal "x-cos-meta-" prefix; this library will handle that for them. Likewise, when callers
     * retrieve custom user-metadata, they will not see the "x-cos-meta-" header prefix.
     * </p>
     * <p>
     * User-metadata keys are <b>case insensitive</b> and will be returned as lowercase strings,
     * even if they were originally specified with uppercase strings.
     * </p>
     * <p>
     * Note that user-metadata for an object is limited by the HTTP request header limit. All HTTP
     * headers included in a request (including user metadata headers and other standard HTTP
     * headers) must be less than 8KB.
     * </p>
     *
     * @param userMetadata The custom user-metadata for the associated object. Note that the key
     *        should not include the internal COS HTTP header prefix.
     * @see ObjectMetadata#getUserMetadata()
     * @see ObjectMetadata#addUserMetadata(String, String)
     */
    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }

    /**
     * For internal use only. Sets a specific metadata header value. Not intended to be called by
     * external code.
     *
     * @param key The name of the header being set.
     * @param value The value for the header.
     */
    public void setHeader(String key, Object value) {
        metadata.put(key, value);
    }

    /**
     * <p>
     * Adds the key value pair of custom user-metadata for the associated object. If the entry in
     * the custom user-metadata map already contains the specified key, it will be replaced with
     * these new contents.
     * </p>
     * <p>
     * Qcloud COS can store additional metadata on objects by internally representing it as HTTP
     * headers prefixed with "x-cos-meta-". Use user-metadata to store arbitrary metadata alongside
     * their data in Qcloud COS. When setting user metadata, callers <i>should not</i> include the
     * internal "x-cos-meta-" prefix; this library will handle that for them. Likewise, when callers
     * retrieve custom user-metadata, they will not see the "x-cos-meta-" header prefix.
     * </p>
     * <p>
     * Note that user-metadata for an object is limited by the HTTP request header limit. All HTTP
     * headers included in a request (including user metadata headers and other standard HTTP
     * headers) must be less than 8KB.
     * </p>
     *
     * @param key The key for the custom user metadata entry. Note that the key should not include
     *        the internal COS HTTP header prefix.
     * @param value The value for the custom user-metadata entry.
     *
     * @see ObjectMetadata#setUserMetadata(Map)
     * @see ObjectMetadata#getUserMetadata()
     */
    public void addUserMetadata(String key, String value) {
        this.userMetadata.put(key, value);
    }

    /**
     * For internal use only. Gets a map of the raw metadata/headers for the associated object.
     *
     * @return A map of the raw metadata/headers for the associated object.
     */
    public Map<String, Object> getRawMetadata() {
        return Collections.unmodifiableMap(metadata);
    }



    /**
     * For internal use only. Returns the raw value of the metadata/headers for the specified key.
     */
    public Object getRawMetadataValue(String key) {
        return metadata.get(key);
    }

    /**
     * Gets the value of the Last-Modified header, indicating the date and time at which Qcloud COS
     * last recorded a modification to the associated object.
     *
     * @return The date and time at which Qcloud COS last recorded a modification to the associated
     *         object. Returns <code>null</code> if the Last-Modified header hasn't been set.
     */
    public Date getLastModified() {
        return (Date) metadata.get(Headers.LAST_MODIFIED);
    }

    /**
     * For internal use only. Sets the Last-Modified header value indicating the date and time at
     * which Qcloud COS last recorded a modification to the associated object.
     *
     * @param lastModified The date and time at which Qcloud COS last recorded a modification to the
     *        associated object.
     */
    public void setLastModified(Date lastModified) {
        metadata.put(Headers.LAST_MODIFIED, lastModified);
    }

    /**
     * <p>
     * Gets the Content-Length HTTP header indicating the size of the associated object in bytes.
     * </p>
     * <p>
     * This field is required when uploading objects to COS, but the COS Java client will
     * automatically set it when working directly with files. When uploading directly from a stream,
     * set this field if possible. Otherwise the client must buffer the entire stream in order to
     * calculate the content length before sending the data to Qcloud COS.
     * </p>
     * <p>
     * For more information on the Content-Length HTTP header, see
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13"> http://www.w3.org/
     * Protocols/rfc2616/rfc2616-sec14.html#sec14.13</a>
     * </p>
     *
     * @return The Content-Length HTTP header indicating the size of the associated object in bytes.
     *         Returns <code>null</code> if it hasn't been set yet.
     *
     * @see ObjectMetadata#setContentLength(long)
     */
    public long getContentLength() {
        Long contentLength = (Long) metadata.get(Headers.CONTENT_LENGTH);

        if (contentLength == null)
            return 0;
        return contentLength.longValue();
    }

    /**
     * Returns the physical length of the entire object stored in COS. This is useful during, for
     * example, a range get operation.
     */
    public long getInstanceLength() {
        String contentRange = (String) metadata.get(Headers.CONTENT_RANGE);
        if (contentRange != null) {
            int pos = contentRange.lastIndexOf("/");
            if (pos >= 0)
                return Long.parseLong(contentRange.substring(pos + 1));
        }
        return getContentLength();
    }

    /**
     * <p>
     * Sets the Content-Length HTTP header indicating the size of the associated object in bytes.
     * </p>
     * <p>
     * This field is required when uploading objects to COS, but the COS Java client will
     * automatically set it when working directly with files. When uploading directly from a stream,
     * set this field if possible. Otherwise the client must buffer the entire stream in order to
     * calculate the content length before sending the data to Qcloud COS.
     * </p>
     * <p>
     * For more information on the Content-Length HTTP header, see
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13"> http://www.w3.org/
     * Protocols/rfc2616/rfc2616-sec14.html#sec14.13</a>
     * </p>
     *
     * @param contentLength The Content-Length HTTP header indicating the size of the associated
     *        object in bytes.
     *
     * @see ObjectMetadata#getContentLength()
     */
    public void setContentLength(long contentLength) {
        metadata.put(Headers.CONTENT_LENGTH, contentLength);
    }

    /**
     * <p>
     * Gets the Content-Type HTTP header, which indicates the type of content stored in the
     * associated object. The value of this header is a standard MIME type.
     * </p>
     * <p>
     * When uploading files, the COS Java client will attempt to determine the correct content type
     * if one hasn't been set yet. Users are responsible for ensuring a suitable content type is set
     * when uploading streams. If no content type is provided and cannot be determined by the
     * filename, the default content type, "application/octet-stream", will be used.
     * </p>
     * <p>
     * For more information on the Content-Type header, see
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17"> http://www.w3.org/
     * Protocols/rfc2616/rfc2616-sec14.html#sec14.17</a>
     * </p>
     *
     * @return The HTTP Content-Type header, indicating the type of content stored in the associated
     *         object. Returns <code>null</code> if it hasn't been set.
     *
     * @see ObjectMetadata#setContentType(String)
     */
    public String getContentType() {
        return (String) metadata.get(Headers.CONTENT_TYPE);
    }

    /**
     * <p>
     * Sets the Content-Type HTTP header indicating the type of content stored in the associated
     * object. The value of this header is a standard MIME type.
     * </p>
     * <p>
     * When uploading files, the COS Java client will attempt to determine the correct content type
     * if one hasn't been set yet. Users are responsible for ensuring a suitable content type is set
     * when uploading streams. If no content type is provided and cannot be determined by the
     * filename, the default content type "application/octet-stream" will be used.
     * </p>
     * <p>
     * For more information on the Content-Type header, see
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17"> http://www.w3.org/
     * Protocols/rfc2616/rfc2616-sec14.html#sec14.17</a>
     * </p>
     *
     * @param contentType The HTTP Content-Type header indicating the type of content stored in the
     *        associated COS object.
     *
     * @see ObjectMetadata#getContentType()
     */
    public void setContentType(String contentType) {
        metadata.put(Headers.CONTENT_TYPE, contentType);
    }

    /**
     * <p>
     * Gets the Content-Language HTTP header, which describes the natural language(s) of the
     * intended audience for the enclosed entity.
     * </p>
     */
    public String getContentLanguage() {
        return (String) metadata.get(Headers.CONTENT_LANGUAGE);
    }

    /**
     * <p>
     * Sets the Content-Language HTTP header which describes the natural language(s) of the intended
     * audience for the enclosed entity.
     * </p>
     */
    public void setContentLanguage(String contentLanguage) {
        metadata.put(Headers.CONTENT_LANGUAGE, contentLanguage);
    }

    /**
     * <p>
     * Gets the optional Content-Encoding HTTP header specifying what content encodings have been
     * applied to the object and what decoding mechanisms must be applied in order to obtain the
     * media-type referenced by the Content-Type field.
     * </p>
     * <p>
     * For more information on how the Content-Encoding HTTP header works, see
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11"> http://www.w3.org/
     * Protocols/rfc2616/rfc2616-sec14.html#sec14.11</a>
     * </p>
     *
     * @return The HTTP Content-Encoding header. Returns <code>null</code> if it hasn't been set.
     *
     * @see ObjectMetadata#setContentType(String)
     */
    public String getContentEncoding() {
        return (String) metadata.get(Headers.CONTENT_ENCODING);
    }

    /**
     * <p>
     * Sets the optional Content-Encoding HTTP header specifying what content encodings have been
     * applied to the object and what decoding mechanisms must be applied in order to obtain the
     * media-type referenced by the Content-Type field.
     * </p>
     * <p>
     * For more information on how the Content-Encoding HTTP header works, see
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11"> http://www.w3.org/
     * Protocols/rfc2616/rfc2616-sec14.html#sec14.11</a>
     * </p>
     *
     * @param encoding The HTTP Content-Encoding header, as defined in RFC 2616.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11" >http://www.w3
     *      .org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11</a>
     *
     * @see ObjectMetadata#getContentType()
     */
    public void setContentEncoding(String encoding) {
        metadata.put(Headers.CONTENT_ENCODING, encoding);
    }

    /**
     * <p>
     * Gets the optional Cache-Control HTTP header which allows the user to specify caching behavior
     * along the HTTP request/reply chain.
     * </p>
     * <p>
     * For more information on how the Cache-Control HTTP header affects HTTP requests and
     * responses, see
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9"> http://www.w3.org/
     * Protocols/rfc2616/rfc2616-sec14.html#sec14.9</a>
     * </p>
     *
     * @return The HTTP Cache-Control header as defined in RFC 2616. Returns <code>null</code> if it
     *         hasn't been set.
     *
     * @see ObjectMetadata#setCacheControl(String)
     */
    public String getCacheControl() {
        return (String) metadata.get(Headers.CACHE_CONTROL);
    }

    /**
     * <p>
     * Sets the optional Cache-Control HTTP header which allows the user to specify caching behavior
     * along the HTTP request/reply chain.
     * </p>
     * <p>
     * For more information on how the Cache-Control HTTP header affects HTTP requests and responses
     * see
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9"> http://www.w3.org/
     * Protocols/rfc2616/rfc2616-sec14.html#sec14.9</a>
     * </p>
     *
     * @param cacheControl The HTTP Cache-Control header as defined in RFC 2616.
     *
     * @see ObjectMetadata#getCacheControl()
     */
    public void setCacheControl(String cacheControl) {
        metadata.put(Headers.CACHE_CONTROL, cacheControl);
    }

    /**
     * <p>
     * Sets the base64 encoded 128-bit MD5 digest of the associated object (content - not including
     * headers) according to RFC 1864. This data is used as a message integrity check to verify that
     * the data received by Qcloud COS is the same data that the caller sent. If set to null,then
     * the MD5 digest is removed from the metadata.
     * </p>
     * <p>
     * This field represents the base64 encoded 128-bit MD5 digest digest of an object's content as
     * calculated on the caller's side. The ETag metadata field represents the hex encoded 128-bit
     * MD5 digest as computed by Qcloud COS.
     * </p>
     * <p>
     * The COS Java client will attempt to calculate this field automatically when uploading files
     * to Qcloud COS.
     * </p>
     *
     * @param md5Base64 The base64 encoded MD5 hash of the content for the object associated with
     *        this metadata.
     *
     * @see ObjectMetadata#getContentMD5()
     */
    public void setContentMD5(String md5Base64) {
        if (md5Base64 == null) {
            metadata.remove(Headers.CONTENT_MD5);
        } else {
            metadata.put(Headers.CONTENT_MD5, md5Base64);
        }

    }

    /**
     * <p>
     * Gets the base64 encoded 128-bit MD5 digest of the associated object (content - not including
     * headers) according to RFC 1864. This data is used as a message integrity check to verify that
     * the data received by Qcloud COS is the same data that the caller sent.
     * </p>
     * <p>
     * This field represents the base64 encoded 128-bit MD5 digest digest of an object's content as
     * calculated on the caller's side. The ETag metadata field represents the hex encoded 128-bit
     * MD5 digest as computed by Qcloud COS.
     * </p>
     * <p>
     * The COS Java client will attempt to calculate this field automatically when uploading files
     * to Qcloud COS.
     * </p>
     *
     * @return The base64 encoded MD5 hash of the content for the associated object. Returns
     *         <code>null</code> if the MD5 hash of the content hasn't been set.
     *
     * @see ObjectMetadata#setContentMD5(String)
     */
    public String getContentMD5() {
        return (String) metadata.get(Headers.CONTENT_MD5);
    }

    /**
     * <p>
     * Sets the optional Content-Disposition HTTP header, which specifies presentational information
     * such as the recommended filename for the object to be saved as.
     * </p>
     * <p>
     * For more information on how the Content-Disposition header affects HTTP client behavior, see
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1"> http://www.w3.org
     * /Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1</a>
     * </p>
     *
     * @param disposition The value for the Content-Disposition header.
     *
     * @see ObjectMetadata#getContentDisposition()
     */
    public void setContentDisposition(String disposition) {
        metadata.put(Headers.CONTENT_DISPOSITION, disposition);
    }

    /**
     * <p>
     * Gets the optional Content-Disposition HTTP header, which specifies presentation information
     * for the object such as the recommended filename for the object to be saved as.
     * </p>
     * <p>
     * For more information on how the Content-Disposition header affects HTTP client behavior, see
     * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1"> http://www.w3.org
     * /Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1</a>
     * </p>
     *
     * @return The value of the Content-Disposition header. Returns <code>null</code> if the
     *         Content-Disposition header hasn't been set.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1" >http://www.
     *      w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1</a>
     *
     * @see ObjectMetadata#setCacheControl(String)
     */
    public String getContentDisposition() {
        return (String) metadata.get(Headers.CONTENT_DISPOSITION);
    }

    /**
     * Gets the hex encoded 128-bit MD5 digest of the associated object according to RFC 1864. This
     * data is used as an integrity check to verify that the data received by the caller is the same
     * data that was sent by Qcloud COS.
     * <p>
     * This field represents the hex encoded 128-bit MD5 digest of an object's content as calculated
     * by Qcloud COS. The ContentMD5 field represents the base64 encoded 128-bit MD5 digest as
     * calculated on the caller's side.
     * </p>
     *
     * @return The hex encoded MD5 hash of the content for the associated object as calculated by
     *         Qcloud COS. Returns <code>null</code> if it hasn't been set yet.
     */
    public String getETag() {
        return (String) metadata.get(Headers.ETAG);
    }

    /**
     * Gets the version ID of the associated Qcloud COS object if available. Version IDs are only
     * assigned to objects when an object is uploaded to an Qcloud COS bucket that has object
     * versioning enabled.
     *
     * @return The version ID of the associated Qcloud COS object if available.
     */
    public String getVersionId() {
        return (String) metadata.get(Headers.COS_VERSION_ID);
    }

    /**
     * Returns the server-side encryption algorithm for the object, or null if none was used.
     */
    public String getServerSideEncryption() {
        return (String) metadata.get(Headers.SERVER_SIDE_ENCRYPTION);
    }

    /**
     * Sets the server-side encryption algorithm for the object.
     *
     * @param serverSideEncryption The server-side encryption algorithm for the object.
     */
    public void setServerSideEncryption(String serverSideEncryption) {
        metadata.put(Headers.SERVER_SIDE_ENCRYPTION, serverSideEncryption);
    }

    /**
     * Sets the security token for the object.
     *
     * @param securityToken The security token for the object.
     */
    public void setSecurityToken(String securityToken) {
        metadata.put(Headers.SECURITY_TOKEN, securityToken);
    }

    /**
     * Returns the time this object will expire and be completely removed from COS. Returns null if
     * this object will never expire.
     */
    public Date getExpirationTime() {
        return expirationTime;
    }

    /**
     * For internal use only. This will *not* set the object's expiration time, and is only used to
     * set the value in the object after receiving the value in a response from COS.
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

    /**
     * Set the date when the object is no longer cacheable.
     */
    public void setHttpExpiresDate(Date httpExpiresDate) {
        this.httpExpiresDate = httpExpiresDate;
    }

    /**
     * Returns the date when the object is no longer cacheable.
     */
    public Date getHttpExpiresDate() {
        return httpExpiresDate;
    }

    /**
     * Returns the time at which an object that has been temporarily restored from CAS will expire,
     * and will need to be restored again in order to be accessed. Returns null if this is not a
     * temporary copy of an object restored from CAS.
     */
    public Date getRestoreExpirationTime() {
        return restoreExpirationTime;
    }

    /**
     * only used to set the value in the object after receiving the value in a response from COS.
     *
     * @param restoreExpirationTime The new restore expiration time for the object.
     */
    public void setRestoreExpirationTime(Date restoreExpirationTime) {
        this.restoreExpirationTime = restoreExpirationTime;
    }

    /**
     * For internal use only. Sets the boolean value which indicates whether there is ongoing
     * restore request. Not intended to be called by external code.
     */
    public void setOngoingRestore(boolean ongoingRestore) {
        this.ongoingRestore = Boolean.valueOf(ongoingRestore);
    }


    /**
     * Returns the boolean value which indicates whether there is ongoing restore request.
     */
    public Boolean getOngoingRestore() {
        return this.ongoingRestore;
    }

    public boolean isDeleteMarker() {
        return isDeleteMarker;
    }

    public void setDeleteMarker(boolean isDeleteMarker) {
        this.isDeleteMarker = isDeleteMarker;
    }

    /**
     * Returns the value of the specified user meta datum.
     */
    public String getUserMetaDataOf(String key) {
        return userMetadata == null ? null : userMetadata.get(key);
    }



    public ObjectMetadata() {
        userMetadata = new HashMap<String, String>();
        metadata = new HashMap<String, Object>();
    }

    private ObjectMetadata(ObjectMetadata from) {
        // shallow clone the internal hash maps
        userMetadata =
                from.userMetadata == null ? null : new HashMap<String, String>(from.userMetadata);
        metadata = from.metadata == null ? null : new HashMap<String, Object>(from.metadata);
        this.expirationTime = from.expirationTime;
        this.expirationTimeRuleId = from.expirationTimeRuleId;
        this.httpExpiresDate = from.httpExpiresDate;
    }

    public ObjectMetadata clone() {
        return new ObjectMetadata(this);
    }

    @Override
    public String getSSEAlgorithm() {
        return (String) metadata.get(Headers.SERVER_SIDE_ENCRYPTION);
    }

    @Override
    public void setSSEAlgorithm(String algorithm) {
        metadata.put(Headers.SERVER_SIDE_ENCRYPTION, algorithm);
    }

    @Override
    public String getSSECustomerAlgorithm() {
        return (String) metadata.get(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM);
    }

    @Override
    public void setSSECustomerAlgorithm(String algorithm) {
        metadata.put(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM, algorithm);

    }

    @Override
    public String getSSECustomerKeyMd5() {
        return (String) metadata.get(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5);
    }

    @Override
    public void setSSECustomerKeyMd5(String md5Digest) {
        metadata.put(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5, md5Digest);

    }

   /**
    * @return The storage class of the object. Returns null if the object is in STANDARD storage.
    */
    public String getStorageClass() {
        final Object storageClass = metadata.get(Headers.STORAGE_CLASS);
        if (storageClass == null) {
            return null;
        }
        return storageClass.toString();
    }

    /**
     * Returns the Key Management System key id used for Server Side Encryption of the COS
     * object.
     */
    public String getSSECOSKmsKeyId() {
        return (String) metadata.get(Headers.SERVER_SIDE_ENCRYPTION_QCLOUD_KMS_KEYID);
    }

    public String getCrc64Ecma() {
        return (String)metadata.get(Headers.COS_HASH_CRC64_ECMA);
    }
}
