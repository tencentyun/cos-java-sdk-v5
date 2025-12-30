package com.qcloud.cos.model.ciModel.persistence;

import com.qcloud.cos.model.ciModel.job.AigcMetadata;
import com.qcloud.cos.model.ciModel.recognition.QRcodeInfo;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class CIObject {
    @XStreamAlias("Key")
    private String key;

    @XStreamAlias("Location")
    private String location;

    @XStreamAlias("Format")
    private String format;

    @XStreamAlias("Width")
    private int width;

    @XStreamAlias("Height")
    private int height;

    @XStreamAlias("Size")
    private int size;

    @XStreamAlias("Quality")
    private int quality;

    @XStreamAlias("FrameCount")
    private int frameCount;

    @XStreamAlias("ETag")
    private String etag;

    @XStreamAlias("WatermarkStatus")
    private Integer watermarkStatus;
    @XStreamAlias("CodeStatus")
    private Integer codeStatus;
    @XStreamImplicit(itemFieldName = "QRcodeInfo")
    private List<QRcodeInfo> QRcodeInfoList;

    @XStreamAlias("AIGCMetadata")
    private AigcMetadata aigcMetadata;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Integer getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(Integer codeStatus) {
        this.codeStatus = codeStatus;
    }

    public List<QRcodeInfo> getQRcodeInfoList() {
        return QRcodeInfoList;
    }

    public void setQRcodeInfoList(List<QRcodeInfo> QRcodeInfoList) {
        this.QRcodeInfoList = QRcodeInfoList;
    }

    public Integer getWatermarkStatus() {
        return watermarkStatus;
    }

    public void setWatermarkStatus(Integer watermarkStatus) {
        this.watermarkStatus = watermarkStatus;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }


    public AigcMetadata getAigcMetadata() {
        if (aigcMetadata == null) {
            aigcMetadata = new AigcMetadata();
        }
        return aigcMetadata;
    }

    public void setAigcMetadata(AigcMetadata aigcMetadata) {
        this.aigcMetadata = aigcMetadata;
    }
}
