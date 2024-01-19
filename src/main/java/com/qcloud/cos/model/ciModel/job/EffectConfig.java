package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class EffectConfig {
    @XStreamAlias("EnableStartFadein")
    private String enableStartFadein;

    @XStreamAlias("StartFadeinTime")
    private String startFadeinTime;

    @XStreamAlias("EnableEndFadeout")
    private String enableEndFadeout;

    @XStreamAlias("EndFadeoutTime")
    private String endFadeoutTime;

    @XStreamAlias("EnableBgmFade")
    private String enableBgmFade;

    @XStreamAlias("BgmFadeTime")
    private String bgmFadeTime;

    public String getEnableStartFadein() {
        return enableStartFadein;
    }

    public void setEnableStartFadein(String enableStartFadein) {
        this.enableStartFadein = enableStartFadein;
    }

    public String getStartFadeinTime() {
        return startFadeinTime;
    }

    public void setStartFadeinTime(String startFadeinTime) {
        this.startFadeinTime = startFadeinTime;
    }

    public String getEnableEndFadeout() {
        return enableEndFadeout;
    }

    public void setEnableEndFadeout(String enableEndFadeout) {
        this.enableEndFadeout = enableEndFadeout;
    }

    public String getEndFadeoutTime() {
        return endFadeoutTime;
    }

    public void setEndFadeoutTime(String endFadeoutTime) {
        this.endFadeoutTime = endFadeoutTime;
    }

    public String getEnableBgmFade() {
        return enableBgmFade;
    }

    public void setEnableBgmFade(String enableBgmFade) {
        this.enableBgmFade = enableBgmFade;
    }

    public String getBgmFadeTime() {
        return bgmFadeTime;
    }

    public void setBgmFadeTime(String bgmFadeTime) {
        this.bgmFadeTime = bgmFadeTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EffectConfig{");
        sb.append("enableStartFadein='").append(enableStartFadein).append('\'');
        sb.append(", startFadeinTime='").append(startFadeinTime).append('\'');
        sb.append(", enableEndFadeout='").append(enableEndFadeout).append('\'');
        sb.append(", endFadeoutTime='").append(endFadeoutTime).append('\'');
        sb.append(", enableBgmFade='").append(enableBgmFade).append('\'');
        sb.append(", bgmFadeTime='").append(bgmFadeTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
