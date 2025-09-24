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


package com.qcloud.cos;

import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.endpoint.DefaultEndpointResolver;
import com.qcloud.cos.endpoint.EndpointBuilder;
import com.qcloud.cos.endpoint.EndpointResolver;
import com.qcloud.cos.endpoint.RegionEndpointBuilder;
import com.qcloud.cos.endpoint.SuffixEndpointBuilder;
import com.qcloud.cos.http.DefaultHandlerAfterProcess;
import com.qcloud.cos.http.HandlerAfterProcess;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.retry.BackoffStrategy;
import com.qcloud.cos.retry.PredefinedBackoffStrategies;
import com.qcloud.cos.retry.PredefinedRetryPolicies;
import com.qcloud.cos.retry.RetryPolicy;
import com.qcloud.cos.utils.VersionInfoUtils;

public class ClientConfig {

    // 默认的获取连接的超时时间, 单位ms
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = -1;
    // 默认连接超时, 单位ms
    private static final int DEFAULT_CONNECTION_TIMEOUT = 30 * 1000;
    // 默认的SOCKET读取超时时间, 单位ms
    private static final int DEFAULT_SOCKET_TIMEOUT = 30 * 1000;
    // 默认请求超时时间, 单位ms
    private static final int DEFAULT_REQUEST_TIMEOUT = 5 * 60 * 1000;
    // 线程池关闭最长等待时间, 单位ms
    private static final int DEFAULT_SHUTDOWN_TIMEOUT = 60 * 1000;
    // 默认的维护最大HTTP连接数
    private static final int DEFAULT_MAX_CONNECTIONS_COUNT = 1024;
    private static final int DEFAULT_IDLE_CONNECTION_ALIVE = 60 * 1000;
    // 多次签名的默认过期时间,单位秒
    private static final long DEFAULT_SIGN_EXPIRED = 3600;
    // 默认的user_agent标识
    private static final String DEFAULT_USER_AGENT = VersionInfoUtils.getUserAgent();
    // Read Limit
    private static final int DEFAULT_READ_LIMIT = (2 << 17) + 1;
    /**
     * default retry times is 3 when retryable exception occured
     **/
    private static final int DEFAULT_RETRY_TIMES = 3;
    /**
     * The max retry times if retryable exception occured
     **/
    private int maxErrorRetry = DEFAULT_RETRY_TIMES;

    private int maxErrorRetryForCopyRequest = DEFAULT_RETRY_TIMES;
    /**
     * The retry policy if exception occured
     **/
    private static final RetryPolicy DEFAULT_RETRY_POLICY = PredefinedRetryPolicies.DEFAULT;
    /**
     * The sleep time interval between exception occured and retry
     **/
    public static final BackoffStrategy DEFAULT_BACKOFF_STRATEGY = PredefinedBackoffStrategies.DEFAULT;
    private Region region;
    private HttpProtocol httpProtocol = HttpProtocol.https;
    private String endPointSuffix = null;
    private EndpointBuilder endpointBuilder = null;
    private EndpointResolver endpointResolver = new DefaultEndpointResolver();
    private RetryPolicy retryPolicy = DEFAULT_RETRY_POLICY;
    private BackoffStrategy backoffStrategy = DEFAULT_BACKOFF_STRATEGY;

    // http proxy代理，如果使用http proxy代理，需要设置IP与端口
    private String httpProxyIp = null;
    private int httpProxyPort = 0;
    private String proxyUsername = null;
    private String proxyPassword = null;
    private boolean useBasicAuth = false;
    private long signExpired = DEFAULT_SIGN_EXPIRED;
    private int connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT;
    private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    private int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
    private int maxConnectionsCount = DEFAULT_MAX_CONNECTIONS_COUNT;
    private int idleConnectionAlive = DEFAULT_IDLE_CONNECTION_ALIVE;
    private String userAgent = DEFAULT_USER_AGENT;
    private int readLimit = DEFAULT_READ_LIMIT;
    private COSSigner cosSigner = new COSSigner();

    private int requestTimeout = DEFAULT_REQUEST_TIMEOUT;
    private int shutdownTimeout = DEFAULT_SHUTDOWN_TIMEOUT;
    private boolean isRequestTimeOutEnable = false;

    // 数据万象特殊请求配置
    private boolean ciSpecialRequest = false;

    //是否区分host与endpoint
    private  boolean isDistinguishHost = false;

    private boolean isShortConnection = false;

    private boolean isChangeEndpointRetry = false;

    private boolean isPrintShutdownStackTrace = true;

    private boolean isCheckRequestPath = true;

    private int timeoutClientThreadSize = 0;

    private int errorLogStatusCodeThresh = 500;

    private boolean checkSSLCertificate = true;

    private boolean useDefaultDnsResolver = true;

