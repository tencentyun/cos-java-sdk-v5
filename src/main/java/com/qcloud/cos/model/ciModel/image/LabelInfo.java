package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 标签信息，包含标签名称和标签值
 *
 * <p>XML 示例：</p>
 * <pre>{@code
 * <LabelInfo>
 *     <LabelName>Brand</LabelName>
 *     <LabelValue>Nike</LabelValue>
 * </LabelInfo>
 * }</pre>
 */
@XStreamAlias("LabelInfo")
public class LabelInfo {

    /**
     * 识别出的标签名称，例如商品类型 Category、品牌 Brand、商标 Logo
     */
    @XStreamAlias("LabelName")
    private String labelName;

    /**
     * 识别出标签名称对应的标签值，例如商品类型对应上衣、裤子等，品牌对应 Nike、李宁等
     */
    @XStreamAlias("LabelValue")
    private String labelValue;

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
        final StringBuffer sb = new StringBuffer("LabelInfo{");
        sb.append("labelName='").append(labelName).append('\'');
        sb.append(", labelValue='").append(labelValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
