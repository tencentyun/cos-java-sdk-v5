package com.qcloud.cos.model.ciModel.job;

import java.util.ArrayList;
import java.util.List;

public class QualityEstimateItem {
    private String confidence;
    private String startTimeOffset;
    private String endTimeOffset;
    private List<String> areaCoordSet;

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getStartTimeOffset() {
        return startTimeOffset;
    }

    public void setStartTimeOffset(String startTimeOffset) {
        this.startTimeOffset = startTimeOffset;
    }

    public String getEndTimeOffset() {
        return endTimeOffset;
    }

    public void setEndTimeOffset(String endTimeOffset) {
        this.endTimeOffset = endTimeOffset;
    }

    public List<String> getAreaCoordSet() {
        if (areaCoordSet == null) {
            areaCoordSet = new ArrayList<>();
        }
        return areaCoordSet;
    }

    public void setAreaCoordSet(List<String> areaCoordSet) {
        this.areaCoordSet = areaCoordSet;
    }


}
