package com.qcloud.cos.model.ciModel.image;

/**
 * 标签信息
 */
public class Label {
    /**
     * 算法对于Name的置信度，0-100之间，值越高，表示对于Name越确定
     */
    private String confidence;

    /**
     * 图片标签名
     */
    private String name;
    /**
     * 一级标签
     */
    private String firstCategory;
    /**
     * 二级标签
     */
    private String secondCategory ;

    /**
     * 请求参数携带scenes时，标签返回的名称
     */
    private String LabelName;

    public String getLabelName() {
        return LabelName;
    }

    public void setLabelName(String labelName) {
        LabelName = labelName;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(String firstCategory) {
        this.firstCategory = firstCategory;
    }

    public String getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(String secondCategory) {
        this.secondCategory = secondCategory;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Lobel{");
        sb.append("confidence='").append(confidence).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
