package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class TimeInfo {

    @XStreamAlias("Index")
    private String index;
    @XStreamAlias("PartBegin")
    private String partBegin;
    @XStreamAlias("PartEnd")
    private String partEnd;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPartBegin() {
        return partBegin;
    }

    public void setPartBegin(String partBegin) {
        this.partBegin = partBegin;
    }

    public String getPartEnd() {
        return partEnd;
    }

    public void setPartEnd(String partEnd) {
        this.partEnd = partEnd;
    }
}
