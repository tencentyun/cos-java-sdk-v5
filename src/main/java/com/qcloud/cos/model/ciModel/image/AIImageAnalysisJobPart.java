package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 通用图片分析（任务式）请求中的 Part 节点，即多模态内容片段。
 *
 * <p>可重复多个，按出现顺序组装为大模型输入。Part 总数最多 20 个，其中图片类型 Part 最多 10 个。</p>
 *
 * <p>字段有效性说明：</p>
 * <ul>
 *   <li>Type=Image：需通过 objectKey 或 url 指定图片来源，二选一，同时传入时优先使用 objectKey</li>
 *   <li>Type=Text：通过 text 字段传入文本内容，长度限制 1024 字符</li>
 * </ul>
 *
 * <p>XML 示例（图片 Part）：</p>
 * <pre>{@code
 * <Part>
 *     <Type>Image</Type>
 *     <ObjectKey>sample/image1.jpg</ObjectKey>
 * </Part>
 * }</pre>
 *
 * <p>XML 示例（文本 Part）：</p>
 * <pre>{@code
 * <Part>
 *     <Type>Text</Type>
 *     <Text>请比较前两张图片的主要区别</Text>
 * </Part>
 * }</pre>
 */
@XStreamAlias("Part")
public class AIImageAnalysisJobPart {

    /**
     * 内容类型，取值：
     * <ul>
     *   <li>{@code Image}：图片类型，需通过 objectKey 或 url 指定图片来源</li>
     *   <li>{@code Text}：文本类型，通过 text 字段传入文本内容</li>
     * </ul>
     */
    @XStreamAlias("Type")
    private String type;

    /**
     * 存储在 COS 存储桶中的图片对象键，例如 test/image.jpg。仅在 Type=Image 时有效。
     * 与 url 二选一，同时传入时优先处理 objectKey。
     */
    @XStreamAlias("ObjectKey")
    private String objectKey;

    /**
     * 公网可访问的图片链接地址。仅在 Type=Image 时有效。
     * 与 objectKey 二选一。通过 url 分析会产生图片所在源站的外网流量。
     */
    @XStreamAlias("Url")
    private String url;

    /**
     * 文本内容，仅在 Type=Text 时有效。长度限制 1024 字符。
     * 可用于传入时间戳、描述信息、分析指令（Custom 模式下作为 Prompt）等。
     */
    @XStreamAlias("Text")
    private String text;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AIImageAnalysisJobPart{");
        sb.append("type='").append(type).append('\'');
        sb.append(", objectKey='").append(objectKey).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
