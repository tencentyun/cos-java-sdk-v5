package com.qcloud.cos.model.ciModel.snapshot;

import com.qcloud.cos.model.CosServiceResult;

import java.io.Serializable;

/**
 * 媒体截图请求实体  详情见：https://cloud.tencent.com/document/product/460/38934
 */
public class PrivateM3U8Response extends CosServiceResult implements Serializable {

    /**
     * 私有 ts 资源 url 下载凭证的相对有效期，单位为秒，范围为[3600, 43200]
     */
    private String m3u8;

    public String getM3u8() {
        return m3u8;
    }

    public void setM3u8(String m3u8) {
        this.m3u8 = m3u8;
    }
}
