package com.qcloud.cos.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qcloud.cos.Headers;
import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.http.HttpResponseHandler;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.utils.DateUtils;
import com.qcloud.cos.utils.StringUtils;

import java.util.Map.Entry;

public abstract class AbstractCosResponseHandler<T>
        implements HttpResponseHandler<CosServiceResponse<T>> {

    private static final Logger log = LoggerFactory.getLogger(AbstractCosResponseHandler.class);

    /** The set of response headers that aren't part of the object's metadata */
    private static final Set<String> ignoredHeaders;

    static {
        ignoredHeaders = new HashSet<String>();
//        ignoredHeaders.add(Headers.DATE);
        ignoredHeaders.add(Headers.SERVER);
//        ignoredHeaders.add(Headers.REQUEST_ID);
        ignoredHeaders.add(Headers.TRACE_ID);
    }

    /**
     * The majority of COS response handlers read the complete response while handling it, and don't
     * need to manually manage the underlying HTTP connection.
     *
     */
    public boolean needsConnectionLeftOpen() {
        return false;
    }

    /**
     * Parses the COS response metadata (ex: COS request ID) from the specified response, and
     * returns a CosServiceResponse<T> object ready for the result to be plugged in.
     *
     * @param response The response containing the response metadata to pull out.
     *
     * @return A new, populated CosServiceResponse<T> object, ready for the result to be plugged in.
     */
    protected CosServiceResponse<T> parseResponseMetadata(CosHttpResponse response) {
        CosServiceResponse<T> cosResponse = new CosServiceResponse<T>();
        String cosRequestId = response.getHeaders().get(Headers.REQUEST_ID);
        String cosTraceId = response.getHeaders().get(Headers.TRACE_ID);

        Map<String, String> metadataMap = new HashMap<String, String>();
        metadataMap.put(Headers.REQUEST_ID, cosRequestId);
        metadataMap.put(Headers.TRACE_ID, cosTraceId);
        cosResponse.setResponseMetadata(new ResponseMetadata(metadataMap));

        return cosResponse;
    }

    /**
     * Populates the specified COSObjectMetadata object with all object metadata pulled from the
     * headers in the specified response.
     *
     * @param response The HTTP response containing the object metadata within the headers.
     * @param metadata The metadata object to populate from the response's headers.
     */
    protected void populateObjectMetadata(CosHttpResponse response, ObjectMetadata metadata) {
        for (Entry<String, String> header : response.getHeaders().entrySet()) {
            String key = header.getKey();
            if (key.startsWith(Headers.COS_USER_METADATA_PREFIX)) {
                key = key.substring(Headers.COS_USER_METADATA_PREFIX.length());
                metadata.addUserMetadata(key, header.getValue());
            } else if (ignoredHeaders.contains(key)) {
                // ignore...
            } else if (key.equals(Headers.LAST_MODIFIED)) {
                try {
                    metadata.setHeader(key, DateUtils.parseRFC822Date(header.getValue()));
                } catch (Exception pe) {
                    log.warn("Unable to parse last modified date: " + header.getValue(), pe);
                }
            } else if (key.equals(Headers.CONTENT_LENGTH)) {
                try {
                    metadata.setHeader(key, Long.parseLong(header.getValue()));
                } catch (NumberFormatException nfe) {
                    log.warn("Unable to parse content length: " + header.getValue(), nfe);
                }
            } else if (key.equals(Headers.DELETE_MARKER)) {
                metadata.setDeleteMarker(Boolean.parseBoolean(header.getValue()));
            } else if (key.equals(Headers.ETAG)) {
                metadata.setHeader(key, StringUtils.removeQuotes(header.getValue()));
            } else if (key.equals(Headers.EXPIRES)) {
                try {
                    metadata.setHttpExpiresDate(DateUtils.parseRFC822Date(header.getValue()));
                } catch (Exception pe) {
                    log.warn("Unable to parse http expiration date: " + header.getValue(), pe);
                }
            } else if (key.equals(Headers.EXPIRATION)) {
                new ObjectExpirationHeaderHandler<ObjectMetadata>().handle(metadata, response);
            } else if (key.equalsIgnoreCase(Headers.RESTORE)) {
                new ObjectRestoreHeaderHandler<ObjectRestoreResult>().handle(metadata, response);
            } else {
                metadata.setHeader(key, header.getValue());
            }
        }
    }
}
