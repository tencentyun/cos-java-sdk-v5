package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * OCR 命中详情信息，包含 OCR 识别文本和关键词命中信息列表。
 * 用于图片/视频审核结果中返回天御审核引擎的 OCR 命中详情。
 */
public class OcrHitInfos {
    @XStreamAlias("Text")
    private String text;

    @XStreamImplicit(itemFieldName = "HitInfos")
    private List<HitInfo> hitInfos;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<HitInfo> getHitInfos() {
        return hitInfos;
    }

    public void setHitInfos(List<HitInfo> hitInfos) {
        this.hitInfos = hitInfos;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OcrHitInfos{");
        sb.append("text='").append(text).append('\'');
        sb.append(", hitInfos=").append(hitInfos);
        sb.append('}');
        return sb.toString();
    }
}
