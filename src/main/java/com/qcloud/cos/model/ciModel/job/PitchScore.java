package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("PitchScore")
public class PitchScore {
    @XStreamImplicit(itemFieldName = "SentenceScores")
    List<SentenceScores> sentenceScores;

    public List<SentenceScores> getSentenceScores() {
        return sentenceScores;
    }

    public void setSentenceScores(List<SentenceScores> sentenceScores) {
        this.sentenceScores = sentenceScores;
    }
}
