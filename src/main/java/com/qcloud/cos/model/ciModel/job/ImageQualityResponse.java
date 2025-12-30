package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("Response")
public class ImageQualityResponse extends CIServiceRequest implements Serializable {

    @XStreamAlias("LongImage")
    private Boolean longImage;

    @XStreamAlias("BlackAndWhite")
    private Boolean blackAndWhite;

    @XStreamAlias("SmallImage")
    private Boolean smallImage;

    @XStreamAlias("BigImage")
    private Boolean bigImage;

    @XStreamAlias("PureImage")
    private Boolean pureImage;

    @XStreamAlias("ClarityScore")
    private Integer clarityScore;

    @XStreamAlias("AestheticScore")
    private Integer aestheticScore;

    @XStreamAlias("LowQualityScore")
    private Integer lowQualityScore;

    @XStreamAlias("RequestId")
    private String requestId;

    public Boolean getLongImage() {
        return longImage;
    }

    public void setLongImage(Boolean longImage) {
        this.longImage = longImage;
    }

    public Boolean getBlackAndWhite() {
        return blackAndWhite;
    }

    public void setBlackAndWhite(Boolean blackAndWhite) {
        this.blackAndWhite = blackAndWhite;
    }

    public Boolean getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(Boolean smallImage) {
        this.smallImage = smallImage;
    }

    public Boolean getBigImage() {
        return bigImage;
    }

    public void setBigImage(Boolean bigImage) {
        this.bigImage = bigImage;
    }

    public Boolean getPureImage() {
        return pureImage;
    }

    public void setPureImage(Boolean pureImage) {
        this.pureImage = pureImage;
    }

    public Integer getClarityScore() {
        return clarityScore;
    }

    public void setClarityScore(Integer clarityScore) {
        this.clarityScore = clarityScore;
    }

    public Integer getAestheticScore() {
        return aestheticScore;
    }

    public void setAestheticScore(Integer aestheticScore) {
        this.aestheticScore = aestheticScore;
    }

    public Integer getLowQualityScore() {
        return lowQualityScore;
    }

    public void setLowQualityScore(Integer lowQualityScore) {
        this.lowQualityScore = lowQualityScore;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "ImageQualityResponse{" +
                "longImage=" + longImage +
                ", blackAndWhite=" + blackAndWhite +
                ", smallImage=" + smallImage +
                ", bigImage=" + bigImage +
                ", pureImage=" + pureImage +
                ", clarityScore=" + clarityScore +
                ", aestheticScore=" + aestheticScore +
                ", lowQualityScore=" + lowQualityScore +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}