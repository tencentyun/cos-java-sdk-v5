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
import java.util.Date;

public class COSVersionSummary implements Serializable {
    /** The name of the bucket in which this version is stored */
    protected String bucketName;
    
    /** The key under which this version is stored */
    private String key;
    
    /** The version ID uniquely identifying this version of an object */
    private String versionId;
    
    /** True if this is the latest version of the associated object */
    private boolean isLatest;
    
    /** The date, according to COS, when this version was last modified */
    private Date lastModified;

    /**
     * The owner of this version of the associated object - can be null if the
     * requester doesn't have permission to view object ownership information
     */
    private Owner owner;

    /** Hex encoded MD5 hash of this version's contents, as computed by COS */
    private String eTag;
    
    /** The size of this version, in bytes */
    private long size;
    
    /** The class of storage used by COS to store this version */
    private String storageClass;
    
    /** True if this object represents a delete marker */
    private boolean isDeleteMarker;

    
    /**
     * Gets the name of the COS bucket in which this version is stored.
     * 
     * @return The name of the COS bucket in which this version is stored.
     * 
     * @see COSVersionSummary#setBucketName(String)
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Sets the name of the COS bucket in which this version is stored.
     * 
     * @param bucketName
     *            The name of the COS bucket in which this version is
     *            stored.
     *            
     * @see COSVersionSummary#getBucketName()          
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    
    /**
     * Gets the key under which this version is stored in COS.
     * 
     * @return The key under which this version is stored in COS.
     * 
     * @see COSVersionSummary#setKey(String)
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key under which this version is stored in COS.
     * 
     * @param key
     *            The key under which this version is stored in COS.
     *            
     * @see COSVersionSummary#getKey()
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the version ID which uniquely identifies this version of an
     * object.
     * <p>
     * Objects created before versioning was enabled or when versioning is
     * suspended will be given the default/null version ID (see
     * {@link Constants#NULL_VERSION_ID}). Note that the
     * {@link Constants#NULL_VERSION_ID} is a valid version ID and is not the
     * same as not having a version ID.
     * </p>
     * 
     * @return The version ID which uniquely identifies this version of an
     *         object.
     *         
     * @see COSVersionSummary#setVersionId(String)        
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * Sets the version ID which uniquely identifies this version of an object.
     * 
     * @param id
     *            The version ID which uniquely identifies this version of an
     *            object.
     *            
     * @see COSVersionSummary#getVersionId()           
     */
    public void setVersionId(String id) {
        this.versionId = id;
    }

    /**
     * Returns whether or not this version is the latest version
     * for the associated object.
     * 
     * @return The value <code>true</code> if this version is the 
     * latest version for the associated object; returns the value
     * <code>false</code> if otherwise.
     */
    public boolean isLatest() {
        return this.isLatest;
    }

    /**
     * For internal use only.
     * Sets whether this version is the latest version for the associated
     * object. This method is intended to be used only by the client internals
     * and developers shouldn't need to use it.
     * 
     * @param isLatest
     *            True if this version represents the latest version for the
     *            associated object in COS.
     */
    public void setIsLatest(boolean isLatest) {
        this.isLatest = isLatest;
    }

    /**
     * Gets the date according to COS at which this version was last
     * modified.
     * 
     * @return The date according to COS at which this version was last
     *         modified.
     *         
     * @see COSVersionSummary#setLastModified(Date)      
     */
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * Sets the date according to COS at which this version was last
     * modified.
     * 
     * @param lastModified
     *            The date according to COS at which this version was
     *            last modified.
     *            
     * @see COSVersionSummary#getLastModified()                  
     */
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Gets the owner of this version. Returns <code>null</code> 
     * if the requester doesn't have
     * {@link Permission#ReadAcp} permission for this version or owns the bucket
     * in which it resides.
     * 
     * @return The owner of this version. Returns <code>null</code> 
     *         if the requester doesn't have
     *         permission to see object ownership for this version.
     *         
     * @see COSVersionSummary#setOwner(Owner)        
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     * For internal use only.
     * Sets the owner of this version. This method is intended to be used only
     * by the client internals and developers shouldn't need to use it.
     * 
     * @param owner
     *            The owner of this version.
     *            
     * @see COSVersionSummary#getOwner()         
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Returns whether or not this version represents a delete marker.
     * <p>
     * Delete markers are special types of versions that have no data associated
     * with them. When deleting a versioned object in COS without specifying
     * an explicit version ID, a new delete marker is created as the latest
     * version of that object to mark that the object was deleted.
     * This occurs even though
     * the previous versions still exist in COS.
     * </p>
     * <p>
     * Delete markers have no data associated with them and therefore have no
     * associated ACL, size or storage class. Only requesters with read access
     * to a bucket can learn of their existence by listing the versions in a
     * bucket.
     * </p>
     * 
     * @return The value <code>true</code> if this version represents a delete marker.
     *         Returns the value <code>false</code> if otherwise.
     */
    public boolean isDeleteMarker() {
        return isDeleteMarker;
    }

    /**
     * Intended for internal use only in the COS client code. Sets the value of
     * the <code>isDeleteMarker</code> property to record if this is a delete marker or not.
     * 
     * @param isDeleteMarker
     *            Specify <code>true<code> if this version summary represents a delete marker,
     *            otherwise <code>false<code> if it is a regular version summary.
     */
    public void setIsDeleteMarker(boolean isDeleteMarker) {
        this.isDeleteMarker = isDeleteMarker;
    }

    /**
     * Gets the hex encoded 128-bit MD5 hash of this version's contents as
     * computed by COS.
     * 
     * @return The hex encoded 128-bit MD5 hash of this version's contents as
     *         computed by COS.
     *         
     * @see COSVersionSummary#setETag(String)       
     */
    public String getETag() {
        return eTag;
    }

    /**
     * Sets the hex encoded 128-bit MD5 hash of this version's contents as
     * computed by COS.
     * 
     * @param eTag
     *            The hex encoded 128-bit MD5 hash of this version's contents
     *            as computed by COS.
     *            
     * @see COSVersionSummary#getETag()             
     */
    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    /**
     * Gets the storage class used by COS for this version.
     * 
     * @return The storage class used by COS for this version.
     * 
     * @see COSVersionSummary#setStorageClass(String)
     */
    public String getStorageClass() {
        return storageClass;
    }

    /**
     * Sets the storage class used by COS for this version.
     * 
     * @param storageClass
     *            The storage class used by COS for this version.
     *            
     * @see COSVersionSummary#getStorageClass()         
     */
    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    /**
     * Gets the size of this version in bytes.
     * 
     * @return The size of this version in bytes.
     * 
     * @see COSVersionSummary#setSize(long)
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the size of this version in bytes.
     * 
     * @param size
     *            The size of this version in bytes.
     *            
     * @see COSVersionSummary#getSize()          
     */
    public void setSize(long size) {
        this.size = size;
    }
}
