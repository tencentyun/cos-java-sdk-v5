package com.qcloud.cos.internal;

public class CosServiceResponse<T> {

    private T result;

    private ResponseMetadata responseMetadata;

    /**
     * Returns the result contained by this response.
     *
     * @return The result contained by this response.
     */
    public T getResult() {
        return result;
    }

    /**
     * Sets the result contained by this response.
     *
     * @param result
     *            The result contained by this response.
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * Sets the response metadata associated with this response.
     * 
     * @param responseMetadata
     *            The response metadata for this response.
     */
    public void setResponseMetadata(ResponseMetadata responseMetadata) {
        this.responseMetadata = responseMetadata;
    }

    /**
     * Returns the response metadata for this response. Response metadata
     * provides additional information about a response that isn't necessarily
     * directly part of the data the service is returning. 
     * 
     * @return The response metadata for this response.
     */
    public ResponseMetadata getResponseMetadata() {
        return responseMetadata;
    }

    /**
     * Returns the cos request ID from the response metadata 
     *
     * @return The COS request ID from the response metadata
     */
    public String getRequestId() {
        if (responseMetadata == null) return null;
        return responseMetadata.getRequestId();
    }

}
