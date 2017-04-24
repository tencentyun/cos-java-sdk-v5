package com.qcloud.cos.internal;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.qcloud.cos.http.CosHttpResponse;

/**
 * An XML response handler that can also process an arbitrary number of headers
 * in the response.
 */
public class ResponseHeaderHandlerChain <T> extends COSXmlResponseHandler<T> {

    private final List<HeaderHandler<T>> headerHandlers;
    
    public ResponseHeaderHandlerChain(Unmarshaller<T, InputStream> responseUnmarshaller, HeaderHandler<T>... headerHandlers) {
        super(responseUnmarshaller);
        this.headerHandlers = Arrays.asList(headerHandlers);
    }

    @Override
    public CosServiceResponse<T> handle(CosHttpResponse response) throws Exception {
        CosServiceResponse<T> cseResponse = super.handle(response);
        
        T result = cseResponse.getResult();
        if (result != null) {
            for (HeaderHandler<T> handler : headerHandlers) {
                handler.handle(result, response);
            }
        }
        
        return cseResponse;
    }
}
