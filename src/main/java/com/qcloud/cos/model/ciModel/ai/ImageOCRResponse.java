package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.model.CosServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("Response")
public class ImageOCRResponse extends CosServiceResult {

    @XStreamImplicit(itemFieldName = "TextDetections")
    private List<TextDetection> textDetections; // 检测到的文本信息

    @XStreamAlias("Language")
    private String language; // 检测到的语言类型

    @XStreamAlias("Angel")
    private Float angel; // 图片旋转角度

    @XStreamAlias("PdfPageSize")
    private Integer pdfPageSize; // PDF的总页数

    public List<TextDetection> getTextDetections() {
        return textDetections;
    }

    public void setTextDetections(List<TextDetection> textDetections) {
        this.textDetections = textDetections;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Float getAngel() {
        return angel;
    }

    public void setAngel(Float angel) {
        this.angel = angel;
    }

    public Integer getPdfPageSize() {
        return pdfPageSize;
    }

    public void setPdfPageSize(Integer pdfPageSize) {
        this.pdfPageSize = pdfPageSize;
    }
}
