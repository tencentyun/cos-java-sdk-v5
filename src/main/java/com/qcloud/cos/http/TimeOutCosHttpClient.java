package com.qcloud.cos.http;

import com.qcloud.cos.ClientConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.Callable;

public class TimeOutCosHttpClient  extends DefaultCosHttpClient{
    private ThreadPoolExecutor threadPool;
    private static final Logger log = LoggerFactory.getLogger(TimeOutCosHttpClient.class);

    public TimeOutCosHttpClient(ClientConfig clientConfig) {
        super(clientConfig);
        int poolSize = clientConfig.getTimeoutClientThreadSize();
        if (poolSize <= 0) {
            poolSize = Runtime.getRuntime().availableProcessors() * 5;
        }

        threadPool = new ThreadPoolExecutor(poolSize, poolSize * 2, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(poolSize * 20), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        threadPool.allowCoreThreadTimeOut(true);
    }

    @Override
    protected HttpResponse executeOneRequest(HttpContext context, HttpRequestBase httpRequest) throws Exception{
        HttpRequestTask httpRequestTask = new HttpRequestTask(httpRequest, context);
        Future<HttpResponse> future = threadPool.submit(httpRequestTask);
        return future.get(this.clientConfig.getRequestTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void shutdown() {
        threadPool.shutdown();

        try {
            int shutdownTimeout = clientConfig.getShutdownTimeout();
            if (!threadPool.awaitTermination(shutdownTimeout, TimeUnit.MILLISECONDS)) {
                log.warn("The threadPool has not shutdown successfully during the last " + shutdownTimeout/1000 + " seconds");
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
