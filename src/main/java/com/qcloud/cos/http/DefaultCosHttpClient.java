package com.qcloud.cos.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.Headers;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.CosServiceException.ErrorType;
import com.qcloud.cos.internal.CosErrorResponseHandler;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.internal.CosServiceResponse;

public class DefaultCosHttpClient implements CosHttpClient {

    private ClientConfig clientConfig;
    private RequestConfig requestConfig;
    private HttpClient httpClient;
    private PoolingHttpClientConnectionManager connectionManager;
    private IdleConnectionMonitorThread idleConnectionMonitor;

    private CosErrorResponseHandler errorResponseHandler;
    private static final Logger log = LoggerFactory.getLogger(DefaultCosHttpClient.class);

    public DefaultCosHttpClient(ClientConfig clientConfig) {
        super();
        this.errorResponseHandler = new CosErrorResponseHandler();
        this.clientConfig = clientConfig;
        this.connectionManager = new PoolingHttpClientConnectionManager();
        initHttpClient();
    }

    private void initHttpClient() {
        this.connectionManager.setMaxTotal(this.clientConfig.getMaxConnectionsCount());
        this.connectionManager.setDefaultMaxPerRoute(this.clientConfig.getMaxConnectionsCount());
        this.connectionManager.setValidateAfterInactivity(1);
        HttpClientBuilder httpClientBuilder =
                HttpClients.custom().setConnectionManager(connectionManager);
        if (this.clientConfig.getHttpProxyIp() != null
                && this.clientConfig.getHttpProxyPort() != 0) {
            HttpHost proxy = new HttpHost(this.clientConfig.getHttpProxyIp(),
                    this.clientConfig.getHttpProxyPort());
            httpClientBuilder.setProxy(proxy);
        }
        this.httpClient = httpClientBuilder.build();
        this.requestConfig =
                RequestConfig.custom()
                        .setConnectionRequestTimeout(
                                this.clientConfig.getConnectionRequestTimeout())
                        .setConnectTimeout(this.clientConfig.getConnectionTimeout())
                        .setSocketTimeout(this.clientConfig.getSocketTimeout()).build();
        this.idleConnectionMonitor = new IdleConnectionMonitorThread(this.connectionManager);
        this.idleConnectionMonitor.start();
    }

    @Override
    public void shutdown() {
        this.idleConnectionMonitor.shutdown();
    }


    private <X extends CosServiceRequest> HttpRequestBase buildHttpRequest(
            CosHttpRequest<X> request) throws CosClientException {
        HttpRequestBase httpRequestBase = null;
        HttpMethodName httpMethodName = request.getHttpMethod();
        if (httpMethodName.equals(HttpMethodName.PUT)) {
            httpRequestBase = new HttpPut();
        } else if (httpMethodName.equals(HttpMethodName.GET)) {
            httpRequestBase = new HttpGet();
        } else if (httpMethodName.equals(HttpMethodName.DELETE)) {
            httpRequestBase = new HttpDelete();
        } else if (httpMethodName.equals(HttpMethodName.POST)) {
            httpRequestBase = new HttpPost();
        } else if (httpMethodName.equals(HttpMethodName.HEAD)) {
            httpRequestBase = new HttpHead();
        }

        URIBuilder uriBuilder = new URIBuilder(request.getEndpoint());
        Map<String, String> requestParams = request.getParameters();
        for (Entry<String, String> paramEntry : requestParams.entrySet()) {
            String paramKey = paramEntry.getKey();
            String paramValue = paramEntry.getValue();
            uriBuilder.addParameter(paramKey, paramValue);
        }

        Map<String, List<String>> customParamsList =
                request.getOriginalRequest().getCustomQueryParameters();
        if (customParamsList != null) {
            for (Entry<String, List<String>> customParamsEntry : customParamsList.entrySet()) {
                String paramKey = customParamsEntry.getKey();
                List<String> paramValueList = customParamsEntry.getValue();
                int paramValueNum = paramValueList.size();
                for (int paramValueIndex = 0; paramValueIndex < paramValueNum; ++paramValueIndex) {
                    uriBuilder.addParameter(paramKey, paramValueList.get(paramValueIndex));
                }
            }
        }
        URI uri = null;
        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new CosClientException("build uri error! CosHttpRequest: " + request.toString(),
                    e);
        }
        httpRequestBase.setURI(uri);

        long content_length = 0;
        Map<String, String> requestHeaders = request.getHeaders();
        for (Entry<String, String> headerEntry : requestHeaders.entrySet()) {
            String headerKey = headerEntry.getKey();
            String headerValue = headerEntry.getValue();
            if (headerKey.equals(Headers.CONTENT_LENGTH)) {
                content_length = Long.parseLong(headerValue);
                continue;
            }
            httpRequestBase.addHeader(headerKey, headerValue);
        }

        Map<String, String> customRequestHeaders =
                request.getOriginalRequest().getCustomRequestHeaders();
        if (customRequestHeaders != null) {
            for (Entry<String, String> customHeaderEntry : customRequestHeaders.entrySet()) {
                String headerKey = customHeaderEntry.getKey();
                String headerValue = customHeaderEntry.getValue();
                if (headerKey.equals(Headers.CONTENT_LENGTH)) {
                    content_length = Long.parseLong(headerValue);
                    continue;
                }
                httpRequestBase.addHeader(headerKey, headerValue);
            }
        }


