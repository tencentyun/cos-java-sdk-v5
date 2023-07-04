package com.qcloud.cos.model.ciModel.job;

import java.util.ArrayList;
import java.util.List;

public class VqaPlusResult {
    private String noAudio;
    private String noVideo;
    private List<DetailedResult> detailedResults;

    public String getNoAudio() {
        return noAudio;
    }

    public void setNoAudio(String noAudio) {
        this.noAudio = noAudio;
    }

    public String getNoVideo() {
        return noVideo;
    }

    public void setNoVideo(String noVideo) {
        this.noVideo = noVideo;
    }

    public List<DetailedResult> getDetailedResults() {
        if (detailedResults == null) {
            detailedResults = new ArrayList<>();
        }
        return detailedResults;
    }

    public void setDetailedResults(List<DetailedResult> detailedResults) {
        this.detailedResults = detailedResults;
    }
}
