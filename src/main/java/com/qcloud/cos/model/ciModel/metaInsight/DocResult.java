package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * 文档检索结果项
 */
public class DocResult {

    /**
     * 资源标识字段，命中文档的 COS 路径。
     * <p>
     * 字段名沿用项目 {@link ImageResult#getURI()} 的命名约定（{@code uRI} + {@code @JsonProperty("URI")}），
     * 保持类间风格一致。
     * </p>
     */
    @JsonProperty("URI")
    private String uRI;

    /**
     * 命中文本所在的页码（仅 PDF/Office 等多页文档有意义）。
     */
    private Integer textPage;

    /**
     * 相关度匹配得分。
     */
    private Integer score;

    /**
     * 命中文本片段的内容。
     */
    private String text;

    /**
     * 文档对应的预览图片下载链接，键为图片序号（如 {@code Image_0}、{@code Image_1}），值为 URL。
     * <p>
     * 后端返回的键是动态的（每个文档命中的图片数量不固定），因此声明为 {@link Map} 而非固定字段。
     * </p>
     */
    private Map<String, String> imageUrls;

    public String getURI() { return uRI; }

    public void setURI(String uRI) { this.uRI = uRI; }

    public Integer getTextPage() { return textPage; }

    public void setTextPage(Integer textPage) { this.textPage = textPage; }

    public Integer getScore() { return score; }

    public void setScore(Integer score) { this.score = score; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Map<String, String> getImageUrls() { return imageUrls; }

    public void setImageUrls(Map<String, String> imageUrls) { this.imageUrls = imageUrls; }

}
