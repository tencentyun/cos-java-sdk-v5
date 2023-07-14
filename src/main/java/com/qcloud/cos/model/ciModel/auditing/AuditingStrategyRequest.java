package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Request")
public class AuditingStrategyRequest extends CIServiceRequest {
    @XStreamAlias("Service")
    private String service;
    @XStreamAlias("Name")
    private String name;
    @XStreamAlias("Labels")
    private StrategyLabels labels;
    @XStreamImplicit(itemFieldName = "TextLibs")
    private List<String> textLibs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StrategyLabels getLabels() {
        if (labels == null) {
            labels = new StrategyLabels();
        }
        return labels;
    }

    public void setLabels(StrategyLabels labels) {
        this.labels = labels;
    }

    public List<String> getTextLibs() {
        if (textLibs == null) {
            textLibs = new ArrayList<>();
        }
        return textLibs;
    }

    public void setTextLibs(List<String> textLibs) {
        this.textLibs = textLibs;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
