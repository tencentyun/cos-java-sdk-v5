package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 大模型图片分析请求实体
 *
 * <p>基于大模型能力，提供通用图片分析功能。</p>
 * <p>当前支持 ImageLabels（标签模式）：返回图片整体描述和标签信息（品牌、类别、Logo 等）</p>
 *
 * <p>请求方式：GET ?ci-process=AIImageAnalysis</p>
 */
public class AIImageAnalysisRequest extends CosServiceRequest {

    /**
     * 操作的 bucket 名称
     */
    @XStreamOmitField
    private String bucketName;

    /**
     * COS 存储的图片对象文件名（如 car.jpg），与 detectUrl 二选一传入。
     * 若同时传入 objectKey 和 detectUrl，优先处理 detectUrl 对应的图片。
     */
    @XStreamOmitField
    private String objectKey;

    /**
     * 公网可访问的图片 URL，与 objectKey 二选一传入，输入值需进行 UrlEncode。
     * 若同时传入 objectKey 和 detectUrl，优先处理 detectUrl 对应的图片。
     */
    @XStreamOmitField
    private String detectUrl;

    /**
     * 分析类型，可选值：
     * <ul>
     *   <li>ImageLabels：默认返回图片的整体描述及图片标签，可与 labelScenes 联合使用</li>
     *   <li>Custom：自定义分析场景，返回 Base64 编码的自定义输出内容</li>
     * </ul>
     */
    @XStreamOmitField
    private String type;

    /**
     * 图片标签的预设场景，仅在 type 为 ImageLabels 时有效，可选值：
     * <ul>
     *   <li>Ecommerce：电商场景，返回商品品牌、类别等标签</li>
     *   <li>General（默认值）：通用场景，返回一级、二级、三级分类标签</li>
     * </ul>
     */
    @XStreamOmitField
    private String labelScenes;

    /**
     * 定制化模板 ID，用于自定义分析场景。
     * 对于客户定制化场景，需传入与后端商定的模板 ID，后端通过定制模板提供服务。
     */
    @XStreamOmitField
    private String templateId;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getDetectUrl() {
        return detectUrl;
    }

    public void setDetectUrl(String detectUrl) {
        this.detectUrl = detectUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabelScenes() {
        return labelScenes;
    }

    public void setLabelScenes(String labelScenes) {
        this.labelScenes = labelScenes;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AIImageAnalysisRequest{");
        sb.append("bucketName='").append(bucketName).append('\'');
        sb.append(", objectKey='").append(objectKey).append('\'');
        sb.append(", detectUrl='").append(detectUrl).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", labelScenes='").append(labelScenes).append('\'');
        sb.append(", templateId='").append(templateId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
