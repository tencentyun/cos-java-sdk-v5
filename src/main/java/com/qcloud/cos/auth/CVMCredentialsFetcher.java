package com.qcloud.cos.auth;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.utils.DateUtils;
import com.qcloud.cos.utils.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * Helper class that contains the common behavior of the CredentialsProvider that loads the credentials(in milliseconds)
 * from a local endpoint on CVM instance
 */
public class CVMCredentialsFetcher {
    private static final Logger LOG = LoggerFactory.getLogger(CVMCredentialsFetcher.class);

    // Refresh every hour by default
    private static final int REFRESH_THRESHOLD = 60 * 60 * 1000;

    // The threshold before credentials expire (in milliseconds) at which
    // this class will attempt to load new credentials.
    private static final int EXPIRATION_THRESHOLD = 10 * 60 * 1000;

    private static final String SECRET_ID = "TmpSecretId";
    private static final String SECRET_KEY = "TmpSecretKey";
    private static final String TOKEN = "Token";
    private static final String EXPIRATION = "Expiration";

    private volatile COSCredentials cosCredentials;
    private volatile Date credentialExpiration;
    private volatile Date lastInstanceProfileCheck;
    private CredentialsEndpointProvider credentialsEndpointProvider;

    public CVMCredentialsFetcher(CredentialsEndpointProvider credentialsEndpointProvider) {
        if (null == credentialsEndpointProvider) {
            this.handleError("The credentials endpoint provider can not be null.", null);
        }
        this.credentialsEndpointProvider = credentialsEndpointProvider;
    }

    public COSCredentials getCredentials() throws CosClientException {
        if (needsToLoadCredentials()) {
            this.fetchCredentials();
        }
        if (expired()) {
            throw new CosClientException("The credentials received have been expired");
        }

        return this.cosCredentials;
    }

    protected boolean needsToLoadCredentials() {
        if (null == this.cosCredentials) {
            return true;
        }

        if (null != this.credentialExpiration) {
            if (isWithinExpirationThreshold()) {
                return true;
            }
        }

        if (null != this.lastInstanceProfileCheck) {
            return isPastRefreshThreshold();
        }

        return false;
    }

    private synchronized void fetchCredentials() {
        if (!needsToLoadCredentials())
            return;

        JsonNode node;
        JsonNode secretId;
        JsonNode secretKey;
        JsonNode token;
        JsonNode expirationJsonNode;

        try {
            this.lastInstanceProfileCheck = new Date();

            String credentialsResponse = InstanceCredentialsUtils.getInstance().readResource(
                    this.credentialsEndpointProvider.getCredentialsEndpoint(),
                    this.credentialsEndpointProvider.getRetryPolicy(),
                    this.credentialsEndpointProvider.getHeaders()
            );

            node = Jackson.fromSensitiveJsonString(credentialsResponse, JsonNode.class);
            secretId = node.get(SECRET_ID);
            secretKey = node.get(SECRET_KEY);
            token = node.get(TOKEN);

            if (null == secretId || null == secretKey) {
                throw new CosClientException("Unable to load credentials");
            }

            if (null != token) {
                this.cosCredentials = new BasicSessionCredentials(secretId.asText(), secretKey.asText(),
                        token.asText());
            } else {
                this.cosCredentials = new BasicCOSCredentials(secretId.asText(), secretKey.asText());
            }

            expirationJsonNode = node.get(CVMCredentialsFetcher.EXPIRATION);
            if (null != expirationJsonNode) {
                String expiration = expirationJsonNode.asText();
                try {
                    this.credentialExpiration = DateUtils.parseISO8601Date(expiration);
                } catch (Exception ex) {
                    this.handleError("Unable to parse credentials expiration date from CVM instance", ex);
                }
            }
        } catch (JsonMappingException e) {
            this.handleError("Unable to parse the returned from service endpoint.", e);
        } catch (IOException e) {
            this.handleError("Unable to load the credentials from service endpoint", e);
        } catch (URISyntaxException e) {
            this.handleError("Unable to load the credentials from service endpoint", e);
        }
    }

    public void refresh() {
        this.cosCredentials = null;
    }

    private void handleError(String errorMessage, Exception e) {
        if (null == this.cosCredentials || this.expired()) {
            if (null == errorMessage && null == e) {
                // Nothing to do
                return;
            }
            if (null != errorMessage && null == e) {
                throw new CosClientException(errorMessage);
            }
            if (null == errorMessage && null != e) {
                throw new CosClientException(e);
            }

            throw new CosClientException(errorMessage, e);
        }

        LOG.warn(errorMessage, e);
    }

    private boolean isWithinExpirationThreshold() {
        return (this.credentialExpiration.getTime() - System.currentTimeMillis()) < CVMCredentialsFetcher.EXPIRATION_THRESHOLD;
    }

    private boolean isPastRefreshThreshold() {
        return (System.currentTimeMillis() - this.lastInstanceProfileCheck.getTime()) > CVMCredentialsFetcher.REFRESH_THRESHOLD;
    }

    private boolean expired() {
        if (null != credentialExpiration) {
            if (credentialExpiration.getTime() < System.currentTimeMillis()) {
                return true;
            }
        }

        return false;
    }
}
