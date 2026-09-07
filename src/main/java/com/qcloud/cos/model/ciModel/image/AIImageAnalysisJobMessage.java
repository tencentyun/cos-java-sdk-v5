package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 通用图片分析（任务式）请求 Message 容器，包含 Content 内容容器。
 */
@XStreamAlias("Message")
public class AIImageAnalysisJobMessage {

    /**
     * 内容容器，由若干 Part 组成
     */
    @XStreamAlias("Content")
    private AIImageAnalysisJobContent content;

    public AIImageAnalysisJobContent getContent() {
        if (content == null) {
            content = new AIImageAnalysisJobContent();
        }
        return content;
    }

    public void setContent(AIImageAnalysisJobContent content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AIImageAnalysisJobMessage{");
        sb.append("content=").append(content);
        sb.append('}');
        return sb.toString();
    }
}
