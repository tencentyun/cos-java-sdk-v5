package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.model.ciModel.job.MediaTranscodeObject;

public class AuditingCosOutput {
    private String region;
    private String bucket;
    private String object;
    private MediaTranscodeObject transcode;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
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
        final StringBuilder sb = new StringBuilder("AuditingCosOutput{");
        sb.append("region='").append(region).append('\'');
        sb.append(", bucket='").append(bucket).append('\'');
        sb.append(", object='").append(object).append('\'');
        sb.append(", transcode=").append(transcode);
        sb.append('}');
        return sb.toString();
    }
}
