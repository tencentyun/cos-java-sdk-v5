package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签详情容器，包含标签信息和置信度
 *
 * <p>其中可包含多个 LabelInfos，每组 LabelInfos 代表一个物品，对应一个置信度。</p>
 *
 * <p>XML 示例：</p>
 * <pre>{@code
 * <LabelDetail>
 *     <LabelInfos>
 *         <LabelInfo>
 *             <LabelName>Brand</LabelName>
 *             <LabelValue>Nike</LabelValue>
 *         </LabelInfo>
 *         <LabelInfo>
 *             <LabelName>Category</LabelName>
 *             <LabelValue>上衣</LabelValue>
 *         </LabelInfo>
 *     </LabelInfos>
 *     <Confidence>high</Confidence>
 * </LabelDetail>
 * }</pre>
 */
@XStreamAlias("LabelDetail")
public class LabelDetail {

    /**
     * 标签信息容器列表，每组 LabelInfos 代表一个物品，包含多个 LabelInfo 元素。
     * 默认情况下仅返回一组，支持多组是为后续客户特殊情况预留。
     */
    @XStreamImplicit(itemFieldName = "LabelInfos")
    private List<LabelInfos> labelInfos = new ArrayList<>();

    /**
     * 标签置信度，值以模型返回的为准。
     * 电商标签默认为 high、medium、low 三个等级，每组 LabelInfos 对应一个置信度。
     */
    @XStreamAlias("Confidence")
    private String confidence;

    public List<LabelInfos> getLabelInfos() {
        return labelInfos;
    }

    public void setLabelInfos(List<LabelInfos> labelInfos) {
        this.labelInfos = labelInfos;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LabelDetail{");
        sb.append("labelInfos=").append(labelInfos);
        sb.append(", confidence='").append(confidence).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
