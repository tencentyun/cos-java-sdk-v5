package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;

public class JobParam {
    private String templateId;
    private MediaPicProcessTemplateObject picProcess;
    private PicProcessResult picProcessResult;
    private MediaSegmentObject segment;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public MediaPicProcessTemplateObject getPicProcess() {
        if (picProcess == null) {
            picProcess = new MediaPicProcessTemplateObject();
        }
        return picProcess;
    }

    public void setPicProcess(MediaPicProcessTemplateObject picProcess) {
        this.picProcess = picProcess;
    }

    public PicProcessResult getPicProcessResult() {
        if (picProcessResult == null) {
            picProcessResult = new PicProcessResult();
        }
        return picProcessResult;
    }

    public void setPicProcessResult(PicProcessResult picProcessResult) {
        this.picProcessResult = picProcessResult;
    }

    public MediaSegmentObject getSegment() {
        if (segment == null) {
            segment = new MediaSegmentObject();
        }
        return segment;
    }

    public void setSegment(MediaSegmentObject segment) {
        this.segment = segment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JobParam{");
        sb.append("templateId='").append(templateId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
