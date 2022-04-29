package com.qcloud.cos.model.ciModel.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆识别请求实体 参数详情参考：https://cloud.tencent.com/document/product/460/63225
 */
public class DetectCarResponse {
    /**
     * 车辆属性识别的结果数组，支持返回多个车辆信息
     */
    private List<CarTag> carTags = new ArrayList<>();

    /**
     * 请求id
     */
    private String requestId;

    public List<CarTag> getCarTags() {
        return carTags;
    }

    public void setCarTags(List<CarTag> carTags) {
        this.carTags = carTags;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DetectCarResponse{");
        sb.append("carTags=").append(carTags);
        sb.append(", requestId='").append(requestId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
