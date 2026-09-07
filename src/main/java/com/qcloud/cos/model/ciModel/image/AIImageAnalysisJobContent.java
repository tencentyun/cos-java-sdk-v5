package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用图片分析（任务式）请求 Content 容器，由若干 Part 组成。
 *
 * <p>Part 总数最多 20 个，其中图片类型 Part 最多 10 个。
 * 图片类型和文本类型的 Part 可以任意顺序混合排列，后端按出现顺序组装为大模型输入。</p>
 *
 * <p>请求中必须包含至少一个图片类型的 Part（Type=Image），否则会返回 NoImageProvided 错误。</p>
 */
@XStreamAlias("Content")
public class AIImageAnalysisJobContent {

    /**
     * 多模态内容片段列表，可包含图片类型（Type=Image）和文本类型（Type=Text）的 Part。
     */
    @XStreamImplicit(itemFieldName = "Part")
    private List<AIImageAnalysisJobPart> parts = new ArrayList<>();

    public List<AIImageAnalysisJobPart> getParts() {
        if (parts == null) {
            parts = new ArrayList<>();
        }
        return parts;
    }

    public void setParts(List<AIImageAnalysisJobPart> parts) {
        this.parts = parts;
    }

    /**
     * 便捷方法：追加一个图片 Part（通过 COS ObjectKey 指定图片来源）
     *
     * @param objectKey COS 存储桶中的图片对象键，例如 test/image.jpg
     * @return 当前 Content 对象，便于链式调用
     */
    public AIImageAnalysisJobContent addImagePartByObjectKey(String objectKey) {
        AIImageAnalysisJobPart part = new AIImageAnalysisJobPart();
        part.setType("Image");
        part.setObjectKey(objectKey);
        getParts().add(part);
        return this;
    }

    /**
     * 便捷方法：追加一个图片 Part（通过公网 URL 指定图片来源）
     *
     * @param url 公网可访问的图片链接地址
     * @return 当前 Content 对象，便于链式调用
     */
    public AIImageAnalysisJobContent addImagePartByUrl(String url) {
        AIImageAnalysisJobPart part = new AIImageAnalysisJobPart();
        part.setType("Image");
        part.setUrl(url);
        getParts().add(part);
        return this;
    }

    /**
     * 便捷方法：追加一个文本 Part（Custom 模式下作为 Prompt；Description 模式下作为上下文信息）
     *
     * @param text 文本内容，长度限制 1024 字符
     * @return 当前 Content 对象，便于链式调用
     */
    public AIImageAnalysisJobContent addTextPart(String text) {
        AIImageAnalysisJobPart part = new AIImageAnalysisJobPart();
        part.setType("Text");
        part.setText(text);
        getParts().add(part);
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AIImageAnalysisJobContent{");
        sb.append("parts=").append(parts);
        sb.append('}');
        return sb.toString();
    }
}
