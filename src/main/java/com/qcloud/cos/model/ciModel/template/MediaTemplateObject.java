package com.qcloud.cos.model.ciModel.template;

import com.qcloud.cos.model.ciModel.common.MediaCommonResponse;
import com.qcloud.cos.model.ciModel.job.MediaConcatTemplateObject;
import com.qcloud.cos.model.ciModel.job.MediaTransConfigObject;
import com.qcloud.cos.model.ciModel.job.TtsTpl;
import com.qcloud.cos.model.ciModel.job.VideoEnhance;
import com.qcloud.cos.model.ciModel.job.VideoTargetRec;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @descript 媒体模板响应实体类。 注释详情请参见 https://cloud.tencent.com/document/product/460/46989
 */
public class MediaTemplateObject extends MediaCommonResponse {

    @XStreamAlias("TemplateId")
    private String templateId;

    @XStreamAlias("Name")
    private String name;

    @XStreamAlias("Tag")
    private String tag;

    @XStreamAlias("State")
    private String state;

    @XStreamAlias("BucketId")
    private String bucketId;

    @XStreamAlias("Category")
    private String category;

    @XStreamAlias("TransTpl")
    private MediaTemplateTransTplObject transTpl;

    @XStreamAlias("Snapshot")
    private MediaSnapshotObject snapshot;

    @XStreamAlias("Watermark")
    private MediaWatermark watermark;

    @XStreamAlias("TransConfig")
    private MediaTransConfigObject transConfig;

    @XStreamAlias("ConcatTemplate")
    private MediaConcatTemplateObject concatTemplate;

    @XStreamAlias("VideoTargetRec")
    private VideoTargetRec videoTargetRec;

    @XStreamAlias("TtsTpl")
    private TtsTpl ttsTpl;

    @XStreamAlias("VideoEnhance")
    private VideoEnhance videoEnhance;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MediaTemplateTransTplObject getTransTpl() {
        if (transTpl == null) {
            transTpl = new MediaTemplateTransTplObject();
        }
        return transTpl;
    }

    public void setTransTpl(MediaTemplateTransTplObject transTpl) {
        this.transTpl = transTpl;
    }

    public MediaSnapshotObject getSnapshot() {
        if (snapshot == null) {
            snapshot = new MediaSnapshotObject();
        }
        return snapshot;
    }

    public void setSnapshot(MediaSnapshotObject snapshot) {
        this.snapshot = snapshot;
    }

    public MediaWatermark getWatermark() {
        if (watermark == null) {
            watermark = new MediaWatermark();
        }
        return watermark;
    }

    public void setWatermark(MediaWatermark watermark) {
        this.watermark = watermark;
    }

    public MediaTransConfigObject getTransConfig() {
        return transConfig;
    }

    public void setTransConfig(MediaTransConfigObject transConfig) {
        this.transConfig = transConfig;
    }

    public MediaConcatTemplateObject getConcatTemplate() {
        if (concatTemplate == null) {
            concatTemplate = new MediaConcatTemplateObject();
        }
        return concatTemplate;
    }

    public void setConcatTemplate(MediaConcatTemplateObject concatTemplate) {
        this.concatTemplate = concatTemplate;
    }

    public VideoTargetRec getVideoTargetRec() {
        if (videoTargetRec == null) {
            videoTargetRec = new VideoTargetRec();
        }
        return videoTargetRec;
    }

    public void setVideoTargetRec(VideoTargetRec videoTargetRec) {
        this.videoTargetRec = videoTargetRec;
    }

    public TtsTpl getTtsTpl() {
        if (ttsTpl == null) {
            ttsTpl = new TtsTpl();
        }
        return ttsTpl;
    }

    public void setTtsTpl(TtsTpl ttsTpl) {
        this.ttsTpl = ttsTpl;
    }

    public VideoEnhance getVideoEnhance() {
        if (videoEnhance == null) {
            videoEnhance = new VideoEnhance();
        }
        return videoEnhance;
    }

    public void setVideoEnhance(VideoEnhance videoEnhance) {
        this.videoEnhance = videoEnhance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MediaTemplateObject{");
        sb.append("templateId='").append(templateId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", tag='").append(tag).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", bucketId='").append(bucketId).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", transTpl=").append(transTpl);
        sb.append(", snapshot=").append(snapshot);
        sb.append(", watermark=").append(watermark);
        sb.append(", transConfig=").append(transConfig);
        sb.append(", concatTemplate=").append(concatTemplate);
        sb.append(", videoTargetRec=").append(videoTargetRec);
        sb.append('}');
        return sb.toString();
    }
}
