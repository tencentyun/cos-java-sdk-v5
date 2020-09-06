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

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;


public class COSObject implements Closeable, Serializable {

    private static final long serialVersionUID = 1L;

    /** The key under which this object is stored */
    private String key = null;

    /** The name of the bucket in which this object is contained */
    private String bucketName = null;

    /** The metadata stored by Qcloud COS for this object */
    private ObjectMetadata metadata = new ObjectMetadata();

    /** The stream containing the contents of this object from COS */
    private transient COSObjectInputStream objectContent;

    /**
     * Gets the metadata stored by Qcloud COS for this object. The {@link ObjectMetadata} object
     * includes any custom user metadata supplied by the caller when the object was uploaded, as
     * well as HTTP metadata such as content length and content type.
     *
     * @return The metadata stored by Qcloud COS for this object.
     * @see COSObject#getObjectContent()
     */
    public ObjectMetadata getObjectMetadata() {
        return metadata;
    }

    /**
     * Sets the object metadata for this object.
     * <p>
     * <b>NOTE:</b> This does not update the object metadata stored in Qcloud COS, but only updates
     * this object in local memory. To update an object's metadata in COS, use
     * {@link COS#copyObject(CopyObjectRequest)} to copy the object to a new (or the same location)
     * and specify new object metadata then.
     *
     * @param metadata The new metadata to set for this object in memory.
     */
    public void setObjectMetadata(ObjectMetadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Gets the input stream containing the contents of this object.
     *
     * <p>
     * <b>Note</b>: The method is a simple getter and does not actually create a stream. If you
     * retrieve an COSObject, you should close this input stream as soon as possible, because the
     * object contents aren't buffered in memory and stream directly from Qcloud COS. Further,
     * failure to close this stream can cause the request pool to become blocked.
     * </p>
     *
     * @return An input stream containing the contents of this object.
     *
     * @see COSObject#getObjectMetadata()
     * @see COSObject#setObjectContent(InputStream)
     */
    public COSObjectInputStream getObjectContent() {
        return objectContent;
    }

    /**
     * Sets the input stream containing this object's contents.
     *
     * @param objectContent The input stream containing this object's contents.
     *
     * @see COSObject#getObjectContent()
     */
    public void setObjectContent(COSObjectInputStream objectContent) {
        this.objectContent = objectContent;
    }

    /**
     * Sets the input stream containing this object's contents.
     *
     * @param objectContent The input stream containing this object's contents. Will get wrapped in
     *        an COSObjectInputStream.
     * @see COSObject#getObjectContent()
     */
    public void setObjectContent(InputStream objectContent) {
        setObjectContent(new COSObjectInputStream(objectContent,
                this.objectContent != null ? this.objectContent.getHttpRequest() : null));
    }

    /**
     * Gets the name of the bucket in which this object is contained.
     *
     * @return The name of the bucket in which this object is contained.
     *
     * @see COSObject#setBucketName(String)
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Sets the name of the bucket in which this object is contained.
     *
     * @param bucketName The name of the bucket containing this object.
     *
     * @see COSObject#getBucketName()
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Gets the key under which this object is stored.
     *
     * @return The key under which this object is stored.
     *
     * @see COSObject#setKey(String)
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key under which this object is stored.
     *
     * @param key The key under which this object is stored.
     *
     * @see COSObject#getKey()
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "COSObject [key=" + getKey() + ",bucket="
                + (bucketName == null ? "<Unknown>" : bucketName) + "]";
    }

    /**
     * Releases any underlying system resources. If the resources are already released then invoking
     * this method has no effect.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        InputStream is = getObjectContent();
        if (is != null)
            is.close();
    }

}
