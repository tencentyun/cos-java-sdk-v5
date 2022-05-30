package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.model.CosServiceResult;

import java.util.ArrayList;
import java.util.List;

public class ImageSearchResponse extends CosServiceResult {
    /**
     * 数量
     */
    private String count;

    /**
     * 请求的唯一 ID
     */
    private String requestId;

    /**
     * 图片信息
     */
    private List<ImageInfos> imageInfos = new ArrayList<>();

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String getRequestId() {
        return requestId;
    }

    @Override
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<ImageInfos> getImageInfos() {
        return imageInfos;
    }

    public void setImageInfos(List<ImageInfos> imageInfos) {
        this.imageInfos = imageInfos;
    }

    @Override
    public String toString() {
        return "ImageSearchResponse{" +
                "count='" + count + '\'' +
                ", requestId='" + requestId + '\'' +
                ", imageInfos=" + imageInfos +
                '}';
    }
}
