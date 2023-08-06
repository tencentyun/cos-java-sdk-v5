package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("VocalScoreResult")
public class VocalScoreResult {
    @XStreamAlias("PitchScore")
    private PitchScore pitchScore;
    @XStreamAlias("RhythemScore")
    private RhythemScore rhythemScore;

    public PitchScore getPitchScore() {
        return pitchScore;
    }

    public void setPitchScore(PitchScore pitchScore) {
        this.pitchScore = pitchScore;
    }

    public RhythemScore getRhythemScore() {
        return rhythemScore;
    }

    public void setRhythemScore(RhythemScore rhythemScore) {
        this.rhythemScore = rhythemScore;
    }
}
