package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 通用图片分析（任务式）响应中的 AnalysisResult 容器。
 *
 * <p>根据 Type 不同，返回 DescriptionResult 或 CustomResult 之一。</p>
 */
@XStreamAlias("AnalysisResult")
public class AIImageAnalysisJobResult {

    /**
     * 分析类型，取值：Description、Custom
     */
    @XStreamAlias("Type")
    private String type;

    /**
     * 图片综合描述分析结果，包含描述和标签，当 type=Description 时返回
     */
    @XStreamAlias("DescriptionResult")
    private DescriptionResult descriptionResult;

    /**
     * 自定义模式的分析结果，当 type=Custom 时返回
     */
    @XStreamAlias("CustomResult")
    private JobCustomResult customResult;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DescriptionResult getDescriptionResult() {
        return descriptionResult;
    }

    public void setDescriptionResult(DescriptionResult descriptionResult) {
        this.descriptionResult = descriptionResult;
    }

    public JobCustomResult getCustomResult() {
        return customResult;
    }

    public void setCustomResult(JobCustomResult customResult) {
        this.customResult = customResult;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AIImageAnalysisJobResult{");
        sb.append("type='").append(type).append('\'');
        sb.append(", descriptionResult=").append(descriptionResult);
        sb.append(", customResult=").append(customResult);
        sb.append('}');
        return sb.toString();
    }
}
