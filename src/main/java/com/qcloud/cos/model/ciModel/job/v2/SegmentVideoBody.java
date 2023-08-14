package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SegmentVideoBody {

    @XStreamAlias("Mode")
    private String mode;

    @XStreamAlias("SegmentType")
    private String segmentType;

    @XStreamAlias("BackgroundRed")
    private String backgroundRed;

    @XStreamAlias("BackgroundGreen")
    private String backgroundGreen;

    @XStreamAlias("BackgroundBlue")
    private String backgroundBlue;

    @XStreamAlias("BackgroundLogoUrl")
    private String backgroundLogoUrl;

    @XStreamAlias("BinaryThreshold")
    private String binaryThreshold;

    @XStreamAlias("RemoveRed")
    private String removeRed;

    @XStreamAlias("RemoveGreen")
    private String removeGreen;

    @XStreamAlias("RemoveBlue")
    private String removeBlue;


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSegmentType() {
        return segmentType;
    }

    public void setSegmentType(String segmentType) {
        this.segmentType = segmentType;
    }

    public String getBackgroundRed() {
        return backgroundRed;
    }

    public void setBackgroundRed(String backgroundRed) {
        this.backgroundRed = backgroundRed;
    }

    public String getBackgroundGreen() {
        return backgroundGreen;
    }

    public void setBackgroundGreen(String backgroundGreen) {
        this.backgroundGreen = backgroundGreen;
    }

    public String getBackgroundBlue() {
        return backgroundBlue;
    }

    public void setBackgroundBlue(String backgroundBlue) {
        this.backgroundBlue = backgroundBlue;
    }

    public String getBackgroundLogoUrl() {
        return backgroundLogoUrl;
    }

    public void setBackgroundLogoUrl(String backgroundLogoUrl) {
        this.backgroundLogoUrl = backgroundLogoUrl;
    }

    public String getBinaryThreshold() {
        return binaryThreshold;
    }

    public void setBinaryThreshold(String binaryThreshold) {
        this.binaryThreshold = binaryThreshold;
    }

    public String getRemoveRed() {
        return removeRed;
    }

    public void setRemoveRed(String removeRed) {
        this.removeRed = removeRed;
    }

    public String getRemoveGreen() {
        return removeGreen;
    }

    public void setRemoveGreen(String removeGreen) {
        this.removeGreen = removeGreen;
    }

    public String getRemoveBlue() {
        return removeBlue;
    }

    public void setRemoveBlue(String removeBlue) {
        this.removeBlue = removeBlue;
    }
}
