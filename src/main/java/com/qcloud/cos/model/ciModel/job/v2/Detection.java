package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class Detection {

    @XStreamAlias("VideoId")
    private String videoId;
    @XStreamAlias("Similar")
    private Integer similar;
    @XStreamAlias("SimilarDuration")
    private Integer similarDuration;
    @XStreamAlias("Duration")
    private Integer duration;
    @XStreamImplicit(itemFieldName = "MatchDetail")
    private List<MatchDetail> matchDetails;
    @XStreamImplicit(itemFieldName = "Audio")
    private List<DetectionAudio> Audios;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Integer getSimilar() {
        return similar;
    }

    public void setSimilar(Integer similar) {
        this.similar = similar;
    }

    public Integer getSimilarDuration() {
        return similarDuration;
    }

    public void setSimilarDuration(Integer similarDuration) {
        this.similarDuration = similarDuration;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<MatchDetail> getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(List<MatchDetail> matchDetails) {
        this.matchDetails = matchDetails;
    }

    public List<DetectionAudio> getAudios() {
        return Audios;
    }

    public void setAudios(List<DetectionAudio> audios) {
        Audios = audios;
    }
}
