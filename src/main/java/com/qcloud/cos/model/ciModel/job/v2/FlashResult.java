package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.model.ciModel.job.PostSpeechRecognitionResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("FlashResult")
public class FlashResult {
    /**
     * 声道标识，从0开始，对应音频声道数
     */
    @XStreamAlias("channel_id")
    private Integer channelId;

    /**
     * 声道音频完整识别结果
     */
    @XStreamAlias("text")
    private String text;

    /**
     * 句子/段落级别的识别结果列表
     */
    @XStreamImplicit(itemFieldName = "sentence_list")
    private List<Sentence> sentenceList;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Sentence> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(List<Sentence> sentenceList) {
        this.sentenceList = sentenceList;
    }
}
