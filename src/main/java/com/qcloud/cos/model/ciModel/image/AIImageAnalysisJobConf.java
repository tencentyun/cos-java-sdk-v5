package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 通用图片分析（任务式）请求 Conf 配置容器。
 *
 * <p>包含分析类型、模板、模型等配置项。</p>
 */
@XStreamAlias("Conf")
public class AIImageAnalysisJobConf {

    /**
     * 分析类型，可选值：
     * <ul>
     *   <li>{@code Description}（默认）：将所有图片一次性发送给大模型进行联合分析，
     *       返回一组统一的描述和整体的标签结果，本期仅允许与预设的 templateName 联合使用</li>
     *   <li>{@code Custom}：将所有图片与用户通过 Part 传入的文本一起发送给大模型，
     *       输出对多张图片的联合理解结果，本期允许与客户自定义的 templateName 联合使用</li>
     * </ul>
     */
    @XStreamAlias("Type")
    private String type;

    /**
     * 图片分析模板，目前支持两种预设模板：
     * <ul>
     *   <li>{@code general}（默认）：开放性标签模板，返回一级、二级、三级分类标签</li>
     *   <li>{@code ecommerce}：电商标签模板，返回商品品牌、类别、物品三个层级的标签</li>
     * </ul>
     */
    @XStreamAlias("TemplateName")
    private String templateName;

    /**
     * 模型参数，用户可自行选择使用哪个模型。默认值：{@code qwen3.5-4b}。
     * 本期仅支持一个模型，后续可扩展。
     */
    @XStreamAlias("AiModel")
    private String aiModel;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getAiModel() {
        return aiModel;
    }

    public void setAiModel(String aiModel) {
        this.aiModel = aiModel;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AIImageAnalysisJobConf{");
        sb.append("type='").append(type).append('\'');
        sb.append(", templateName='").append(templateName).append('\'');
        sb.append(", aiModel='").append(aiModel).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
