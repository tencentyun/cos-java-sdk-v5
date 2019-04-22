package com.qcloud.cos.endpoint;

/**
 * user specified endpoint builder
 *
 */
public class UserSpecifiedEndpointBuilder implements EndpointBuilder {

    private String generalApiEndpoint;
    private String getServiceApiEndpoint;
    
    public UserSpecifiedEndpointBuilder(String generalApiEndpoint, String getServiceApiEndpoint) {
        super();
        if (null == generalApiEndpoint  || null == getServiceApiEndpoint) {
            throw new IllegalArgumentException("endpoint must not be null");
        }
        this.generalApiEndpoint = generalApiEndpoint;
        this.getServiceApiEndpoint = getServiceApiEndpoint;
    }

    @Override
    public String buildGeneralApiEndpoint(String bucketName) {
        return generalApiEndpoint;
    }

    @Override
    public String buildGetServiceApiEndpoint() {
        return getServiceApiEndpoint;
    }
    
}
