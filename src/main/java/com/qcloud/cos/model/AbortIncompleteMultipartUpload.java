package com.qcloud.cos.model;

import java.io.Serializable;

/**
 * Specifies the days since the initiation of an Incomplete Multipart Upload that Lifecycle will wait before permanently removing all parts of the upload.
 */
public class AbortIncompleteMultipartUpload implements Serializable {

    /**
     * Indicates the number of days that must pass since initiation for Lifecycle to abort an Incomplete Multipart Upload.
     */
    private int daysAfterInitiation;

    public int getDaysAfterInitiation() {
        return daysAfterInitiation;
    }

    public void setDaysAfterInitiation(int daysAfterInitiation) {
        this.daysAfterInitiation = daysAfterInitiation;
    }

    public AbortIncompleteMultipartUpload withDaysAfterInitiation(int daysAfterInitiation) {
        setDaysAfterInitiation(daysAfterInitiation);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbortIncompleteMultipartUpload that = (AbortIncompleteMultipartUpload) o;

        return daysAfterInitiation == that.daysAfterInitiation;
    }

    @Override
    public int hashCode() {
        return daysAfterInitiation;
    }

    @Override
    protected AbortIncompleteMultipartUpload clone() throws CloneNotSupportedException {
        try {
            return (AbortIncompleteMultipartUpload) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(
                    "Got a CloneNotSupportedException from Object.clone() "
                            + "even though we're Cloneable!", e);
        }
    }
}
