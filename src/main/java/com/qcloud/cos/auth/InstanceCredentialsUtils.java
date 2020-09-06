package com.qcloud.cos.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.utils.IOUtils;
import com.qcloud.cos.utils.Jackson;
import com.qcloud.cos.utils.VersionInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The utils for CPM and CVM credentials fetcher
 */
public class InstanceCredentialsUtils {
    private static final Logger LOG = LoggerFactory.getLogger(InstanceCredentialsUtils.class);

    private static InstanceCredentialsUtils instance = new InstanceCredentialsUtils(ConnectionUtils.getInstance());
    private static final String USER_AGENT = VersionInfoUtils.getUserAgent();

    private final ConnectionUtils connectionUtils;

    private InstanceCredentialsUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public static InstanceCredentialsUtils getInstance() {
        return instance;
    }

    public String readResource(URI endpoint) throws IOException {
        return readResource(endpoint, CredentialsEndpointRetryPolicy.NO_RETRY_POLICY, null);
    }

    public String readResource(URI endpoint, CredentialsEndpointRetryPolicy retryPolicy, Map<String, String> headers) throws IOException {
        int retriesAttempted = 0;
        InputStream inputStream = null;

        headers = addDefaultHeader(headers);

        while (true) {
            try {
                HttpURLConnection connection = this.connectionUtils.connectToEndpoint(endpoint, headers);

                int statusCode = connection.getResponseCode();

                if (statusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                    return IOUtils.toString(inputStream);
                } else if (statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    throw new CosClientException("The requested metadata is not found at " + connection.getURL());
                } else {
                    if (!retryPolicy.shouldRetry(retriesAttempted++,
                            CredentialsEndpointRetryParameters.builder().withStatusCode(statusCode).build())) {
                        inputStream = connection.getErrorStream();
                        String errorMessage = null;
                        String errorCode = null;
                        if (null != inputStream) {
                            String errorResponse = IOUtils.toString(inputStream);
                            try {
                                JsonNode node = Jackson.jsonNodeOf(errorResponse);
                                JsonNode code = node.get("code");
                                JsonNode message = node.get("message");
                                if (null != code) {
                                    errorCode = code.asText();
                                }
                                if (null != message) {
                                    errorMessage = message.asText();
                                }
                            } catch (Exception exception) {
                                LOG.debug("Unable to parse error stream.");
                            }
                        }
                        CosServiceException cosServiceException =
                                new CosServiceException(connection.getResponseMessage());
                        if (null != errorMessage) {
                            cosServiceException.setErrorMessage(errorMessage);
                        }
                        if (null != errorCode) {
                            cosServiceException.setErrorCode(errorCode);
                        }
                        throw cosServiceException;
                    }
                }
            } catch (IOException e) {
                if (!retryPolicy.shouldRetry(retriesAttempted++,
                        CredentialsEndpointRetryParameters.builder().withException(e).build())) {
                    throw e;
                }
                LOG.warn("An IOException occurred when connecting to service endpoint: {}. Retrying to connect again" +
                        ".", endpoint);
            } finally {
                IOUtils.closeQuietly(inputStream, LOG);
            }
        }
    }

    private Map<String, String> addDefaultHeader(Map<String, String> headers) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (headers != null) {
            map.putAll(headers);
        }

        putIfAbsent(map,"User-Agent", USER_AGENT);
        putIfAbsent(map,"Accept", "*/*");
        putIfAbsent(map,"Connection", "keep-alive");
        return map;
    }

    private <K, V> void putIfAbsent(Map<K, V> map, K key, V value) {
        if (map.get(key) == null) {
            map.put(key, value);
        }
    }
}
