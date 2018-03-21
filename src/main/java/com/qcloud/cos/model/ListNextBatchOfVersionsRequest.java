package com.qcloud.cos.model;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * Request object for the parameters to list next batch of versions.
 */
public class ListNextBatchOfVersionsRequest extends CosServiceRequest {

    private VersionListing previousVersionListing;

    /**
     * Creates a request object for listing next batch of versions.
     *
     * @param previousVersionListing The previous version listing whose features are to be fetched.
     */
    public ListNextBatchOfVersionsRequest(VersionListing previousVersionListing) {
        setPreviousVersionListing(previousVersionListing);
    }

    /**
     * Returns the previous version listing.
     * 
     * @return The previous version listing.
     */
    public VersionListing getPreviousVersionListing() {
        return previousVersionListing;
    }

    /**
     * Sets the previous version listing and all the features of the next version listing as well.
     * 
     * @param previousVersionListing This parameter must be specified.
     */
    public void setPreviousVersionListing(VersionListing previousVersionListing) {
        if (previousVersionListing == null) {
            throw new IllegalArgumentException(
                    "The parameter previousVersionListing must be specified.");
        }
        this.previousVersionListing = previousVersionListing;
    }

    /**
     * Sets the previous version listing and returns the updated request object so that additional
     * method calls can be chained together.
     *
     * @param previousVersionListing The previous version listing object whose features are to be
     *        fetched.
     * @return This updated request object so that additional method calls can be chained together.
     */
    public ListNextBatchOfVersionsRequest withPreviousVersionListing(
            VersionListing previousVersionListing) {
        setPreviousVersionListing(previousVersionListing);
        return this;
    }

    /**
     * Creates a new {@link ListVersionsRequest} object using the previous version listing.
     * 
     * @return A new {@link ListVersionsRequest} object using the previous version listing.
     */
    public ListVersionsRequest toListVersionsRequest() {
        return new ListVersionsRequest(previousVersionListing.getBucketName(),
                previousVersionListing.getPrefix(), previousVersionListing.getNextKeyMarker(),
                previousVersionListing.getNextVersionIdMarker(),
                previousVersionListing.getDelimiter(),
                Integer.valueOf(previousVersionListing.getMaxKeys()))
                        .withEncodingType(previousVersionListing.getEncodingType());
    }
}
