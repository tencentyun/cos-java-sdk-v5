package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 通用图片分析（任务式）Custom 模式的分析结果容器。
 *
 * <p>包含大模型输出的自定义分析结果。</p>
 *
 * <p>XML 示例：</p>
 * <pre>{@code
 * <CustomResult>
 *     <CustomOutput>前两张图片的主要差异在于……</CustomOutput>
 * </CustomResult>
 * }</pre>
 */
@XStreamAlias("CustomResult")
public class JobCustomResult {

    /**
     * 大模型输出结果，明文 UTF-8 字符串。
     * XML 中的特殊字符已进行 XML 实体转义，客户端无需进行 Base64 解码。
     */
    @XStreamAlias("CustomOutput")
    private String customOutput;

    public String getCustomOutput() {
        return customOutput;
    }

    public void setCustomOutput(String customOutput) {
        this.customOutput = customOutput;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobCustomResult{");
        sb.append("customOutput='").append(customOutput).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