    private HandlerAfterProcess handlerAfterProcess = new DefaultHandlerAfterProcess();

    private boolean isRefreshEndpointAddr = false;

    private boolean checkPreflightStatus = true;

    private long preflightStatusUpdateInterval = 10 * 1000L;

    private boolean isRedirectsEnabled = false;

    private boolean useConnectionMonitor = false;

    private boolean retryAfterPreflight = false;

    private long connectionMaxIdleMillis = 60 * 1000;

    private boolean addLogDebugHeader = true;

    private boolean throw412Directly = false;

    private boolean throw304Directly = false;

    private boolean renameFaultTolerant = false;

    // 不传入region 用于后续调用List Buckets(获取所有的bucket信息)
    public ClientConfig() {
        super();
        this.region = null;
        this.endpointBuilder = new RegionEndpointBuilder(this.region);
    }

    // 除了List Buckets, 其他API需要传入region(比如上传，下载，遍历bucket文件)
    public ClientConfig(Region region) {
        super();
        this.region = region;
        this.endpointBuilder = new RegionEndpointBuilder(this.region);
    }

    public int getIdleConnectionAlive() {
        return this.idleConnectionAlive;
    }

    public void setIdleConnectionAlive(int idleConnectionAlive) {
        this.idleConnectionAlive = idleConnectionAlive;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
        this.endpointBuilder = new RegionEndpointBuilder(this.region);
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public void setHttpProtocol(HttpProtocol httpProtocol) {
        this.httpProtocol = httpProtocol;
    }

    public String getHttpProxyIp() {
        return httpProxyIp;
    }

    public void setHttpProxyIp(String httpProxyIp) {
        this.httpProxyIp = httpProxyIp;
    }

    public int getHttpProxyPort() {
        return httpProxyPort;
    }

    public void setHttpProxyPort(int httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    public long getSignExpired() {
        return signExpired;
    }

    public void setSignExpired(long signExpired) {
        this.signExpired = signExpired;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getMaxConnectionsCount() {
        return maxConnectionsCount;
    }

    public void setMaxConnectionsCount(int maxConnectionsCount) {
        this.maxConnectionsCount = maxConnectionsCount;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }

    @Deprecated
    public String getEndPointSuffix() {
        return endPointSuffix;
    }

    public void setEndPointSuffix(String endPointSuffix) {
        this.endPointSuffix = endPointSuffix;
        this.endpointBuilder = new SuffixEndpointBuilder(endPointSuffix);
    }

    public int getReadLimit() {
        return readLimit;
    }

    public void setReadLimit(int readLimit) {
        this.readLimit = readLimit;
    }

    public EndpointBuilder getEndpointBuilder() {
        return endpointBuilder;
    }

    public void setEndpointBuilder(EndpointBuilder endpointBuilder) {
        this.endpointBuilder = endpointBuilder;
    }

    public EndpointResolver getEndpointResolver() {
        return endpointResolver;
    }

    public void setEndpointResolver(EndpointResolver endpointResolver) {
        this.endpointResolver = endpointResolver;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public void setUseBasicAuth(boolean useBasicAuth) {
        this.useBasicAuth = useBasicAuth;
    }

    public boolean useBasicAuth() {
        return useBasicAuth;
    }

    public int getMaxErrorRetry() {
        return maxErrorRetry;
    }

    public void setMaxErrorRetry(int maxErrorRetry) {
        this.maxErrorRetry = maxErrorRetry;
    }

    public int getMaxErrorRetryForCopyRequest() {
        return maxErrorRetryForCopyRequest;
    }

    public void setMaxErrorRetryForCopyRequest(int maxErrorRetry) {
        this.maxErrorRetryForCopyRequest = maxErrorRetry;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public BackoffStrategy getBackoffStrategy() {
        return backoffStrategy;
    }

    public void setBackoffStrategy(BackoffStrategy backoffStrategy) {
        this.backoffStrategy = backoffStrategy;
    }

    public COSSigner getCosSigner() {
        return cosSigner;
    }

    public void setCosSigner(COSSigner cosSigner) {
        this.cosSigner = cosSigner;
    }

    public boolean getCiSpecialRequest() {
        return ciSpecialRequest;
    }

    public void setCiSpecialRequest(boolean ciSpecialRequest) {
        this.ciSpecialRequest = ciSpecialRequest;
    }

    public void setIsDistinguishHost(boolean isDistinguishHost) {
        this.isDistinguishHost = isDistinguishHost;
    }

    public boolean getIsDistinguishHost() {
        return isDistinguishHost;
    }

    /**
     * 显示的设置使用短链接，在请求头中增加"Connection: close"
     * HTTP 1.1默认使用长链接
     */
    public void setShortConnection() {
        isShortConnection = true;
    }

    public boolean isShortConnection() {
        return isShortConnection;
    }

    public int getRequestTimeout () {
        return requestTimeout;
    }

    public void setRequestTimeout (int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public void setRequestTimeOutEnable(boolean requestTimeOutEnable) {
        this.isRequestTimeOutEnable = requestTimeOutEnable;
    }

    public boolean getRequestTimeOutEnable() {
        return isRequestTimeOutEnable && (requestTimeout > 0);
    }

    public void setShutdownTimeout(int shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }

    public int getShutdownTimeout() {
        return shutdownTimeout;
    }

    public boolean isChangeEndpointRetry() {
        return isChangeEndpointRetry;
    }

    public void setChangeEndpointRetry(boolean changeEndpointRetry) {
        isChangeEndpointRetry = changeEndpointRetry;
    }

    public boolean isPrintShutdownStackTrace() {
        return isPrintShutdownStackTrace;
    }

    public void setPrintShutdownStackTrace(boolean printShutdownStackTrace) {
        isPrintShutdownStackTrace = printShutdownStackTrace;
    }

    public void setCheckRequestPath(boolean isCheck) {
        isCheckRequestPath = isCheck;
    }

    public boolean isCheckRequestPath() {
        return isCheckRequestPath;
    }

    public int getTimeoutClientThreadSize() {
        return timeoutClientThreadSize;
    }

    public void setTimeoutClientThreadSize(int poolSize) {
        timeoutClientThreadSize = poolSize;
    }

    public void setErrorLogStatusCodeThresh(int statusCode) {
        errorLogStatusCodeThresh = statusCode;
    }

    public int getErrorLogStatusCodeThresh() {
        return errorLogStatusCodeThresh;
    }

    public void setCheckSSLCertificate(boolean isCheckSSLCertificate) {
        checkSSLCertificate = isCheckSSLCertificate;
    }

    public boolean isCheckSSLCertificate() {
        return checkSSLCertificate;
    }

    public void setUseDefaultDnsResolver(boolean useDefaultDnsResolver) {
        this.useDefaultDnsResolver = useDefaultDnsResolver;
    }

    public boolean isUseDefaultDnsResolver() {
        return useDefaultDnsResolver;
    }

    public void setHandlerAfterProcess(HandlerAfterProcess handler) {
        this.handlerAfterProcess = handler;
    }

    public HandlerAfterProcess getHandlerAfterProcess() {
        return handlerAfterProcess;
    }

    public void turnOnRefreshEndpointAddrSwitch() {
        isRefreshEndpointAddr = true;
    }

    public boolean IsRefreshEndpointAddr() {
        return isRefreshEndpointAddr;
    }

    public boolean isCheckPreflightStatus() {
        return checkPreflightStatus;
    }

    public void setCheckPreflightStatus(boolean checkPreflightStatus) {
        this.checkPreflightStatus = checkPreflightStatus;
    }

    public long getPreflightStatusUpdateInterval() {
        return preflightStatusUpdateInterval;
    }

    public boolean isRedirectsEnabled() {
        return isRedirectsEnabled;
    }

    public void setRedirectsEnabled(boolean redirectsEnabled) {
        isRedirectsEnabled = redirectsEnabled;
    }

    public boolean isUseConnectionMonitor() {
        return useConnectionMonitor;
    }

    public void setUseConnectionMonitor(boolean isUseConnectionMonitor) {
        useConnectionMonitor = isUseConnectionMonitor;
    }

    public long getConnectionMaxIdleMillis() {
        return connectionMaxIdleMillis;
    }

    public void setConnectionMaxIdleMillis(long connectionMaxIdleMillis) {
        this.connectionMaxIdleMillis = connectionMaxIdleMillis;
    }

    public boolean isRetryAfterPreflight() {
        return retryAfterPreflight;
    }

    public void setRetryAfterPreflight(boolean retryAfterPreflight) {
        this.retryAfterPreflight = retryAfterPreflight;
    }

    public boolean isAddLogDebugHeader() {
        return addLogDebugHeader;
    }

    public void setAddLogDebugHeader(boolean addLogDebugHeader) {
        this.addLogDebugHeader = addLogDebugHeader;
    }

    public boolean isThrow412Directly() {
        return throw412Directly;
    }

    public void setThrow412Directly(boolean throw412Directly) {
        this.throw412Directly = throw412Directly;
    }

    public boolean isThrow304Directly() {
        return throw304Directly;
    }

    public void setThrow304Directly(boolean throw304Directly) {
        this.throw304Directly = throw304Directly;
    }

    public boolean isRenameFaultTolerant() {
        return renameFaultTolerant;
    }

    public void setRenameFaultTolerant(boolean renameFaultTolerant) {
        this.renameFaultTolerant = renameFaultTolerant;
    }
}
