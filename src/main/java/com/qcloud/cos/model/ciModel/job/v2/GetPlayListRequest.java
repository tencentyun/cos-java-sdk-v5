package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.internal.CIServiceRequest;

public class GetPlayListRequest extends CIServiceRequest {
    private String object;
    /**
     * 私有 ts 资源 url 下载凭证的相对有效期，单位为秒 [3600,43200]
     */
    private String expires;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
