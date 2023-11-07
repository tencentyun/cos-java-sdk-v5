package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class SplitVideoInfoResult {
    @XStreamImplicit(itemFieldName = "TimeInfo")
    List<TimeInfo> timeInfos;

    public List<TimeInfo> getTimeInfos() {
        return timeInfos;
    }

    public void setTimeInfos(List<TimeInfo> timeInfos) {
        this.timeInfos = timeInfos;
    }
}
