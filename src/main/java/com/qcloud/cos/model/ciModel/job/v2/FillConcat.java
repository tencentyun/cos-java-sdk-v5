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
