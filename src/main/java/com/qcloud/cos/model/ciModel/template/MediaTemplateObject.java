package com.qcloud.cos.model.ciModel.template;

import com.qcloud.cos.model.ciModel.common.MediaCommonResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @descript 媒体模板响应实体类。 注释详情请参见 https://cloud.tencent.com/document/product/460/46989
 */
@XStreamAlias("Template")
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
        return transTpl;
    }

    public void setTransTpl(MediaTemplateTransTplObject transTpl) {
        this.transTpl = transTpl;
    }

    @Override
    public String toString() {
        return "MediaTemplateObject{" +
                "templateId='" + templateId + '\'' +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", state='" + state + '\'' +
                ", bucketId='" + bucketId + '\'' +
                ", category='" + category + '\'' +
                ", transTpl=" + transTpl +
                '}';
    }
}
