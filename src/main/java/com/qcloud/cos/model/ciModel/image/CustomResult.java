package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 自定义分析结果容器
 *
 * <p>当 Type 为 Custom 时返回此结果，CustomOutput 为 Base64 编码的自定义输出内容。</p>
 *
 * <p>XML 示例：</p>
 * <pre>{@code
 * <CustomResult>
 *     <CustomOutput>YGBganNvbgp7Li4ufQpgYGA=</CustomOutput>
 * </CustomResult>
 * }</pre>
 */
@XStreamAlias("CustomResult")
public class CustomResult {

    /**
     * 自定义输出内容，Base64 编码的字符串
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
        final StringBuffer sb = new StringBuffer("CustomResult{");
        sb.append("customOutput='").append(customOutput).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
