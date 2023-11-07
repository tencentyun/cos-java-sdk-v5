package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

public class FillConcat {
    @XStreamImplicit(itemFieldName = "FillInput")
    private List<FillInput> fillInput;

    @XStreamAlias("Format")
    private String format;

    @XStreamAlias("RefMode")
    private String refMode;

    @XStreamAlias("Reflndex")
    private String reflndex;

    public String getRefMode() {
        return refMode;
    }

    public void setRefMode(String refMode) {
        this.refMode = refMode;
    }

    public String getReflndex() {
        return reflndex;
    }

    public void setReflndex(String reflndex) {
        this.reflndex = reflndex;
    }

    public void setFillInput(List<FillInput> fillInput) {
        this.fillInput = fillInput;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<FillInput> getFillInput() {
        if (fillInput == null) {
            fillInput = new ArrayList<>();
        }
        return fillInput;
    }
}
