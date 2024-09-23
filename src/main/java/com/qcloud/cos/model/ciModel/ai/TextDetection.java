package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class TextDetection {
    @XStreamAlias("DetectedText")
    private String detectedText; // 识别出的文本行内容

    @XStreamAlias("Confidence")
    private Integer confidence; // 置信度 0 ~ 100

    @XStreamImplicit(itemFieldName = "Polygon")
    private List<OcrPolygon> polygon; // 文本行坐标

    @XStreamAlias("ItemPolygon")
    private ItemPolygon itemPolygon; // 旋转纠正后的图像中的像素坐标

    @XStreamImplicit(itemFieldName = "Words")
    private List<OcrWord> words; // 识别出来的单字信息

    @XStreamImplicit(itemFieldName = "WordPolygon")
    private List<WordPolygon> wordPolygon; // 字的坐标数组

    public String getDetectedText() {
        return detectedText;
    }

    public void setDetectedText(String detectedText) {
        this.detectedText = detectedText;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public List<OcrPolygon> getPolygon() {
        return polygon;
    }

    public void setPolygon(List<OcrPolygon> polygon) {
        this.polygon = polygon;
    }

    public ItemPolygon getItemPolygon() {
        return itemPolygon;
    }

    public void setItemPolygon(ItemPolygon itemPolygon) {
        this.itemPolygon = itemPolygon;
    }



    public List<WordPolygon> getWordPolygon() {
        return wordPolygon;
    }

    public void setWordPolygon(List<WordPolygon> wordPolygon) {
        this.wordPolygon = wordPolygon;
    }

    public List<OcrWord> getWords() {
        return words;
    }

    public void setWords(List<OcrWord> words) {
        this.words = words;
    }
}
