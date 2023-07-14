package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Labels")
public class StrategyLabels {
    @XStreamAlias("Image")
    private StrategyImageLabel image;
    @XStreamAlias("Text")
    private StrategyTextLabel text;
    @XStreamAlias("Audio")
    private StrategyAudioLabel audio;

    public StrategyImageLabel getImage() {
        if (image == null) {
            image = new StrategyImageLabel();
        }
        return image;
    }

    public void setImage(StrategyImageLabel image) {
        this.image = image;
    }

    public StrategyTextLabel getText() {
        if (text == null) {
            text = new StrategyTextLabel();
        }
        return text;
    }

    public void setText(StrategyTextLabel text) {
        this.text = text;
    }

    public StrategyAudioLabel getAudio() {
        if (audio == null) {
            audio = new StrategyAudioLabel();
        }
        return audio;
    }

    public void setAudio(StrategyAudioLabel audio) {
        this.audio = audio;
    }
}
