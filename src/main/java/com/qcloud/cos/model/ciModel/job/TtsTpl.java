package com.qcloud.cos.model.ciModel.job;

public class TtsTpl {
    private String mode;
    private String codec;
    private String voiceType;
    private String volume;
    private String speed;


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getVoiceType() {
        return voiceType;
    }

    public void setVoiceType(String voiceType) {
        this.voiceType = voiceType;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TtsTpl{");
        sb.append("mode='").append(mode).append('\'');
        sb.append(", codec='").append(codec).append('\'');
        sb.append(", voiceType='").append(voiceType).append('\'');
        sb.append(", volume='").append(volume).append('\'');
        sb.append(", speed='").append(speed).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
