package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 分析结果容器，包含分析类型和对应的结果
 */
@XStreamAlias("AnalysisResult")
public class AnalysisResult {

    /**
     * 分析类型：ImageLabels / Custom
     */
    @XStreamAlias("Type")
    private String type;

    /**
     * 图片标签类型的分析结果，当 Type 为 ImageLabels 时返回
     */
    @XStreamAlias("ImageLabelsResult")
    private ImageLabelsResult imageLabelsResult;

    /**
     * 自定义分析结果，当 Type 为 Custom 时返回
     */
    @XStreamAlias("CustomResult")
    private CustomResult customResult;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ImageLabelsResult getImageLabelsResult() {
        return imageLabelsResult;
    }

    public void setImageLabelsResult(ImageLabelsResult imageLabelsResult) {
        this.imageLabelsResult = imageLabelsResult;
    }

    public CustomResult getCustomResult() {
        return customResult;
    }

    public void setCustomResult(CustomResult customResult) {
        this.customResult = customResult;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AnalysisResult{");
        sb.append("type='").append(type).append('\'');
        sb.append(", imageLabelsResult=").append(imageLabelsResult);
        sb.append(", customResult=").append(customResult);
        sb.append('}');
        return sb.toString();
    }
}
