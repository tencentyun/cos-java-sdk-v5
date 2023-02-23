package com.qcloud.cos.retry;

import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.internal.CosServiceRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DefaultThrottledRetryPolicy extends RetryPolicy {
    private static final Logger log = LoggerFactory.getLogger(DefaultThrottledRetryPolicy.class);
    private static final int THROTTLED_RETRY_COST = 5;
    private static final int DEFAULT_MAX_CAPACITY = 500;
    private final RetryTokenBucket retryTokenBucket;

    public DefaultThrottledRetryPolicy() {
        this(DEFAULT_MAX_CAPACITY);
    }

    public DefaultThrottledRetryPolicy(int maxCapacity) {
        this.retryTokenBucket = new RetryTokenBucket(maxCapacity);
    }

    @Override
    public <X extends CosServiceRequest> boolean shouldRetry(CosHttpRequest<X> request, HttpResponse response, Exception exception, int retryIndex){
        if (RetryUtils.isRetryableServiceException(exception)) {
            CosServiceException cse = (CosServiceException) exception;
            if (cse.getStatusCode() == 503) {
                return applyRetryToken(request, THROTTLED_RETRY_COST);
            }
            return true;
        }

        // Always retry on client exceptions caused by IOException
        if (exception.getCause() instanceof IOException) {
            return true;
        }
        return false;
    }

    private <X extends CosServiceRequest> boolean applyRetryToken(CosHttpRequest<X> request, int applyTokens) {
        if (!retryTokenBucket.apply(applyTokens)) {
            String logMsg = String.format("httpRequest: %s will not retry cause the availableCapacity of retryTokenBucket is %d " +
                    "which is less than the apply tokens %d", request.toString(), retryTokenBucket.getAvailableCapacity(), applyTokens);
            log.warn(logMsg);
            return false;
        }
        request.setLastRetryTokens(applyTokens);
        return true;
    }

    public <X extends CosServiceRequest> void releaseToken(CosHttpRequest<X> request, HttpResponse httpResponse) {
        if (httpResponse == null) {
            return;
        }
        StatusLine statusLine = httpResponse.getStatusLine();
        int statusCode = -1;
        if (statusLine != null) {
            statusCode = statusLine.getStatusCode();
        }
        if (statusCode / 100 != HttpStatus.SC_OK / 100){
            return;
        }

        // only release the retry tokens when the request is successful
        if (request.isRetryRequset()) {
            retryTokenBucket.release(request.getLastRetryTokens());
            return;
        }
        retryTokenBucket.release();
    }
}
