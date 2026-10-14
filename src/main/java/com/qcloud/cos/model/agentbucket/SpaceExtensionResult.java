package com.qcloud.cos.model.agentbucket;

import java.io.Serializable;

import com.qcloud.cos.model.CosServiceResult;

/**
 * Get Space Extension 的返回结果。字段缺失时对应 getter 返回 null。
 *
 * @since 5.6.283
 */
public class SpaceExtensionResult extends CosServiceResult implements Serializable {
    /** 是否为公有读。公有读空间通过智能媒资托管接口访问时，部分读接口无需访问令牌 */
    private Boolean isPublicRead;
    /** 是否检测敏感内容 */
    private Boolean recognizeSensitiveContent;
    /** 空间标签 */
    private String spaceTag;

    public Boolean getIsPublicRead() {
        return isPublicRead;
    }

    public void setIsPublicRead(Boolean isPublicRead) {
        this.isPublicRead = isPublicRead;
    }

    public Boolean getRecognizeSensitiveContent() {
        return recognizeSensitiveContent;
    }

    public void setRecognizeSensitiveContent(Boolean recognizeSensitiveContent) {
        this.recognizeSensitiveContent = recognizeSensitiveContent;
    }

    public String getSpaceTag() {
        return spaceTag;
    }

    public void setSpaceTag(String spaceTag) {
        this.spaceTag = spaceTag;
    }

    @Override
    public String toString() {
        return "SpaceExtensionResult{isPublicRead=" + isPublicRead
                + ", recognizeSensitiveContent=" + recognizeSensitiveContent
                + ", spaceTag='" + spaceTag + "', requestId='" + getRequestId() + "'}";
    }
}
