package com.qcloud.cos.model;

import java.io.Serializable;

public class PartETag implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The part number of the associated part. */
    private int partNumber;

    /** The entity tag generated from the content of the associated part. */
    private String eTag;


    /**
     * Constructs an instance of PartETag and sets the part number and ETag.
     *
     * @param partNumber The part number.
     * @param eTag the associated ETag for the part number.
     */
    public PartETag(int partNumber, String eTag) {
        this.partNumber = partNumber;
        this.eTag = eTag;
    }


    /**
     * Returns the part number of the associated part.
     *
     * @return the part number of the associated part.
     */
    public int getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the part number of the associated part.
     *
     * @param partNumber the part number of the associated part.
     */
    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    /**
     * Sets the part number of the associated part, and returns this updated PartETag object so that
     * additional method calls can be chained together.
     *
     * @param partNumber the part number of the associated part.
     *
     * @return This updated PartETag object.
     */
    public PartETag withPartNumber(int partNumber) {
        this.partNumber = partNumber;
        return this;
    }

    /**
     * Returns the entity tag generated from the content of the associated part.
     *
     * @return the entity tag generated from the content of the associated part.
     */
    public String getETag() {
        return eTag;
    }

    /**
     * Sets the entity tag generated from the content of the associated part.
     *
     * @param eTag the entity tag generated from the content of the associated part.
     */
    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    /**
     * Sets the entity tag generated from the content of the associated part, and returns this
     * updated PartETag object so that additional method calls can be chained together.
     *
     * @param eTag the entity tag generated from the content of the associated part.
     *
     * @return This updated PartETag object.
     */
    public PartETag withETag(String eTag) {
        this.eTag = eTag;
        return this;
    }

}
