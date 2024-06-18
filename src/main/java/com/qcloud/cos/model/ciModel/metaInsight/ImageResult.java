package com.qcloud.cos.model.ciModel.metaInsight;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("ImageResult")
public class ImageResult {

    /**
     *资源标识字段，表示需要建立索引的文件地址。
     */
    @XStreamAlias("URI")
    private String uRI;

    /**
     *相关图片匹配得分。
     */
    @XStreamAlias("Score")
    private Integer score;

    public String getURI() { return uRI; }

    public void setURI(String uRI) { this.uRI = uRI; }

    public Integer getScore() { return score; }

    public void setScore(Integer score) { this.score = score; }



}
