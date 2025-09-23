
package com.qcloud.cos.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AIGC元数据规则构建器
 * 提供链式调用方式构建imageMogr2/AIGCMetadata规则字符串
 */
public class AIGCMetadataRuleBuilder {
    private final StringBuilder ruleBuilder;
    private boolean hasOptionalParams = false;

    private AIGCMetadataRuleBuilder(String label) {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("Label是必填参数，不能为空");
        }
        String encodedLabel = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(label.getBytes(StandardCharsets.UTF_8));
        this.ruleBuilder = new StringBuilder("imageMogr2/AIGCMetadata/Label/").append(encodedLabel);
    }

    /**
     * 创建构建器实例
     * @param label 必填的标签参数
     * @return 构建器实例
     */
    public static AIGCMetadataRuleBuilder create(String label) {
        return new AIGCMetadataRuleBuilder(label);
    }

    /**
     * 添加内容生产者（可选参数）
     * @param contentProducer 内容生产者
     * @return 构建器实例
     */
    public AIGCMetadataRuleBuilder contentProducer(String contentProducer) {
        if (contentProducer != null && !contentProducer.trim().isEmpty()) {
            String encoded = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(contentProducer.getBytes(StandardCharsets.UTF_8));
            ruleBuilder.append("/ContentProducer/").append(encoded);
            hasOptionalParams = true;
        }
        return this;
    }

    /**
     * 添加生产ID（可选参数）
     * @param produceId 生产ID
     * @return 构建器实例
     */
    public AIGCMetadataRuleBuilder produceId(String produceId) {
        if (produceId != null && !produceId.trim().isEmpty()) {
            String encoded = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(produceId.getBytes(StandardCharsets.UTF_8));
            ruleBuilder.append("/ProduceID/").append(encoded);
            hasOptionalParams = true;
        }
        return this;
    }

    public AIGCMetadataRuleBuilder reservedCode1(String reservedCode1) {
        if (reservedCode1 != null && !reservedCode1.trim().isEmpty()) {
            String encoded = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(reservedCode1.getBytes(StandardCharsets.UTF_8));
            ruleBuilder.append("/ReservedCode1/").append(encoded);
        }
        return this;
    }

    public AIGCMetadataRuleBuilder reservedCode2(String reservedCode2) {
        if (reservedCode2 != null && !reservedCode2.trim().isEmpty()) {
            String encoded = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(reservedCode2.getBytes(StandardCharsets.UTF_8));
            ruleBuilder.append("/ReservedCode2/").append(encoded);
        }
        return this;
    }

    /**
     * 添加传播ID
     * @param propagateId 传播ID
     * @return 构建器实例
     */
    public AIGCMetadataRuleBuilder propagateId(String propagateId) {
        if (propagateId != null && !propagateId.trim().isEmpty()) {
            String encoded = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(propagateId.getBytes(StandardCharsets.UTF_8));
            ruleBuilder.append("/PropagateID/").append(encoded);
        }
        return this;
    }

    /**
     * 添加内容传播者
     * @param contentPropagator 内容传播者
     * @return 构建器实例
     */
    public AIGCMetadataRuleBuilder contentPropagator(String contentPropagator) {
        if (contentPropagator != null && !contentPropagator.trim().isEmpty()) {
            String encoded = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(contentPropagator.getBytes(StandardCharsets.UTF_8));
            ruleBuilder.append("/ContentPropagator/").append(encoded);
        }
        return this;
    }

    /**
     * 构建最终的规则字符串
     * @return 规则字符串
     */
    public String build() {
        return ruleBuilder.toString();
    }

    /**
     * 检查是否包含可选参数
     * @return 是否包含可选参数
     */
    public boolean hasOptionalParams() {
        return hasOptionalParams;
    }
}