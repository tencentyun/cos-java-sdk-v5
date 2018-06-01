package com.qcloud.cos.http;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

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
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.Headers;
import com.qcloud.cos.event.ProgressInputStream;
import com.qcloud.cos.event.ProgressListener;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.CosServiceException.ErrorType;
import com.qcloud.cos.internal.CosErrorResponseHandler;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.internal.CosServiceResponse;
import com.qcloud.cos.internal.ReleasableInputStream;
import com.qcloud.cos.internal.ResettableInputStream;
import com.qcloud.cos.internal.SdkBufferedInputStream;
import com.qcloud.cos.utils.UrlEncoderUtils;

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
        this.idleConnectionMonitor.setDaemon(true);
        this.idleConnectionMonitor.start();
    }

    @Override
    public void shutdown() {
        this.idleConnectionMonitor.shutdown();
    }

    // 因为Apache HTTP库自带的URL Encode对一些特殊字符如*等不进行转换, 和COS HTTP服务的URL Encode标准不一致
    private <X extends CosServiceRequest> URI buildUri(CosHttpRequest<X> request) {
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(request.getProtocol().toString()).append("://")
                .append(request.getEndpoint());
        String encodedPath = UrlEncoderUtils.encodeEscapeDelimiter(request.getResourcePath());
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
        }


        try {
            URI uri = new URI(urlBuffer.toString());
            return uri;
        } catch (URISyntaxException e) {
            throw new CosClientException("build uri error! url: " + urlBuffer.toString() + ", CosHttpRequest: " + request.toString(),
                    e);
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
            httpRequestBase = new HttpDelete();
        } else if (httpMethodName.equals(HttpMethodName.POST)) {
            httpRequestBase = new HttpPost();
        } else if (httpMethodName.equals(HttpMethodName.HEAD)) {
            httpRequestBase = new HttpHead();
        } else {
            throw new CosClientException("unsupported http method " + httpMethodName);
        }


        httpRequestBase.setURI(buildUri(request));

        long content_length = -1;
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

    @Override
    public <X, Y extends CosServiceRequest> X exeute(CosHttpRequest<Y> request,
            HttpResponseHandler<CosServiceResponse<X>> responseHandler)
                    throws CosClientException, CosServiceException {

        HttpResponse httpResponse = null;
        HttpRequestBase httpRequest = null;
        int retryIndex = 0;
        int kMaxRetryCnt = 5;
        bufferAndResetAbleContent(request);

        // Always mark the input stream before execution.
        ProgressListener progressListener = request.getProgressListener();
        final InputStream originalContent = request.getContent();
        if (originalContent != null) {
            request.setContent(monitorStreamProgress(progressListener, originalContent));
        }
        if (originalContent != null && originalContent.markSupported() && !(originalContent instanceof BufferedInputStream)) {
            final int readLimit = clientConfig.getReadLimit();
            originalContent.mark(readLimit);
        }


        while (retryIndex < kMaxRetryCnt) {
            try {
                checkInterrupted();
                if (originalContent instanceof BufferedInputStream && originalContent.markSupported()) {
                    // Mark everytime for BufferedInputStream, since the marker could have been invalidated
                    final int readLimit = clientConfig.getReadLimit();
                    originalContent.mark(readLimit);
                }
                // 如果是重试的则恢复流
                if (retryIndex != 0 && originalContent != null) {
                    originalContent.reset();
                }
                HttpContext context = HttpClientContext.create();
                httpRequest = buildHttpRequest(request);
                httpResponse = httpClient.execute(httpRequest, context);
                break;
            } catch (IOException e) {
                httpRequest.abort();
                ++retryIndex;
                if (retryIndex >= kMaxRetryCnt) {
                    String errMsg = String.format(
                            "httpClient execute occur a IOexcepiton. httpRequest: %s, excep: %s",
                            request.toString(), e);
                    log.error(errMsg);
                    throw new CosClientException(errMsg);
                } else {
                    String warnMsg = String.format(
                            "httpClient execute occur a IOexcepiton, ready to retry[%d/%d]. httpRequest: %s, excep: %s",
                            retryIndex, kMaxRetryCnt, request.toString(), e);
                    log.warn(warnMsg);
                    // 加入sleep 避免雪崩
                    int threadSleepMs = ThreadLocalRandom.current().nextInt(10, 100);
                    try {
                        Thread.sleep(threadSleepMs);
                    } catch (InterruptedException e1) {
                        throw new CosClientException("operation has been interrupted!");
                    }
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

}
