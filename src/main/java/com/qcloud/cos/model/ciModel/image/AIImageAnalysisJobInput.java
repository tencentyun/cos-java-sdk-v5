package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 通用图片分析（任务式）请求 Input 容器，包含 Message 消息容器。
 *
 * <p>本期 Message 固定为单条。</p>
 */
@XStreamAlias("Input")
public class AIImageAnalysisJobInput {

    /**
     * 消息容器，本期固定为单条
     */
    @XStreamAlias("Message")
    private AIImageAnalysisJobMessage message;

    public AIImageAnalysisJobMessage getMessage() {
        if (message == null) {
            message = new AIImageAnalysisJobMessage();
        }
        return message;
    }

    public void setMessage(AIImageAnalysisJobMessage message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AIImageAnalysisJobInput{");
        sb.append("message=").append(message);
        sb.append('}');
        return sb.toString();
    }
}
