package com.qcloud.cos.model.ciModel.auditing;


import com.qcloud.cos.internal.CIServiceRequest;

/**
 * 文本审核结果反馈请求 参数详情参考：https://cloud.tencent.com/document/product/460/75497
 */
public class ReportBadCaseRequest extends CIServiceRequest {
    /**
     * 需要反馈的数据类型，错误文本样本取值为1。
     */
    private String contentType;

    /**
     * 文本类型的样本，需要填写 base64 的文本内容，ContentType 为1时必填。
     */
    private String text;

    /**
     * 数据万象审核判定的审核结果标签，例如 Porn。
     */
    private String label;

    /**
     * 您自己期望的正确审核结果的标签，例如期望是正常，则填 Normal。
     */
    private SuggestedLabel suggestedLabel;

    /**
     * 该数据样本对应的审核任务 ID，有助于定位审核记录。
     */
    private String jobId;

    /**
     * 该数据样本之前审核的时间，有助于定位审核记录。 格式为 2021-08-07T12:12:12+08:00
     */
    private String moderationTime;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SuggestedLabel getSuggestedLabel() {
        return suggestedLabel;
    }

    public void setSuggestedLabel(SuggestedLabel suggestedLabel) {
        this.suggestedLabel = suggestedLabel;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getModerationTime() {
        return moderationTime;
    }

    public void setModerationTime(String moderationTime) {
        this.moderationTime = moderationTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ReportBadCaseRequest{");
        sb.append("contentType='").append(contentType).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", suggestedLabel='").append(suggestedLabel).append('\'');
        sb.append(", jobId='").append(jobId).append('\'');
        sb.append(", moderationTime='").append(moderationTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
