package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.internal.CosServiceRequest;

public class AutoTranslationBlockRequest extends CosServiceRequest {
    /**
     * bucket信息
     */
    private String bucketName;
    /**
     * 待翻译的文本
     */
    private String inputText;
    /**
     * 输入语言，如 "zh"
     */
    private String sourceLang;
    /**
     * 输出语言，如 "en"
     */
    private String targetLang;
    /**
     * 文本所属业务领域，如: "ecommerce", 缺省值为 general
     */
    private String textDomain;
    /**
     * 文本类型，如: "title", 缺省值为 sentence
     */
    private String textStyle;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }

    public String getTextDomain() {
        return textDomain;
    }

    public void setTextDomain(String textDomain) {
        this.textDomain = textDomain;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AutoTranslationBlockRequest{");
        sb.append("bucketName='").append(bucketName).append('\'');
        sb.append(", inputText='").append(inputText).append('\'');
        sb.append(", sourceLang='").append(sourceLang).append('\'');
        sb.append(", targetLang='").append(targetLang).append('\'');
        sb.append(", textDomain='").append(textDomain).append('\'');
        sb.append(", textStyle='").append(textStyle).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
