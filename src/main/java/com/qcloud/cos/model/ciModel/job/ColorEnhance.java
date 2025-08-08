package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ColorEnhance {
    @XStreamAlias("Contrast")
    private String contrast;
    @XStreamAlias("Correction")
    private String correction;
    @XStreamAlias("Saturation")
    private String saturation;

    public String getContrast() {
        return contrast;
    }

    public void setContrast(String contrast) {
        this.contrast = contrast;
    }

    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public String getSaturation() {
        return saturation;
    }

    public void setSaturation(String saturation) {
        this.saturation = saturation;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ColorEnhance{");
        sb.append("contrast='").append(contrast).append('\'');
        sb.append(", correction='").append(correction).append('\'');
        sb.append(", saturation='").append(saturation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
