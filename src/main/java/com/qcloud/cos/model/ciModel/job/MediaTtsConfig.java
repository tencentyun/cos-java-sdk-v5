package com.qcloud.cos.model.ciModel.job;


/**
 * 媒体处理 任务时间参数实体 https://cloud.tencent.com/document/product/460/48234
 */
public class MediaTtsConfig {
    /**
     * 输入类型，Url/Text
     */
    private String inputType;
    /**
     * 1. 当 InputType 为 Url 时， 必须是合法的 COS 地址，文件必须是utf-8编码
     * 且大小不超过10M。如果模板中指定的合成方式为同步处理，则文件内容不超过300个utf-8字符；
     * 如果模板中指定的合成方式为异步处理，则文件内容不超过10000个utf-8字符。
     * 2. 当 InputType为Text 时, 输入必须是utf-8字符, 且不超过300个字符。
     */
    private String input;


    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaTtsConfig{");
        sb.append("inputType='").append(inputType).append('\'');
        sb.append(", input='").append(input).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
