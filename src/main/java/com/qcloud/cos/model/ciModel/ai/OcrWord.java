package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class OcrWord {
    @XStreamAlias("Confidence")
    private Integer confidence; // 置信度 0 ~ 100

    @XStreamAlias("Character")
    private String character; // 候选字

    @XStreamImplicit(itemFieldName = "WordCoordPoint")
    private List<OcrPolygon> wordCoordPoint; // 单字在原图

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public List<OcrPolygon> getWordCoordPoint() {
        return wordCoordPoint;
    }

    public void setWordCoordPoint(List<OcrPolygon> wordCoordPoint) {
        this.wordCoordPoint = wordCoordPoint;
    }
}
