package com.qcloud.cos.model.ciModel.job;

public class VoiceSeparate {
    /**
     * IsAudio：输出人声
     * IsBackground：输出背景声
     * AudioAndBackground：输出人声和背景声
     */
    private String audioMode;

    /**
     * 音频配置
     */
    private AudioConfig audioConfig;

    public String getAudioMode() {
        return audioMode;
    }

    public void setAudioMode(String audioMode) {
        this.audioMode = audioMode;
    }

    public AudioConfig getAudioConfig() {
        if (audioConfig == null) {
            audioConfig = new AudioConfig();
        }
        return audioConfig;
    }

    public void setAudioConfig(AudioConfig audioConfig) {
        this.audioConfig = audioConfig;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VoiceSeparate{");
        sb.append("audioMode='").append(audioMode).append('\'');
        sb.append(", audioConfig=").append(audioConfig);
        sb.append('}');
        return sb.toString();
    }
}
