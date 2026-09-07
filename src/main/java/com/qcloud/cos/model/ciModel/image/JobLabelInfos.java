package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用图片分析（任务式）Description 模式下的 LabelInfos 容器。
 *
 * <p>每一组 LabelInfos 代表一个识别对象（一个物品/一组维度），对应一个置信度，包含多个 LabelInfo。</p>
 *
 * <p>与旧接口的 {@link LabelInfos} 区分：本类使用 {@code ConfidenceLevel}（每组独立置信度），
 * 而旧的 {@code LabelInfos} 不含置信度字段（置信度由外层 LabelDetail 承载）。</p>
 *
 * <p>XML 示例：</p>
 * <pre>{@code
 * <LabelInfos>
 *     <ConfidenceLevel>high</ConfidenceLevel>
 *     <LabelInfo>
 *         <LabelName>Brand</LabelName>
 *         <LabelValue>Nike</LabelValue>
 *     </LabelInfo>
 *     <LabelInfo>
 *         <LabelName>Category</LabelName>
 *         <LabelValue>运动鞋</LabelValue>
 *     </LabelInfo>
 * </LabelInfos>
 * }</pre>
 */
@XStreamAlias("LabelInfos")
public class JobLabelInfos {

    /**
     * 标签置信度，值以模型返回的为准。
     * 预设场景下默认为 high、medium、low 三个等级，每组 LabelInfos 对应一个置信度。
     */
    @XStreamAlias("ConfidenceLevel")
    private String confidenceLevel;

    /**
     * 标签信息子节点列表，每个 LabelInfo 包含一对：标签名称 + 标签值。
     * 复用旧的 {@link LabelInfo} 类型（结构完全一致）。
     */
    @XStreamImplicit(itemFieldName = "LabelInfo")
    private List<LabelInfo> labelInfo = new ArrayList<>();

    public String getConfidenceLevel() {
        return confidenceLevel;
    }

    public void setConfidenceLevel(String confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    public List<LabelInfo> getLabelInfo() {
        if (labelInfo == null) {
            labelInfo = new ArrayList<>();
        }
        return labelInfo;
    }

    public void setLabelInfo(List<LabelInfo> labelInfo) {
        this.labelInfo = labelInfo;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobLabelInfos{");
        sb.append("confidenceLevel='").append(confidenceLevel).append('\'');
        sb.append(", labelInfo=").append(labelInfo);
        sb.append('}');
        return sb.toString();
    }
}
