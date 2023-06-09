package com.qcloud.cos.model.ciModel.auditing;


import java.util.ArrayList;
import java.util.List;

public class Mask {
    private List<AuditingImage> images;
    private List<AuditingAudio> audios;
    private AuditingCosOutput cosOutput;
    private AuditingLiveOutput liveOutput;

    public List<AuditingImage> getImages() {
        if (images == null) {
            images = new ArrayList<>();
        }
        return images;
    }

    public void setImages(List<AuditingImage> images) {
        this.images = images;
    }

    public List<AuditingAudio> getAudios() {
        if (audios == null) {
            audios = new ArrayList<>();
        }
        return audios;
    }

    public void setAudios(List<AuditingAudio> audios) {
        this.audios = audios;
    }

    public AuditingCosOutput getCosOutput() {
        if (cosOutput == null) {
            cosOutput = new AuditingCosOutput();
        }
        return cosOutput;
    }

    public void setCosOutput(AuditingCosOutput cosOutput) {
        this.cosOutput = cosOutput;
    }

    public AuditingLiveOutput getLiveOutput() {
        if (liveOutput == null) {
            liveOutput = new AuditingLiveOutput();
        }
        return liveOutput;
    }

    public void setLiveOutput(AuditingLiveOutput liveOutput) {
        this.liveOutput = liveOutput;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Mask{");
        sb.append("images=").append(images);
        sb.append(", audios=").append(audios);
        sb.append(", cosOutput=").append(cosOutput);
        sb.append('}');
        return sb.toString();
    }
}
