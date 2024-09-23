package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.internal.CosServiceRequest;

public class ImageOCRRequest extends CosServiceRequest {
    private String bucketName;
    private String objectKey; // 对象文件名，例如：folder/document.jpg
    private String detectUrl; // 处理的图片链接
    private String type; // ocr 的识别类型
    private String languageType; // 识别语言类型
    private Boolean isPdf = false; // 是否开启 PDF 识别
    private Integer pdfPageNumber = 1; // 需要识别的 PDF 页面的对应页码
    private Boolean isWord = false; // 是否需要返回单字信息
    private Boolean enableWordPolygon = false; // 是否开启单字的四点定位坐标输出

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getDetectUrl() {
        return detectUrl;
    }

    public void setDetectUrl(String detectUrl) {
        this.detectUrl = detectUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public Boolean getPdf() {
        return isPdf;
    }

    public void setPdf(Boolean pdf) {
        isPdf = pdf;
    }

    public Integer getPdfPageNumber() {
        return pdfPageNumber;
    }

    public void setPdfPageNumber(Integer pdfPageNumber) {
        this.pdfPageNumber = pdfPageNumber;
    }

    public Boolean getWord() {
        return isWord;
    }

    public void setWord(Boolean word) {
        isWord = word;
    }

    public Boolean getEnableWordPolygon() {
        return enableWordPolygon;
    }

    public void setEnableWordPolygon(Boolean enableWordPolygon) {
        this.enableWordPolygon = enableWordPolygon;
    }
}
