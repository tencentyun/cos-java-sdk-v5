package com.qcloud.cos.model.agentbucket;

/**
 * AgentBucket 复制/移动接口的冲突解决策略，对应请求参数 conflict_resolution_strategy。
 * <ul>
 * <li>{@link #ASK}：默认策略，目标已存在时抛出 CosServiceException，错误码为 FileAlreadyExists</li>
 * <li>{@link #RENAME}：目标已存在时自动重命名最后一级目录/文件</li>
 * <li>{@link #OVERWRITE}：仅 Move Object（非递归）支持；冲突目标为目录时抛出 CosServiceException，否则覆盖已有文件</li>
 * </ul>
 *
 * @since 5.6.283
 */
public enum ConflictResolutionStrategy {
    ASK("ask"),
    RENAME("rename"),
    OVERWRITE("overwrite");

    private final String value;

    ConflictResolutionStrategy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
