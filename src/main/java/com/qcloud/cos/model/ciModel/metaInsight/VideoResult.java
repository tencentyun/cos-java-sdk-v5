package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 视频检索结果项（视频检索能力新增）。
 */
public class VideoResult {

    /**
     * 匹配视频在对象存储中的统一资源标识符（URI）。
     */
    @JsonProperty("URI")
    private String uRI;

    /**
     * 匹配视频片段的起始时间，单位为秒。
     */
    @JsonProperty("From")
    private Float from;

    /**
     * 匹配视频片段的结束时间，单位为秒。
     */
    @JsonProperty("To")
    private Float to;

    /**
     * 搜索结果的相关度评分，数值越高表示相关性越强。
     */
    @JsonProperty("Score")
    private Integer score;

    public String getURI() { return uRI; }

    public void setURI(String uRI) { this.uRI = uRI; }

    public Float getFrom() { return from; }

    public void setFrom(Float from) { this.from = from; }

    public Float getTo() { return to; }

    public void setTo(Float to) { this.to = to; }

    public Integer getScore() { return score; }

    public void setScore(Integer score) { this.score = score; }

}
