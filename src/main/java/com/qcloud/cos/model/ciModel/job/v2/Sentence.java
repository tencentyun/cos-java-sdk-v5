package com.qcloud.cos.model.ciModel.job.v2;


import com.qcloud.cos.model.ciModel.job.PostSpeechRecognitionResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class Sentence {
    /**
     * 句子/段落级别文本
     */
    @XStreamAlias("text")
    private String text;

    /**
     * 开始时间
     */
    @XStreamAlias("start_time")
    private Integer startTime;

    /**
     * 结束时间
     */
    @XStreamAlias("end_time")
    private Integer endTime;

    /**
     * 说话人 Id（请求中如果设置了 speaker_diarization，可以按照 speaker_id 来区分说话人）
     */
    @XStreamAlias("speaker_id")
    private Integer speakerId;

    /**
     * 词级别的识别结果列表
     */
    @XStreamImplicit(itemFieldName = "word_list")
    private List<WordList> wordList;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(Integer speakerId) {
        this.speakerId = speakerId;
    }

    public List<WordList> getWordList() {
        return wordList;
    }

    public void setWordList(List<WordList> wordList) {
        this.wordList = wordList;
    }
}
