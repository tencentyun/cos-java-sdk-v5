package com.qcloud.cos.endpoint;


/**
 * get the endpoint, the endpoint will be used by host header and EndpointResolver.
 */

public interface EndpointBuilder {  
    /**
     * build endpoint endpoint, the endpoint is used by bucket and objects api. except the get service api.
     * @param bucketName. the bucketname should contain the appid. for example. movie-12510000.
     * @return the endpoint.
     */
    public String buildGeneralApiEndpoint(String bucketName);
    
    /**
     * the endpoint is used by  Get Service Api.
     * @param bucketName
     * @return
     */
    public String buildGetServiceApiEndpoint();
}
