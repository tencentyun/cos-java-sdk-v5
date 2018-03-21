package com.qcloud.cos;

import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;

public class ClientConfig {
    // 默认的获取连接的超时时间, 单位ms
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = -1;
    // 默认连接超时, 单位ms
    private static final int DEFAULT_CONNECTION_TIMEOUT = 30 * 1000;
    // 默认的SOCKET读取超时时间, 单位ms
    private static final int DEFAULT_SOCKET_TIMEOUT = 30 * 1000;
    // 默认的维护最大HTTP连接数
    private static final int DEFAULT_MAX_CONNECTIONS_COUNT = 1024;
    // 多次签名的默认过期时间,单位秒
    private static final int DEFAULT_SIGN_EXPIRED = 300;
    // 默认的user_agent标识
    private static final String DEFAULT_USER_AGENT = "cos-java-sdk-v5.4.0";
    // Read Limit
    private static final int DEFAULT_READ_LIMIT = (2 << 17) + 1;
    
    private Region region;
    private HttpProtocol httpProtocol = HttpProtocol.http;
    private String endPointSuffix = null;
    
    // http proxy代理，如果使用http proxy代理，需要设置IP与端口
    private String httpProxyIp = null;
    private int httpProxyPort = 0;
    private int signExpired = DEFAULT_SIGN_EXPIRED;
    private int connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT;
    private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    private int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
    private int maxConnectionsCount = DEFAULT_MAX_CONNECTIONS_COUNT;
    private String userAgent = DEFAULT_USER_AGENT;
    private int readLimit = DEFAULT_READ_LIMIT;

    public ClientConfig(Region region) {
        super();
        this.region = region;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
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

    public int getSignExpired() {
        return signExpired;
    }

    public void setSignExpired(int signExpired) {
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

    public String getEndPointSuffix() {
        return endPointSuffix;
    }

    public void setEndPointSuffix(String endPointSuffix) {
        this.endPointSuffix = endPointSuffix;
    }

    public int getReadLimit() {
        return readLimit;
    }

    public void setReadLimit(int readLimit) {
        this.readLimit = readLimit;
    }

}
