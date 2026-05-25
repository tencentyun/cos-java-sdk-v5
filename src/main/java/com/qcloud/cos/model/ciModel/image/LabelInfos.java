package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签信息容器，每一组 LabelInfos 代表一个物品，对应一个置信度，包含多个 LabelInfo
 *
 * <p>默认情况下仅支持返回一组物品的标签，支持多个是为后续客户特殊情况需要返回多个而预留的。</p>
 *
 * <p>电商场景 XML 示例（包含 LabelInfo 子节点）：</p>
 * <pre>{@code
 * <LabelInfos>
 *     <LabelInfo>
 *         <LabelName>Brand</LabelName>
 *         <LabelValue>Nike</LabelValue>
 *     </LabelInfo>
 *     <LabelInfo>
 *         <LabelName>Category</LabelName>
 *         <LabelValue>上衣</LabelValue>
 *     </LabelInfo>
 * </LabelInfos>
 * }</pre>
 *
 * <p>通用场景 XML 示例（直接包含 labelName/labelValue）：</p>
 * <pre>{@code
 * <LabelInfos>
 *     <labelName>first_label</labelName>
 *     <labelValue>美妆</labelValue>
 * </LabelInfos>
 * }</pre>
 */
@XStreamAlias("LabelInfos")
public class LabelInfos {

    /**
     * 标签信息列表，包含多个 LabelInfo 元素（电商场景使用）
     */
    @XStreamImplicit(itemFieldName = "LabelInfo")
    private List<LabelInfo> labelInfo = new ArrayList<>();

    /**
     * 标签名称（通用场景使用），例如 first_label、second_label、third_label
     *
     * <p>通用场景下 LabelInfos 直接包含 labelName/labelValue，不再有 LabelInfo 子节点。</p>
     */
    @XStreamAlias("labelName")
    private String labelName;

    /**
     * 标签值（通用场景使用），例如 美妆、人像摄影、室内自拍
     */
    @XStreamAlias("labelValue")
    private String labelValue;

    public List<LabelInfo> getLabelInfo() {
        return labelInfo;
    }

    public void setLabelInfo(List<LabelInfo> labelInfo) {
        this.labelInfo = labelInfo;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LabelInfos{");
        sb.append("labelInfo=").append(labelInfo);
        sb.append(", labelName='").append(labelName).append('\'');
        sb.append(", labelValue='").append(labelValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}