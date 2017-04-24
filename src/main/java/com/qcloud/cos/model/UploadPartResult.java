package com.qcloud.cos.model;

import java.io.Serializable;

public class UploadPartResult implements Serializable {
    /** The part number of the newly uploaded part */
    private int partNumber;

    /** The entity tag generated from the content of the upload part */
    private String eTag;

    /**
     * Returns the part number of the newly uploaded part.
     *
     * @return The part number of the newly uploaded part.
     */
    public int getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the part number of the newly uploaded part.
     *
     * @param partNumber
     *            the part number of the newly uploaded part.
     */
    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    /**
     * Returns the entity tag of the newly uploaded part. The entity tag is
     * needed later when the multipart upload is completed.
     *
     * @return the entity tag of the newly uploaded part.
     */
    public String getETag() {
        return eTag;
    }

    /**
     * Sets the entity tag of the newly uploaded part.
     *
     * @param eTag
     *            the entity tag of the newly uploaded part.
     */
    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    /**
     * Returns an identifier which identifies the upload part by its part number
     * and the entity tag computed from the part's data. This information is
     * later needed to complete a multipart upload.
     *
     * @return An identifier which identifies the upload part by its part number
     *         and the entity tag computed from the part's data.
     */
    public PartETag getPartETag() {
        return new PartETag(partNumber, eTag);
    }
}