        if (request.getContent() != null) {
            InputStreamEntity reqEntity =
                    new InputStreamEntity(request.getContent(), content_length);
            // reqEntity.setChunked(true);
            if (httpMethodName.equals(HttpMethodName.PUT)
                    || httpMethodName.equals(HttpMethodName.POST)) {
                HttpEntityEnclosingRequestBase entityRequestBase =
                        (HttpEntityEnclosingRequestBase) httpRequestBase;
                entityRequestBase.setEntity(reqEntity);
            }
        }
        httpRequestBase.setConfig(this.requestConfig);
        return httpRequestBase;
    }

    private boolean isRequestSuccessful(HttpResponse httpResponse) {
        StatusLine statusLine = httpResponse.getStatusLine();
        int statusCode = -1;
        if (statusLine != null) {
            statusCode = statusLine.getStatusCode();
        }
        return statusCode / 100 == HttpStatus.SC_OK / 100;
    }

    private <X extends CosServiceRequest> CosHttpResponse createResponse(
            HttpRequestBase httpRequestBase, CosHttpRequest<X> request,
            org.apache.http.HttpResponse apacheHttpResponse) throws IOException {
        CosHttpResponse httpResponse = new CosHttpResponse(request, httpRequestBase);

        if (apacheHttpResponse.getEntity() != null) {
            httpResponse.setContent(apacheHttpResponse.getEntity().getContent());
        }

        httpResponse.setStatusCode(apacheHttpResponse.getStatusLine().getStatusCode());
        httpResponse.setStatusText(apacheHttpResponse.getStatusLine().getReasonPhrase());
        for (Header header : apacheHttpResponse.getAllHeaders()) {
            httpResponse.addHeader(header.getName(), header.getValue());
        }

        return httpResponse;
    }

    private <X extends CosServiceRequest> CosServiceException handlerErrorMessage(
            CosHttpRequest<X> request, HttpRequestBase httpRequestBase,
            final org.apache.http.HttpResponse apacheHttpResponse) throws IOException {
        final StatusLine statusLine = apacheHttpResponse.getStatusLine();
        final int statusCode;
        final String reasonPhrase;
        if (statusLine == null) {
            statusCode = -1;
            reasonPhrase = null;
        } else {
            statusCode = statusLine.getStatusCode();
            reasonPhrase = statusLine.getReasonPhrase();
        }
        CosHttpResponse response = createResponse(httpRequestBase, request, apacheHttpResponse);
        CosServiceException exception = null;
        try {
            exception = errorResponseHandler.handle(response);
            log.debug("Received error response: " + exception);
        } catch (Exception e) {
            // If the errorResponseHandler doesn't work, then check for error
            // responses that don't have any content
            if (statusCode == 413) {
                exception = new CosServiceException("Request entity too large");
                exception.setStatusCode(statusCode);
                exception.setErrorType(ErrorType.Client);
                exception.setErrorCode("Request entity too large");
            } else if (statusCode == 503 && "Service Unavailable".equalsIgnoreCase(reasonPhrase)) {
                exception = new CosServiceException("Service unavailable");
                exception.setStatusCode(statusCode);
                exception.setErrorType(ErrorType.Service);
                exception.setErrorCode("Service unavailable");
            } else {
                String errorMessage = "Unable to unmarshall error response (" + e.getMessage()
                        + "). Response Code: " + (statusLine == null ? "None" : statusCode)
                        + ", Response Text: " + reasonPhrase;
                throw new CosClientException(errorMessage, e);
            }
        }

        exception.setStatusCode(statusCode);
        exception.fillInStackTrace();
        return exception;
    }

    @Override
    public <X, Y extends CosServiceRequest> X exeute(CosHttpRequest<Y> request,
            HttpResponseHandler<CosServiceResponse<X>> responseHandler)
                    throws CosClientException, CosServiceException {
        HttpResponse httpResponse = null;
        HttpRequestBase httpRequest = null;
        int retryIndex = 0;
        int kMaxRetryCnt = 5;
        while (retryIndex < kMaxRetryCnt) {
            try {
                httpRequest = buildHttpRequest(request);
                httpResponse = httpClient.execute(httpRequest);
                break;
            } catch (IOException e) {
                httpRequest.abort();
                ++retryIndex;
                if (retryIndex >= kMaxRetryCnt) {
                    String errMsg = String.format(
                            "httpClient execute occur a Ioexcepiton. httpRequest: %s, excep: %s",
                            request.toString(), e);
                    log.error(errMsg);
                    throw new CosClientException(errMsg);
                }
            }
        }
        if (!isRequestSuccessful(httpResponse)) {
            try {
                throw handlerErrorMessage(request, httpRequest, httpResponse);
            } catch (IOException ioe) {
                log.info("Unable to execute HTTP request: " + ioe.getMessage(), ioe);
                CosServiceException cse = new CosServiceException(
                        "Unable to execute HTTP request: " + ioe.getMessage(), ioe);
                throw cse;
            } finally {
                httpRequest.abort();
                httpRequest.releaseConnection();
            }
        }
        try {
            CosHttpResponse cosHttpResponse = createResponse(httpRequest, request, httpResponse);
            return responseHandler.handle(cosHttpResponse).getResult();
        } catch (Exception e) {
            log.info("Unable to execute Response handle: " + e.getMessage(), e);
            CosClientException cce = new CosClientException(
                    "Unable to execute Response handle: " + e.getMessage(), e);
            throw cce;
        } finally {
            if (!responseHandler.needsConnectionLeftOpen()) {
                httpRequest.abort();
                httpRequest.releaseConnection();
            }
        }
    }

}
