package com.qcloud.cos.internal;

import java.util.Date;

/**
 * Interface for service responses that receive the x-cos-restore header.
 *
 * @see Headers#RESTORE
 */
public interface ObjectRestoreResult {

    /**
     * Returns the expiration date when the Object is scheduled to move to CAS, or null if the object is not
     * configured to expire.
     */
    public Date getRestoreExpirationTime();

    /**
     * Sets the expiration date when the Object is scheduled to move to CAS.
     *
     * @param expiration
     *            The date the object will expire.
     */
    public void setRestoreExpirationTime(Date expiration);

    /**
     * Sets a boolean value which indicates there is an ongoing restore request.
     * @param ongoingRestore
     */
    public void setOngoingRestore(boolean ongoingRestore);

    /**
     * Returns then  boolean value which indicates there is an ongoing restore request.
     */
    public Boolean getOngoingRestore();
}