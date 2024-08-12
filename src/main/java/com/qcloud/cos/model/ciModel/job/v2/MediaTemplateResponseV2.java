package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.model.CiServiceResult;
import com.qcloud.cos.model.ciModel.template.MediaTemplateObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public class MediaTemplateResponseV2 extends CiServiceResult {

    @XStreamAlias("Template")
    private MediaTemplateObject template;

    public MediaTemplateObject getTemplate() {
        return template;
    }

    public void setTemplate(MediaTemplateObject template) {
        this.template = template;
    }
}
