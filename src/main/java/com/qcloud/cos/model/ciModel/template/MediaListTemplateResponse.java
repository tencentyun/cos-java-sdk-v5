package com.qcloud.cos.model.ciModel.template;

import com.qcloud.cos.model.ciModel.common.MediaCommonResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * @descript 媒体模板响应实体类。 注释详情请参见 https://cloud.tencent.com/document/product/460/46989
 */
@XStreamAlias("Response")
public class MediaListTemplateResponse extends MediaCommonResponse {

    @XStreamImplicit(itemFieldName = "TemplateList")
    private List<MediaTemplateObject> templateList;
    @XStreamAlias("TemplateId")
    private String templateId;

    public List<MediaTemplateObject> getTemplateList() {
        if (templateList == null) {
            templateList = new ArrayList<>();
        }
        return templateList;
    }

    public void setTemplateList(List<MediaTemplateObject> templateList) {
        this.templateList = templateList;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "MediaListTemplateResponse{" +
                "templateList=" + templateList +
                ", templateId=" + templateId +
                '}';
    }
}
