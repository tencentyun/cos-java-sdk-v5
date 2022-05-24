package com.qcloud.cos.model.ciModel.job;


/**
 * 图片处理模板实体类
 */
public class MediaPicProcessTemplateObject {
    /**
     * 是否返回原图信息
     */
    private String isPicInfo;

    /**
     * 图片处理规则
     */
    private String processRule;

    public String getIsPicInfo() {
        return isPicInfo;
    }

    public void setIsPicInfo(String isPicInfo) {
        this.isPicInfo = isPicInfo;
    }

    public String getProcessRule() {
        return processRule;
    }

    public void setProcessRule(String processRule) {
        this.processRule = processRule;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MediaPicProcessTemplateObject{");
        sb.append("isPicInfo='").append(isPicInfo).append('\'');
        sb.append(", processRule='").append(processRule).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
