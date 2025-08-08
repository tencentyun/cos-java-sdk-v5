package com.qcloud.cos.model.ciModel.job;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 媒体处理 画质增强参数
 */
public class VideoEnhance {

    @XStreamAlias("Transcode")
    private MediaTranscodeObject trascode;
    @XStreamAlias("SuperResolution")
    private SuperResolution superResolution;
    @XStreamAlias("SDRtoHDR")
    private SDRtoHDR sdrToHDR;
    @XStreamAlias("ColorEnhance")
    private ColorEnhance colorEnhance;
    @XStreamAlias("MsSharpen")
    private MsSharpen msSharpen;
    @XStreamAlias("FrameEnhance")
    private FrameEnhance frameEnhance;

    public MediaTranscodeObject getTrascode() {
        if (trascode == null) {
            trascode = new MediaTranscodeObject();
        }
        return trascode;
    }

    public void setTrascode(MediaTranscodeObject trascode) {
        this.trascode = trascode;
    }

    public SuperResolution getSuperResolution() {
        if (superResolution == null) {
            superResolution = new SuperResolution();
        }
        return superResolution;
    }

    public void setSuperResolution(SuperResolution superResolution) {
        this.superResolution = superResolution;
    }

    public SDRtoHDR getSdrToHDR() {
        if (sdrToHDR == null) {
            sdrToHDR = new SDRtoHDR();
        }
        return sdrToHDR;
    }

    public void setSdrToHDR(SDRtoHDR sdrToHDR) {
        this.sdrToHDR = sdrToHDR;
    }

    public ColorEnhance getColorEnhance() {
        if (colorEnhance == null) {
            colorEnhance = new ColorEnhance();
        }
        return colorEnhance;
    }

    public void setColorEnhance(ColorEnhance colorEnhance) {
        this.colorEnhance = colorEnhance;
    }

    public MsSharpen getMsSharpen() {
        if (msSharpen == null) {
            msSharpen = new MsSharpen();
        }
        return msSharpen;
    }

    public void setMsSharpen(MsSharpen msSharpen) {
        this.msSharpen = msSharpen;
    }

    public FrameEnhance getFrameEnhance() {
        if (frameEnhance == null) {
            frameEnhance = new FrameEnhance();
        }
        return frameEnhance;
    }

    public void setFrameEnhance(FrameEnhance frameEnhance) {
        this.frameEnhance = frameEnhance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VideoEnhance{");
        sb.append("trascode=").append(trascode);
        sb.append(", superResolution=").append(superResolution);
        sb.append(", sdrToHDR=").append(sdrToHDR);
        sb.append(", colorEnhance=").append(colorEnhance);
        sb.append(", msSharpen=").append(msSharpen);
        sb.append(", frameEnhance=").append(frameEnhance);
        sb.append('}');
        return sb.toString();
    }
}
