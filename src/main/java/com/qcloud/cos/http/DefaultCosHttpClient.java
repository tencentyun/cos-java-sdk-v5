/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.

 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos.http;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.Headers;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.endpoint.CIRegionEndpointBuilder;
import com.qcloud.cos.endpoint.CIPicRegionEndpointBuilder;
import com.qcloud.cos.internal.cihandler.HttpEntityEnclosingDelete;
import com.qcloud.cos.internal.CIPicServiceRequest;
import com.qcloud.cos.model.ListBucketsRequest;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.event.ProgressInputStream;
import com.qcloud.cos.event.ProgressListener;
import com.qcloud.cos.exception.AbortedException;
import com.qcloud.cos.exception.ClientExceptionConstants;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.CosServiceException.ErrorType;
import com.qcloud.cos.exception.ResponseNotCompleteException;
import com.qcloud.cos.exception.ExceptionLogDetail;
import com.qcloud.cos.internal.CosErrorResponseHandler;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.internal.CosServiceResponse;
import com.qcloud.cos.internal.ReleasableInputStream;
import com.qcloud.cos.internal.ResettableInputStream;
import com.qcloud.cos.internal.SdkBufferedInputStream;
import com.qcloud.cos.internal.CIWorkflowServiceRequest;
import com.qcloud.cos.internal.CIServiceRequest;
import com.qcloud.cos.internal.CosClientAbortTaskMonitor;
import com.qcloud.cos.retry.BackoffStrategy;
import com.qcloud.cos.retry.RetryPolicy;
import com.qcloud.cos.utils.CodecUtils;
import com.qcloud.cos.utils.ExceptionUtils;
import com.qcloud.cos.utils.UrlEncoderUtils;
import com.qcloud.cos.utils.ValidationUtils;

import org.apache.commons.codec.binary.Base64;
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
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;

public class DefaultCosHttpClient implements CosHttpClient {

    protected ClientConfig clientConfig;
    private RequestConfig requestConfig;
    protected HttpClient httpClient;
    private PoolingHttpClientConnectionManager connectionManager;
    private IdleConnectionMonitorThread idleConnectionMonitorThread;
    private int maxErrorRetry;
    private RetryPolicy retryPolicy;
    private BackoffStrategy backoffStrategy;

    private CosErrorResponseHandler errorResponseHandler;
    private HandlerAfterProcess handlerAfterProcess;
    private final CosHttpClientTimer cosHttpClientTimer;
    private static final Logger log = LoggerFactory.getLogger(DefaultCosHttpClient.class);

