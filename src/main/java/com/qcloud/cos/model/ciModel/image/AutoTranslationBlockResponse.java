package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.model.CosServiceResult;

public class AutoTranslationBlockResponse extends CosServiceResult {
    /**
     * 翻译结果
     */
    private String translationResult;

    public String getTranslationResult() {
        return translationResult;
    }

    public void setTranslationResult(String translationResult) {
        this.translationResult = translationResult;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AutoTranslationBlockResponse{");
        sb.append("translationResult='").append(translationResult).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
