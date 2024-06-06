package com.qcloud.cos.model.ciModel.snapshot;

import com.qcloud.cos.internal.CosServiceRequest;

import java.io.Serializable;

/**
 * 媒体截图请求实体  详情见：https://cloud.tencent.com/document/product/460/38934
 */
public class PrivateM3U8Request extends CosServiceRequest implements Serializable {

    /**
     * cos bucket名称
     */
    private String bucketName;
    /**
     * 对象在cos上的相对位置
     */
    private String object;

    /**
     * 私有 ts 资源 url 下载凭证的相对有效期，单位为秒，范围为[3600, 43200]
     */
    private String expires;

    private String tokenType;
    private String token;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PrivateM3U8Request{");
        sb.append("expires='").append(expires).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
