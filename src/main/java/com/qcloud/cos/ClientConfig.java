package com.qcloud.cos;

import com.qcloud.cos.endpoint.DefaultEndpointResolver;
import com.qcloud.cos.endpoint.EndpointBuilder;
import com.qcloud.cos.endpoint.EndpointResolver;
import com.qcloud.cos.endpoint.RegionEndpointBuilder;
import com.qcloud.cos.endpoint.SuffixEndpointBuilder;
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
    private static final long DEFAULT_SIGN_EXPIRED = 3600;
    // 默认的user_agent标识
    private static final String DEFAULT_USER_AGENT = "cos-java-sdk-v5.5.1";
    // Read Limit
    private static final int DEFAULT_READ_LIMIT = (2 << 17) + 1;

    private Region region;
    private HttpProtocol httpProtocol = HttpProtocol.http;
    private String endPointSuffix = null;
    private EndpointBuilder endpointBuilder = null;
    private EndpointResolver endpointResolver = new DefaultEndpointResolver();

    // http proxy代理，如果使用http proxy代理，需要设置IP与端口
    private String httpProxyIp = null;
    private int httpProxyPort = 0;
    private long signExpired = DEFAULT_SIGN_EXPIRED;
    private int connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT;
    private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    private int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
    private int maxConnectionsCount = DEFAULT_MAX_CONNECTIONS_COUNT;
    private String userAgent = DEFAULT_USER_AGENT;
    private int readLimit = DEFAULT_READ_LIMIT;

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

    /**
     * @return
     */
    @Deprecated
    public String getEndPointSuffix() {
        return endPointSuffix;
    }

    /**
     * @param endPointSuffix
     * @deprecated please use {@link #setEndpointBuilder(EndpointBuilder)} and ${SuffixEndpointBuilder}
     */
    @Deprecated
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
}
