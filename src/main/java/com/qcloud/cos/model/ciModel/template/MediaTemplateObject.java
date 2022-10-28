package com.qcloud.cos.model.ciModel.template;

import com.qcloud.cos.model.ciModel.common.MediaCommonResponse;
import com.qcloud.cos.model.ciModel.job.MediaConcatTemplateObject;
import com.qcloud.cos.model.ciModel.job.MediaTransConfigObject;

/**
 * @descript 媒体模板响应实体类。 注释详情请参见 https://cloud.tencent.com/document/product/460/46989
 */
public class MediaTemplateObject extends MediaCommonResponse {

    private String templateId;

    private String name;

    private String tag;

    private String state;

    private String bucketId;
    private String category;

    private MediaTemplateTransTplObject transTpl;

    private MediaSnapshotObject snapshot;

    private MediaWatermark watermark;

    private MediaTransConfigObject transConfig;

    private MediaConcatTemplateObject concatTemplate;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaTemplateObject{");
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
        sb.append('}');
        return sb.toString();
    }
}
