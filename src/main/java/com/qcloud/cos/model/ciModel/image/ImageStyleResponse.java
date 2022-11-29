package com.qcloud.cos.model.ciModel.image;


import com.qcloud.cos.internal.CIPicServiceRequest;

import java.util.ArrayList;
import java.util.List;

public class ImageStyleResponse extends CIPicServiceRequest {
    /**
     * bucket信息
     */
    private String bucketName;

    /**
     * 样式规则
     */
    private List<StyleRule> styleRule;


    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public List<StyleRule> getStyleRule() {
        if (styleRule == null) {
            styleRule = new ArrayList<>();
        }
        return styleRule;
    }

    public void setStyleRule(List<StyleRule> styleRule) {
        this.styleRule = styleRule;
    }
}
