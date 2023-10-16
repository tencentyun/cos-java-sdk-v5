package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class WordList {
    /**
     * 词级别文本
     */
    @XStreamAlias("word")
    private String word;

    /**
     * 开始时间
     */
    @XStreamAlias("start_time")
    private Integer start_time;

    /**
     * 结束时间
     */
    @XStreamAlias("end_time")
    private Integer end_time;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getStart_time() {
        return start_time;
    }

    public void setStart_time(Integer start_time) {
        this.start_time = start_time;
    }

    public Integer getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Integer end_time) {
        this.end_time = end_time;
    }

}