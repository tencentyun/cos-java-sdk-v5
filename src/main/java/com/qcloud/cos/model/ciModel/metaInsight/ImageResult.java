package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;
public class ImageResult {

    /**
     *资源标识字段，表示需要建立索引的文件地址。
     */
    @JsonProperty("URI")
    private String uRI;

    /**
     *相关图片匹配得分。
     */
    @JsonProperty("Score")
    private Integer score;

    public String getURI() { return uRI; }

    public void setURI(String uRI) { this.uRI = uRI; }

    public Integer getScore() { return score; }

    public void setScore(Integer score) { this.score = score; }



}
