package com.qcloud.cos.model;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * Request object for parameters of listing next batch of objects.
 */
public class ListNextBatchOfObjectsRequest extends CosServiceRequest implements Serializable {

    private ObjectListing previousObjectListing;

    /**
     * Creates a request object for listing next batch of objects.
     *
     * @param previousObjectListing
     *          The previous object listing whose features are to be fetched.
     */
    public ListNextBatchOfObjectsRequest(ObjectListing previousObjectListing) {
        setPreviousObjectListing(previousObjectListing);
    }

    /**
     * Returns the previous object listing.
     * @return The previous object listing.
     */
    public ObjectListing getPreviousObjectListing() {
        return previousObjectListing;
    }

    /**
     * Sets the previous object listing and all the features of the next object listing as well.
     * @param previousObjectListing
     *          This parameter must be specified.
     */
    public void setPreviousObjectListing(ObjectListing previousObjectListing) {
        if(previousObjectListing == null) {
            throw new IllegalArgumentException("The parameter previousObjectListing must be specified.");
        }
        this.previousObjectListing = previousObjectListing;
    }

    /**
     * Sets the previous object listing and returns the updated request object so that additional
     * method calls can be chained together.
     *
     * @param previousObjectListing
     *          The previous object listing whose features are to be fetched.
     * @return The updated request object so that additional method calls can be chained together.
     */
    public ListNextBatchOfObjectsRequest withPreviousObjectListing(ObjectListing previousObjectListing) {
        setPreviousObjectListing(previousObjectListing);
        return this;
    }

    /**
     * Creates a new {@link ListObjectsRequest} object using the previous object listing.
     * @return A new {@link ListObjectsRequest} object using the previous object listing.
     */
    public ListObjectsRequest toListObjectsRequest() {
        return new ListObjectsRequest(previousObjectListing.getBucketName(),
                previousObjectListing.getPrefix(),
                previousObjectListing.getNextMarker(),
                previousObjectListing.getDelimiter(),
                Integer.valueOf(previousObjectListing.getMaxKeys()))
                .withEncodingType(previousObjectListing.getEncodingType());
    }

}