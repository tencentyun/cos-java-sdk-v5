package com.qcloud.cos.model.ciModel.workflow;

import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class AttachParam {
    @XStreamAlias("Watermark")
    private MediaWatermark watermark;

    @XStreamAlias("WatermarkTemplateId")
    private String watermarkTemplateId;

    public MediaWatermark getWatermark() {
        if (watermark == null) {
            watermark = new MediaWatermark();
        }
        return watermark;
    }

    public void setWatermark(MediaWatermark watermark) {
        this.watermark = watermark;
    }

    public String getWatermarkTemplateId() {
        return watermarkTemplateId;
    }

    public void setWatermarkTemplateId(String watermarkTemplateId) {
        this.watermarkTemplateId = watermarkTemplateId;
    }

}
