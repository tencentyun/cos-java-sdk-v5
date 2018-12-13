package com.qcloud.cos.endpoint;

/**
 * default endpoint resolver, just return the api endpoint
 * the dns resolve will be implemented by http library.
 * @author chengwu
 *
 */
public class DefaultEndpointResolver implements EndpointResolver {

    @Override
    public String resolveGeneralApiEndpoint(String generalApiEndpoint) {
        return generalApiEndpoint;
    }

    @Override
    public String resolveGetServiceApiEndpoint(String getServiceApiEndpoint) {
        return getServiceApiEndpoint;
    }

}