    public DefaultCosHttpClient(ClientConfig clientConfig) {
        super();
        this.errorResponseHandler = new CosErrorResponseHandler();
        this.clientConfig = clientConfig;
        this.handlerAfterProcess = clientConfig.getHandlerAfterProcess();
        DnsResolver dnsResolver = new DnsResolver() {
            @Override
            public InetAddress[] resolve(String host) throws UnknownHostException {
                InetAddress[] addresses = InetAddress.getAllByName(host);
                List<InetAddress> addressList = new ArrayList<>(Arrays.asList(addresses));
                Collections.shuffle(addressList);

                InetAddress[] newAddresses = addressList.toArray(new InetAddress[0]);
                return newAddresses;
            }
        };

        if (clientConfig.isCheckSSLCertificate()) {
            if (clientConfig.isUseDefaultDnsResolver()) {
                this.connectionManager = new PoolingHttpClientConnectionManager();
            } else {
                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
                this.connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, dnsResolver);
            }
        } else {
            try {
                SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial((chain, authType) -> true).build();

                SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", sslSocketFactory).build();
                if (clientConfig.isUseDefaultDnsResolver()) {
                    this.connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                } else {
                    this.connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, dnsResolver);
                }
            } catch (NoSuchAlgorithmException e) {
                log.error("fail to init http client: ", e);
                throw new RuntimeException(e);
            } catch (KeyStoreException e) {
                log.error("fail to init http client: ", e);
                throw new RuntimeException(e);
            } catch (KeyManagementException e) {
                log.error("fail to init http client: ", e);
                throw new RuntimeException(e);
            }
        }
        this.maxErrorRetry = clientConfig.getMaxErrorRetry();
        this.retryPolicy = ValidationUtils.assertNotNull(clientConfig.getRetryPolicy(), "retry policy");
        this.retryPolicy.setRetryAfterPreflight(clientConfig.isRetryAfterPreflight());
        this.backoffStrategy = ValidationUtils.assertNotNull(clientConfig.getBackoffStrategy(), "backoff strategy");
        this.cosHttpClientTimer = new CosHttpClientTimer();
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
                        .setContentCompressionEnabled(false)
                        .setConnectionRequestTimeout(
                                this.clientConfig.getConnectionRequestTimeout())
                        .setConnectTimeout(this.clientConfig.getConnectionTimeout())
                        .setSocketTimeout(this.clientConfig.getSocketTimeout())
                        .setRedirectsEnabled(this.clientConfig.isRedirectsEnabled())
                        .build();
        if (clientConfig.isUseConnectionMonitor()) {
            IdleConnectionMonitor.registerConnectionManager(this.connectionManager, clientConfig.getConnectionMaxIdleMillis());
        } else {
            this.idleConnectionMonitorThread = new IdleConnectionMonitorThread(this.connectionManager);
            this.idleConnectionMonitorThread.setIdleAliveMS(this.clientConfig.getIdleConnectionAlive());
            this.idleConnectionMonitorThread.setDaemon(true);
            this.idleConnectionMonitorThread.start();
        }
    }

    @Override
    public void shutdown() {
        if (clientConfig.isPrintShutdownStackTrace()) {
            StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

            StringBuilder trace = new StringBuilder("shutdown stackTrace:");
            for (int i = 1; i < stackTraces.length; i++) {
                trace.append("\n");
                StackTraceElement element = stackTraces[i];
                String stackTrace = "Class: " + element.getClassName()
                        + ", Method: " + element.getMethodName()
                        + ", Line: " + element.getLineNumber();
                trace.append(stackTrace);
            }
            log.info(trace.toString());
        }
        cosHttpClientTimer.shutdown();
        if (clientConfig.isUseConnectionMonitor()) {
            IdleConnectionMonitor.removeConnectionManager(this.connectionManager);
            this.connectionManager.shutdown();
        } else {
            this.idleConnectionMonitorThread.shutdown();
        }
    }

    // 因为Apache HTTP库自带的URL Encode对一些特殊字符如*等不进行转换, 和COS HTTP服务的URL Encode标准不一致
    private <X extends CosServiceRequest> URI buildUri(CosHttpRequest<X> request) {
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(request.getProtocol().toString()).append("://")
                .append(request.getEndpoint());
        String encodedPath = UrlEncoderUtils.encodeUrlPath(request.getResourcePath());
        urlBuffer.append(encodedPath);
        StringBuffer paramBuffer = new StringBuffer();
        boolean seeOne = false;
        Map<String, String> requestParams = new HashMap<>();
        requestParams.putAll(request.getParameters());
        Map<String, List<String>> customParamsList =
                request.getOriginalRequest().getCustomQueryParameters();
        if (customParamsList != null) {
            for (Entry<String, List<String>> customParamsEntry : customParamsList.entrySet()) {
                String paramKey = customParamsEntry.getKey();
                List<String> paramValueList = customParamsEntry.getValue();
                int paramValueNum = paramValueList.size();
                for (int paramValueIndex = 0; paramValueIndex < paramValueNum; ++paramValueIndex) {
                    requestParams.put(paramKey, paramValueList.get(paramValueIndex));
                }
            }
        }
        for (Entry<String, String> paramEntry : requestParams.entrySet()) {
            String paramKey = paramEntry.getKey();
            if (paramKey == null) {
                continue;
            }
            if (seeOne) {
                paramBuffer.append("&");
            }
            paramBuffer.append(UrlEncoderUtils.encode(paramKey));
            if (!seeOne) {
                seeOne = true;
            }
            String paramValue = paramEntry.getValue();
            if (paramValue == null) {
                continue;
            }
            paramBuffer.append("=");
            paramBuffer.append(UrlEncoderUtils.encode(paramValue));
        }

        String paramStr = paramBuffer.toString();
        if (!paramStr.isEmpty()) {
            urlBuffer.append("?").append(paramStr);
            //万象接口特殊逻辑 要求某些特定的参数置于末尾
            if (request.getCiSpecialEndParameter() != null) {
                urlBuffer.append("&").append(request.getCiSpecialEndParameter());
            }
        } else {
            if (request.getCiSpecialEndParameter() != null) {
                urlBuffer.append("?").append(request.getCiSpecialEndParameter());
            }
        }

        try {
            URI uri = new URI(urlBuffer.toString());
            return uri;
        } catch (URISyntaxException e) {
            throw new CosClientException("build uri error! url: " + urlBuffer.toString()
                    + ", CosHttpRequest: " + request.toString(), e);
        }
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
            if (request.getOriginalRequest() instanceof CIServiceRequest) {
                httpRequestBase = new HttpEntityEnclosingDelete();
            } else {
                httpRequestBase = new HttpDelete();
            }
        } else if (httpMethodName.equals(HttpMethodName.POST)) {
            httpRequestBase = new HttpPost();
        } else if (httpMethodName.equals(HttpMethodName.HEAD)) {
            httpRequestBase = new HttpHead();
        } else {
            throw new CosClientException("unsupported http method " + httpMethodName);
        }

        httpRequestBase.setURI(buildUri(request));

        long contentLength = -1;
        Map<String, String> requestHeaders = request.getHeaders();
        for (Entry<String, String> headerEntry : requestHeaders.entrySet()) {
            String headerKey = headerEntry.getKey();
            String headerValue = headerEntry.getValue();
            if (headerKey.equals(Headers.CONTENT_LENGTH)) {
                contentLength = Long.parseLong(headerValue);
                continue;
            }
            headerValue = CodecUtils.convertFromUtf8ToIso88591(headerValue);
            httpRequestBase.addHeader(headerKey, headerValue);
        }

        Map<String, String> customRequestHeaders =
                request.getOriginalRequest().getCustomRequestHeaders();

        if (customRequestHeaders != null) {
            for (Entry<String, String> customHeaderEntry : customRequestHeaders.entrySet()) {
                String headerKey = customHeaderEntry.getKey();
                String headerValue = customHeaderEntry.getValue();
                if (headerKey.equals(Headers.CONTENT_LENGTH)) {
                    contentLength = Long.parseLong(headerValue);
                    continue;
                }
                headerValue = CodecUtils.convertFromUtf8ToIso88591(headerValue);
                httpRequestBase.addHeader(headerKey, headerValue);
            }
        }

        if (clientConfig.isAddLogDebugHeader()) {
            if (log.isDebugEnabled()) {
                httpRequestBase.addHeader(Headers.SDK_LOG_DEBUG, "on");
            } else {
                httpRequestBase.addHeader(Headers.SDK_LOG_DEBUG, "off");
            }
        }

        if (clientConfig.isShortConnection()) {
            httpRequestBase.addHeader(Headers.CONNECTION, "close");
        }

        if (request.getContent() != null) {
            InputStreamEntity reqEntity =
                    new InputStreamEntity(request.getContent(), contentLength);
            if (httpMethodName.equals(HttpMethodName.PUT)
                    || httpMethodName.equals(HttpMethodName.POST)) {
                HttpEntityEnclosingRequestBase entityRequestBase =
                        (HttpEntityEnclosingRequestBase) httpRequestBase;
                entityRequestBase.setEntity(reqEntity);
            } else if (httpMethodName.equals(HttpMethodName.DELETE) &&
                    request.getOriginalRequest() instanceof CIServiceRequest) {
                HttpEntityEnclosingRequestBase entityRequestBase =
                        (HttpEntityEnclosingDelete) httpRequestBase;
                entityRequestBase.setEntity(reqEntity);
            }
        }
        httpRequestBase.setConfig(this.requestConfig);
        if (clientConfig.useBasicAuth()) {
            // basic auth认证
            setBasicProxyAuthorization(httpRequestBase);
        }
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
        ProgressListener progressListener = request.getProgressListener();
        CosHttpResponse httpResponse = new CosHttpResponse(request, httpRequestBase);

        if (apacheHttpResponse.getEntity() != null) {
            InputStream oriIn = apacheHttpResponse.getEntity().getContent();
            InputStream progressIn = null;
            if (oriIn != null) {
                progressIn = ProgressInputStream.inputStreamForResponse(oriIn, progressListener);
                httpResponse.setContent(progressIn);
            }
        }

        httpResponse.setStatusCode(apacheHttpResponse.getStatusLine().getStatusCode());
        httpResponse.setStatusText(apacheHttpResponse.getStatusLine().getReasonPhrase());
        for (Header header : apacheHttpResponse.getAllHeaders()) {
            String value = CodecUtils.convertFromIso88591ToUtf8(header.getValue());
            httpResponse.addHeader(header.getName(), value);
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

    private <X extends CosServiceRequest> void bufferAndResetAbleContent(
            CosHttpRequest<X> request) {
        final InputStream origContent = request.getContent();
        if (origContent != null) {
            final InputStream toBeClosed = buffer(makeResettable(origContent));
            // make "notCloseable", so reset would work with retries
            final InputStream notCloseable = (toBeClosed == null) ? null
                    : ReleasableInputStream.wrap(toBeClosed).disableClose();
            request.setContent(notCloseable);
        }
    }

    /**
     * Wrap with a {@link ProgressInputStream} to report request progress to listener.
     *
     * @param listener Listener to report to
     * @param content Input stream to monitor progress for
     * @return Wrapped input stream with progress monitoring capabilities.
     */
    private InputStream monitorStreamProgress(ProgressListener listener, InputStream content) {
        return ProgressInputStream.inputStreamForRequest(content, listener);
    }

    private void setBasicProxyAuthorization(HttpRequestBase httpRequest) {
        String auth = clientConfig.getProxyUsername() + ":" + clientConfig.getProxyPassword();
        String authHeader = "Basic " + new String(Base64.encodeBase64(auth.getBytes()));
        httpRequest.addHeader("Proxy-Authorization", authHeader);
    }

    private <X extends CosServiceRequest> void checkResponse(CosHttpRequest<X> request,
            HttpRequestBase httpRequest,
            HttpResponse httpResponse) {
        if (!isRequestSuccessful(httpResponse)) {
            try {
                throw handlerErrorMessage(request, httpRequest, httpResponse);
            } catch (IOException ioe) {
                String errorMsg = "Unable to execute HTTP request: " + ioe.getMessage();
                log.error(errorMsg, ioe);
                CosServiceException cse = new CosServiceException(errorMsg, ioe);
                throw cse;
            } finally {
                httpRequest.abort();
            }
        }
    }

    private <X extends CosServiceRequest> boolean isRetryableRequest(CosHttpRequest<X> request) {
        if (request.getContent() == null || request.getContent().markSupported()) {
            return true;
        }
        log.info("The content of the request is not null and not markSupported, the request is not retryable");
        return false;
    }

    private <X extends CosServiceRequest> boolean shouldRetry(CosHttpRequest<X> request, HttpResponse response,
            Exception exception, int retryIndex,
            RetryPolicy retryPolicy) {
        if (retryIndex >= maxErrorRetry) {
            return false;
        }

        if (!isRetryableRequest(request)) {
            return false;
        }

        if (exception instanceof CosServiceException) {
            if (((CosServiceException) exception).getStatusCode() == 301 || ((CosServiceException) exception).getStatusCode() == 302 || ((CosServiceException) exception).getStatusCode() == 307) {
                return shouldRetry3xxException(request, (CosServiceException) exception);
            }
        }

        if (retryPolicy.shouldRetry(request, response, exception, retryIndex)) {
            return true;
        }
        return false;
    }

    private <X extends CosServiceRequest> boolean shouldRetry3xxException(CosHttpRequest<X> request, CosServiceException cse) {
        if (!clientConfig.isChangeEndpointRetry() || (cse.getRequestId() != null && !cse.getRequestId().isEmpty())) {
            return false;
        }

        Map<String, String> reqHeaders = request.getHeaders();
        if (!reqHeaders.isEmpty() && reqHeaders.containsKey(Headers.HOST)) {
            String lastEndpoint = request.getEndpoint();
            String lastHost = reqHeaders.get(Headers.HOST);

            return isCosDefaultHost(lastHost, lastEndpoint);
        }

        return false;
    }

    protected HttpResponse executeOneRequest(HttpContext context, HttpRequestBase httpRequest) throws Exception{
        return httpClient.execute(httpRequest, context);
    }

    private void closeHttpResponseStream(HttpResponse httpResponse) {
        try {
            if (httpResponse != null && httpResponse.getEntity() != null
                    && httpResponse.getEntity().getContent() != null) {
                httpResponse.getEntity().getContent().close();
            }
        } catch (IOException e) {
            log.error("exception occur:", e);
        }
    }

    @Override
    public <X, Y extends CosServiceRequest> X exeute(CosHttpRequest<Y> request,
            HttpResponseHandler<CosServiceResponse<X>> responseHandler)
            throws CosClientException, CosServiceException {

        HttpResponse httpResponse = null;
        HttpRequestBase httpRequest = null;
        bufferAndResetAbleContent(request);

        // Always mark the input stream before execution.
        ProgressListener progressListener = request.getProgressListener();
        final InputStream originalContent = request.getContent();
        if (originalContent != null) {
            request.setContent(monitorStreamProgress(progressListener, originalContent));
        }
        if (originalContent != null && originalContent.markSupported()
                && !(originalContent instanceof BufferedInputStream)) {
            final int readLimit = clientConfig.getReadLimit();
            originalContent.mark(readLimit);
        }

        long startTime = 0;
        long endTime = 0;
        int  response_status = 0;

        int retryIndex = 0;
        while (true) {
            try {
                checkInterrupted();
                if (originalContent instanceof BufferedInputStream
                        && originalContent.markSupported()) {
                    // Mark everytime for BufferedInputStream, since the marker could have been
                    // invalidated
                    final int readLimit = clientConfig.getReadLimit();
                    originalContent.mark(readLimit);
                }
                // 如果是重试的则恢复流
                if (retryIndex != 0 && originalContent != null) {
                    originalContent.reset();
                }
                if (retryIndex != 0) {
                    response_status = 0;
                    if (clientConfig.IsRefreshEndpointAddr()) {
                        refreshEndpointAddr(request);
                    }

                    long delay = backoffStrategy.computeDelayBeforeNextRetry(retryIndex);
                    request.addHeader("x-cos-sdk-retry", "true");
                    Thread.sleep(delay);
                }
                HttpContext context = HttpClientContext.create();
                httpRequest = buildHttpRequest(request);
                httpResponse = null;
                startTime = System.currentTimeMillis();
                if (clientConfig.getRequestTimeOutEnable()) {
                    httpResponse = executeRequestWithTimeout(context, httpRequest, request);
                } else {
                    httpResponse = executeRequest(context, httpRequest);
                }
                checkResponse(request, httpRequest, httpResponse);
                break;
            } catch (CosServiceException cse) {
                response_status = -1;
                closeHttpResponseStream(httpResponse);
                String errorMsg = String.format("failed to execute http request due to service exception, request timeStamp %d,"
                                + " httpRequest: %s, retryIdx:%d, maxErrorRetry:%d", System.currentTimeMillis(), request,
                        retryIndex, maxErrorRetry);
                request.addLogDetails(new ExceptionLogDetail(cse, errorMsg));
                if (!shouldRetry(request, httpResponse, cse, retryIndex, retryPolicy)) {
                    int status_code_thresh = clientConfig.getErrorLogStatusCodeThresh();
                    if (status_code_thresh < 0) {
                        status_code_thresh = 500;
                    }
                    if (cse.getStatusCode() >= status_code_thresh) {
                        handleLog(request);
                    }
                    throw cse;
                }
                changeEndpointForRetry(request, httpResponse, retryIndex);
            } catch (CosClientException cce) {
                response_status = -1;
                closeHttpResponseStream(httpResponse);
                String errorMsg = String.format("failed to execute http request due to client exception, request timeStamp %d,"
                                + " httpRequest: %s, retryIdx:%d, maxErrorRetry:%d", System.currentTimeMillis(), request,
                        retryIndex, maxErrorRetry);
                request.addLogDetails(new ExceptionLogDetail(cce, errorMsg));
                if (!shouldRetry(request, httpResponse, cce, retryIndex, retryPolicy)) {
                    handleLog(request);
                    throw cce;
                }
                changeEndpointForRetry(request, httpResponse, retryIndex);
            } catch (Exception exp) {
                response_status = -1;
                String expName = exp.getClass().getName();
                String errorMsg = String.format("httpClient execute occur an unknown exception:%s, httpRequest: %s", expName, request);
                closeHttpResponseStream(httpResponse);
                log.error(errorMsg, exp);
                throw new CosClientException(errorMsg, exp);
            } catch (Error e) {
                String errorMsg = String.format("httpClient execute occur an error, httpRequest: %s", request);
                closeHttpResponseStream(httpResponse);
                log.error(errorMsg, e);
                throw e;
            } finally {
                endTime = System.currentTimeMillis();
                handlerAfterProcess.handle(response_status, endTime - startTime);
                ++retryIndex;
            }
        }

        try {
            CosHttpResponse cosHttpResponse = createResponse(httpRequest, request, httpResponse);
            return responseHandler.handle(cosHttpResponse).getResult();
        } catch (Exception e) {
            if (e.getMessage().equals("Premature end of chunk coded message body: closing chunk expected")) {
                throw new ResponseNotCompleteException("response chunk not complete", e);
            }

            String errorMsg = "Unable to execute response handle: " + e.getMessage();
            log.info(errorMsg, e);
            CosClientException cce = new CosClientException(errorMsg, e);
            throw cce;
        } finally {
            if (!responseHandler.needsConnectionLeftOpen()) {
                httpRequest.releaseConnection();
            }
        }
    }

    /**
     * Make input stream resettable if possible.
     *
     * @param content Input stream to make resettable
     * @return ResettableInputStream if possible otherwise original input stream.
     */
    private InputStream makeResettable(InputStream content) {
        if (!content.markSupported()) {
            // try to wrap the content input stream to become
            // mark-and-resettable for signing and retry purposes.
            if (content instanceof FileInputStream) {
                try {
                    // ResettableInputStream supports mark-and-reset without
                    // memory buffering
                    return new ResettableInputStream((FileInputStream) content);
                } catch (IOException e) {
                    if (log.isDebugEnabled()) {
                        log.debug("For the record; ignore otherwise", e);
                    }
                }
            }
        }
        return content;
    }

    /**
     * Buffer input stream if possible.
     *
     * @param content Input stream to buffer
     * @return SdkBufferedInputStream if possible, otherwise original input stream.
     */
    private InputStream buffer(InputStream content) {
        if (!content.markSupported()) {
            content = new SdkBufferedInputStream(content);
        }
        return content;
    }

    // check interrupted
    private void checkInterrupted() throws CosClientException {
        if (Thread.interrupted()) {
            throw new CosClientException("operation has been interrupted!");
        }
    }

    private HttpResponse executeRequest(HttpContext context, HttpRequestBase httpRequest) throws Exception {
        HttpResponse httpResponse = null;
        try {
            httpResponse = executeOneRequest(context, httpRequest);
        } catch (IOException e) {
            httpRequest.abort();
            throw ExceptionUtils.createClientException(e);
        } catch (InterruptedException e) {
            httpRequest.abort();
            throw new CosClientException(e.getMessage(), e);
        } catch (TimeoutException e) {
            httpRequest.abort();
            String errorMsg = "ExecutorService: time out after waiting  " + this.clientConfig.getRequestTimeout()/1000 + " seconds";
            throw new CosClientException(errorMsg, ClientExceptionConstants.REQUEST_TIMEOUT, e);
        } catch (ExecutionException e) {
            httpRequest.abort();
            if (e.getCause() instanceof IOException) {
                throw ExceptionUtils.createClientException((IOException)e.getCause());
            }
            throw new CosServiceException(e.getMessage(),e);
        }

        return httpResponse;
    }

    private <Y extends CosServiceRequest> HttpResponse executeRequestWithTimer(HttpContext context, HttpRequestBase httpRequest, CosHttpRequest<Y> originRequest) throws Exception {
        CosClientAbortTaskMonitor abortTaskMonitor = cosHttpClientTimer.startTimer(clientConfig.getRequestTimeout());
        abortTaskMonitor.setCurrentHttpRequest(httpRequest);
        HttpResponse httpResponse = null;
        try {
            originRequest.setClientAbortTaskMonitor(abortTaskMonitor);
            httpResponse = executeOneRequest(context, httpRequest);
        } catch (IOException ie) {
            if (originRequest.getClientAbortTaskMonitor().hasTimeoutExpired()) {
                Thread.interrupted();
                String errorMsg = String.format("catch IOException when executing http request[%s], and execution aborted task has been done, exp:", originRequest);
                log.error(errorMsg, ie);
                throw new InterruptedException();
            }
            throw ie;
        } finally {
            originRequest.getClientAbortTaskMonitor().cancelTask();
        }

        return httpResponse;
    }

    private <Y extends CosServiceRequest> HttpResponse executeRequestWithTimeout(HttpContext context, HttpRequestBase httpRequest, CosHttpRequest<Y> originRequest) throws Exception {
        try {
            return executeRequestWithTimer(context, httpRequest, originRequest);
        } catch (InterruptedException ie) {
            if (originRequest.getClientAbortTaskMonitor().hasTimeoutExpired()) {
                Thread.interrupted();
                String errorMsg = "InterruptedException: time out after waiting  " + this.clientConfig.getRequestTimeout()/1000 + " seconds";
                throw new CosClientException(errorMsg, ClientExceptionConstants.REQUEST_TIMEOUT, ie);
            }
            if (!httpRequest.isAborted()) {
                httpRequest.abort();
            }
            throw ie;
        } catch (AbortedException ae) {
            if (originRequest.getClientAbortTaskMonitor().hasTimeoutExpired()) {
                Thread.interrupted();
                String errorMsg = "AbortedException: time out after waiting  " + this.clientConfig.getRequestTimeout()/1000 + " seconds";
                throw new CosClientException(errorMsg, ClientExceptionConstants.REQUEST_TIMEOUT, ae);
            }
            if (!httpRequest.isAborted()) {
                httpRequest.abort();
            }
            throw ae;
        } catch (IOException ie) {
            if (!httpRequest.isAborted()) {
                httpRequest.abort();
            }
            throw ExceptionUtils.createClientException(ie);
        } finally {
            if (originRequest.getClientAbortTaskMonitor().hasTimeoutExpired()) {
                Thread.interrupted();
            }
        }
    }

    private <Y extends CosServiceRequest> void handleLog(CosHttpRequest<Y> request) {
        for (ExceptionLogDetail logDetail : request.getExceptionsLogDetails()) {
            log.error(logDetail.getErrMsg(), logDetail.getException());
        }
    }

    private <Y extends CosServiceRequest> void changeEndpointForRetry(CosHttpRequest<Y> request, HttpResponse httpResponse, int retryIndex) {
        if (httpResponse != null) {
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = -1;
            if (statusLine != null) {
                statusCode = statusLine.getStatusCode();
            }

            if (statusCode == 301 || statusCode == 302 || statusCode == 307) {
                changeRequestHost(request);
                return;
            }
        }

        if (!clientConfig.isChangeEndpointRetry() || retryIndex != (maxErrorRetry - 1)) {
            return;
        }

        if (httpResponse != null) {
            for (Header header : httpResponse.getAllHeaders()) {
                if (Objects.equals(header.getName(), Headers.REQUEST_ID)) {
                    String value = CodecUtils.convertFromIso88591ToUtf8(header.getValue());
                    if (value != null && !value.isEmpty()) {
                        return;
                    }
                }
            }
        }

        Map<String, String> reqHeaders = request.getHeaders();
        if (!reqHeaders.isEmpty() && reqHeaders.containsKey(Headers.HOST)) {
            String lastEndpoint = request.getEndpoint();
            String lastHost = reqHeaders.get(Headers.HOST);

            if (isCosDefaultHost(lastHost, lastEndpoint)) {
                changeRequestHost(request);
            }
        }
    }

    private <X extends CosServiceRequest> void refreshEndpointAddr(CosHttpRequest<X> request) throws CosClientException {
        boolean isCIRequest = request.getOriginalRequest() instanceof CIServiceRequest;
        boolean isServiceRequest = request.getOriginalRequest() instanceof ListBucketsRequest;
        String endpoint = "";
        String endpointAddr = "";
        if (isServiceRequest) {
            endpoint = clientConfig.getEndpointBuilder().buildGetServiceApiEndpoint();
            endpointAddr =
                    clientConfig.getEndpointResolver().resolveGetServiceApiEndpoint(endpoint);
        } else {

            if (request.getOriginalRequest() instanceof CIPicServiceRequest) {
                endpoint = new CIPicRegionEndpointBuilder(clientConfig.getRegion()).buildGeneralApiEndpoint(request.getBucketName());
            } else if (isCIRequest) {
                endpoint = new CIRegionEndpointBuilder(clientConfig.getRegion()).buildGeneralApiEndpoint(request.getBucketName());
            } else {
                endpoint = clientConfig.getEndpointBuilder().buildGeneralApiEndpoint(request.getBucketName());
            }
            endpointAddr = clientConfig.getEndpointResolver().resolveGeneralApiEndpoint(endpoint);
        }

        if (endpoint == null) {
            throw new CosClientException("endpoint is null, please check your endpoint builder");
        }
        if (endpointAddr == null) {
            throw new CosClientException(
                    "endpointAddr is null, please check your endpoint resolver");
        }

        String fixedEndpointAddr = request.getOriginalRequest().getFixedEndpointAddr();
        if (fixedEndpointAddr != null) {
            request.setEndpoint(fixedEndpointAddr);
        } else {
            request.setEndpoint(endpointAddr);
        }
    }

    private boolean isCosDefaultHost(String host, String endPoint) {
        String regex = ".+-\\d+\\.cos\\..+\\.myqcloud\\.com";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherEndpoint = pattern.matcher(endPoint);
        Matcher matcherHost = pattern.matcher(host);
        boolean isAccEndpoint = endPoint.endsWith("cos.accelerate.myqcloud.com");
        boolean isAccHost = host.endsWith("cos.accelerate.myqcloud.com");

        return matcherEndpoint.matches() && matcherHost.matches() && !isAccEndpoint && !isAccHost;
    }

    private <Y extends CosServiceRequest> void changeRequestHost(CosHttpRequest<Y> request) {
        String retryEndpoint = String.format("%s.%s.tencentcos.cn", request.getBucketName(), Region.formatRegion(clientConfig.getRegion()));
        request.addHeader(Headers.HOST, retryEndpoint);
        COSSigner cosSigner = clientConfig.getCosSigner();
        COSCredentials cosCredentials = request.getCosCredentials();
        CosServiceRequest cosServiceRequest = request.getOriginalRequest();
        Date expiredTime = new Date(System.currentTimeMillis() + clientConfig.getSignExpired() * 1000);
        boolean isCIWorkflowRequest = cosServiceRequest instanceof CIWorkflowServiceRequest;
        cosSigner.setCIWorkflowRequest(isCIWorkflowRequest);
        cosSigner.sign(request, cosCredentials, expiredTime);

        String endpointAddr = clientConfig.getEndpointResolver().resolveGeneralApiEndpoint(retryEndpoint);

        String fixedEndpointAddr = request.getOriginalRequest().getFixedEndpointAddr();
        if (fixedEndpointAddr != null) {
            request.setEndpoint(fixedEndpointAddr);
        } else {
            request.setEndpoint(endpointAddr);
        }
    }
}
