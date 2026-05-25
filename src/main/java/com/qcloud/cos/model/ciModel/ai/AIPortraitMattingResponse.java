package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.model.ObjectMetadata;

import java.io.InputStream;

/**
 * 人像抠图响应类
 * 详情见 https://cloud.tencent.com/document/product/460/79735
 */
public class AIPortraitMattingResponse {
    
    /**
     * 处理后的图片流
     */
    private InputStream imageStream;
    
    /**
     * 对象元数据
     */
    private ObjectMetadata metadata;

    public InputStream getImageStream() {
        return imageStream;
    }

    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }

    public ObjectMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ObjectMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "AIPortraitMattingResponse{" +
                "metadata=" + metadata +
                '}';
    }
}
