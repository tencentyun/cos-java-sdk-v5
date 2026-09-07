package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 通用图片分析（任务式）Description 模式的分析结果容器。
 *
 * <p>包含图片的整体描述和识别到的标签。</p>
 *
 * <p>XML 示例：</p>
 * <pre>{@code
 * <DescriptionResult>
 *     <Description>三张图片展示了同一款运动鞋……</Description>
 *     <LabelDetail>
 *         <LabelInfos>
 *             <ConfidenceLevel>high</ConfidenceLevel>
 *             <LabelInfo>
 *                 <LabelName>Brand</LabelName>
 *                 <LabelValue>Nike</LabelValue>
 *             </LabelInfo>
 *         </LabelInfos>
 *     </LabelDetail>
 * </DescriptionResult>
 * }</pre>
 */
@XStreamAlias("DescriptionResult")
public class DescriptionResult {

    /**
     * 图片的整体描述内容
     */
    @XStreamAlias("Description")
    private String description;

    /**
     * 识别到的标签结果容器，可包含多组 LabelInfos。
     * 仅当大模型成功输出有效标签时返回；若模型未输出标签或过滤后无有效标签，该字段为 null。
     */
    @XStreamAlias("LabelDetail")
    private JobLabelDetail labelDetail;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobLabelDetail getLabelDetail() {
        return labelDetail;
    }

    public void setLabelDetail(JobLabelDetail labelDetail) {
        this.labelDetail = labelDetail;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DescriptionResult{");
        sb.append("description='").append(description).append('\'');
        sb.append(", labelDetail=").append(labelDetail);
        sb.append('}');
        return sb.toString();
    }
}
