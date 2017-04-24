package com.qcloud.cos.internal;

import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qcloud.cos.http.CosHttpResponse;

public class COSXmlResponseHandler<T> extends AbstractCosResponseHandler<T> {

    /** The SAX unmarshaller to use when handling the response from COS */
    private Unmarshaller<T, InputStream> responseUnmarshaller;

    /** Shared logger for profiling information */
    private static final Logger log = LoggerFactory.getLogger(COSXmlResponseHandler.class);

    /** Response headers from the processed response */
    private Map<String, String> responseHeaders;

    /**
     * Constructs a new COS response handler that will use the specified SAX
     * unmarshaller to turn the response into an object.
     *
     * @param responseUnmarshaller
     *            The SAX unmarshaller to use on the response from COS.
     */
    public COSXmlResponseHandler(Unmarshaller<T, InputStream> responseUnmarshaller) {
        this.responseUnmarshaller = responseUnmarshaller;
    }

    @Override
    public CosServiceResponse<T> handle(CosHttpResponse response) throws Exception {
        CosServiceResponse<T> cosResponse = parseResponseMetadata(response);
        responseHeaders = response.getHeaders();

        if (responseUnmarshaller != null) {
            log.trace("Beginning to parse service response XML");
            T result = responseUnmarshaller.unmarshall(response.getContent());
            log.trace("Done parsing service response XML");
            cosResponse.setResult(result);
        }

        return cosResponse;
    }

    /**
     * Returns the headers from the processed response. Will return null until a
     * response has been handled.
     *
     * @return the headers from the processed response.
     */
    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }
}
