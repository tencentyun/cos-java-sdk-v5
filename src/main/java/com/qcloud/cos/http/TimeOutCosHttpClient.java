package com.qcloud.cos.http;

import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.utils.ExceptionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.*;

public class TimeOutCosHttpClient  extends DefaultCosHttpClient{
    private ExecutorService threadPool;
    private static final Logger log = LoggerFactory.getLogger(TimeOutCosHttpClient.class);

    public TimeOutCosHttpClient(ClientConfig clientConfig) {
        super(clientConfig);
        threadPool =  Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5);
    }

    @Override
    public HttpResponse executeOneRequest(HttpContext context, HttpRequestBase httpRequest) {
        HttpResponse httpResponse = null;
        HttpRequestTask httpRequestTask = new HttpRequestTask(httpRequest, context);
        Future<HttpResponse> future = threadPool.submit(httpRequestTask);

        try {
            httpResponse = future.get(this.clientConfig.getRequestTimeout(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            httpRequest.abort();
            throw new CosClientException(e.getMessage(),e);
        } catch (TimeoutException e) {
            httpRequest.abort();
            throw new CosClientException("ExecutorService: time out after waiting  " + this.clientConfig.getRequestTimeout()/1000 + " seconds");
        } catch (Exception e) {
            httpRequest.abort();
            if (e.getCause() instanceof IOException) {
                throw ExceptionUtils.createClientException((IOException)e.getCause());
            }
            throw new CosServiceException(e.getMessage(),e);
        }

        return httpResponse;
    }

    @Override
    public void shutdown() {
        threadPool.shutdown();

        try {
            int shutdown_timeout = clientConfig.getShutdownTimeout();
            if (!threadPool.awaitTermination(shutdown_timeout, TimeUnit.MILLISECONDS)) {
                log.warn("The threadPool has not shutdown successfully during the last " + shutdown_timeout/1000 + " seconds");
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
        }
        super.shutdown();
    }

    class HttpRequestTask implements Callable<HttpResponse> {
        private HttpRequestBase httpRequest;
        private HttpContext httpContext;

        public HttpRequestTask(HttpRequestBase httpRequest, HttpContext httpContext) {
            this.httpRequest = httpRequest;
            this.httpContext = httpContext;
        }

        @Override
        public HttpResponse call() throws Exception {
            return httpClient.execute(httpRequest, httpContext);
        }
    }
}
