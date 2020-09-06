package com.qcloud.cos.model.ciModel.template;

import com.qcloud.cos.model.ciModel.common.MediaCommonResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @descript 媒体模板响应实体类。 注释详情请参见 https://cloud.tencent.com/document/product/460/46989
 */
public class MediaTemplateResponse extends MediaCommonResponse{

    @XStreamAlias("Template")
    private MediaTemplateObject template;
    @XStreamAlias("TemplateID")
    private MediaTemplateObject templateId;

    @Override
    public String toString() {
        return "MediaTemplateResponse{" +
                "template=" + template +
                '}';
    }
}
