package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.model.ciModel.job.MediaTranscodeObject;

public class AuditingLiveOutput {
    private String url;
    private MediaTranscodeObject transcode;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public MediaTranscodeObject getTranscode() {
        if (transcode == null) {
            transcode = new MediaTranscodeObject();
        }
        return transcode;
    }

    public void setTranscode(MediaTranscodeObject transcode) {
        this.transcode = transcode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuditingLiveOutput{");
        sb.append("url='").append(url).append('\'');
        sb.append(", transcode=").append(transcode);
        sb.append('}');
        return sb.toString();
    }
}
