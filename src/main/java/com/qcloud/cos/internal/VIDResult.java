package com.qcloud.cos.internal;

public interface VIDResult {

    /**
     * get requestid for this upload
     * 
     * @return requestid
     */
    public String getRequestId();

    /**
     * set requestId for this upload
     * 
     * @param requestId the requestId for the upload
     */

    public void setRequestId(String requestId);

    /**
     * get date header for this upload
     * 
     * @return date str
     */
    public String getDateStr();

    /**
     * set date str for this upload
     * 
     * @param dateStr date str header
     */
    public void setDateStr(String dateStr);
}
