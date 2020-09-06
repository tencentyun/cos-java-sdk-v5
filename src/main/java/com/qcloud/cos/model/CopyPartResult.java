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

import com.qcloud.cos.internal.SSEResultBase;


public class CopyPartResult extends SSEResultBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The ETag value of the new part */
    private String etag;

    /** The last modified date for the new part */
    private Date lastModifiedDate;

    /**
     * The version ID of the source object. This field will only be present
     * if object versioning has been enabled for the bucket from which the object
     * was copied.
     */
    private String versionId;

    /**
     * The part number of the copied part
     */
    private int partNumber;

    /** The crc64ecma value for this object */
    private String crc64Ecma;

    /**
     * Gets the part number of the newly copied part.
     */
    public int getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the part number of the newly copied part.
     *
     * @param partNumber
     *            the part number of the newly uploaded part.
     */
    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    /**
     * Gets the ETag value for the new part that was created in the
     * associated {@link CopyPartRequest}.
     *
     * @return The ETag value for the new part.
     *
     * @see CopyPartResult#setETag(String)
     */
    public String getETag() {
        return etag;
    }

    /**
     * Sets the ETag value for the new part that was created from the
     * associated copy object request.
     *
     * @param etag
     *            The ETag value for the new part.
     *
     * @see CopyPartResult#getETag()
     */
    public void setETag(String etag) {
        this.etag = etag;
    }

    /**
     * Returns an identifier which identifies the copy part by its part number
     * and the entity tag computed from the part's data. This information is
     * later needed to complete a multipart copy.
     *
     * @return An identifier which identifies the copy part by its part number
     *         and the entity tag computed from the part's data.
     */
    public PartETag getPartETag() {
        return new PartETag(partNumber, etag);
    }

    /**
     * Gets the date the newly copied part was last modified.
     *
     * @return The date the newly copied part was last modified.
     *
     * @see CopyPartResult#setLastModifiedDate(Date)
     */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the date the newly copied part was last modified.
     *
     * @param lastModifiedDate
     *            The date the new, copied part was last modified.
     *
     * @see CopyPartResult#getLastModifiedDate()
     */
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Gets the version ID of the source object. This field is only
     * present if object versioning has been enabled for the bucket the
     * object was copied from.
     *
     * @return The version ID of the newly copied object.
     *
     * @see CopyPartResult#setVersionId(String)
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * Sets the version ID of the source object.
     *
     * @param versionId
     *            The version ID of the source object.
     *
     * @see CopyPartResult#getVersionId()
     */
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getCrc64Ecma() {
        return crc64Ecma;
    }

    public void setCrc64Ecma(String crc64Ecma) {
        this.crc64Ecma = crc64Ecma;
    }
}
