package com.qcloud.cos.model.ciModel.job;

public class JobParam {
    private String templateId;
    private MediaPicProcessTemplateObject picProcess;
    private PicProcessResult picProcessResult;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JobParam{");
        sb.append("templateId='").append(templateId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
