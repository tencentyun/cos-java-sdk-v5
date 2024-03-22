package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体文本分片的审核结果信息，只返回带有违规结果的分片
 */
public class SectionInfo {
    @XStreamAlias("StartByte")
    private String startByte;

    @XStreamAlias("PornInfo")
    private PornInfo pornInfo;

    @XStreamAlias("TerrorismInfo")
    private TerroristInfo terroristInfo;

    @XStreamAlias("PoliticsInfo")
    private PoliticsInfo politicsInfo;

    @XStreamAlias("AdsInfo")
    private AdsInfo adsInfo;

    @XStreamAlias("AbuseInfo")
    private AbuseInfo abuseInfo;

    @XStreamAlias("IllegalInfo")
    private IllegalInfo illegalInfo;

    @XStreamAlias("TeenagerInfo")
    private TeenagerInfo teenagerInfo;

    @XStreamAlias("Text")
    private String text;

    @XStreamAlias("Url")
    private String url;

    @XStreamAlias("Duration")
    private String duration;

    @XStreamAlias("OffsetTime")
    private String offsetTime;

    @XStreamAlias("Label")
    private String label;

    @XStreamAlias("Result")
    private String result;

    @XStreamAlias("SubLabel")
    private String subLabel;

    @XStreamAlias("SentimentAnalysis")
    private SentimentAnalysis sentimentAnalysis;

    @XStreamAlias("ValueInfo")
    private ValueInfo valueInfo;

    @XStreamImplicit(itemFieldName = "LanguageResult")
    private List<LanguageResult> languageResult = new ArrayList<>();

    public SentimentAnalysis getSentimentAnalysis() {
        return sentimentAnalysis;
    }

    public void setSentimentAnalysis(SentimentAnalysis sentimentAnalysis) {
        this.sentimentAnalysis = sentimentAnalysis;
    }

    public String getSubLabel() {
        return subLabel;
    }

    public void setSubLabel(String subLabel) {
        this.subLabel = subLabel;
    }

    public List<LanguageResult> getLanguageResult() {
        if (languageResult == null) {
            languageResult = new ArrayList<>();
        }
        return languageResult;
    }

    public ValueInfo getValueInfo() {
        return valueInfo;
    }

    public void setValueInfo(ValueInfo valueInfo) {
        this.valueInfo = valueInfo;
    }

    public void setLanguageResult(List<LanguageResult> languageResult) {
        this.languageResult = languageResult;
    }

    public PornInfo getPornInfo() {
        if (pornInfo == null) {
            pornInfo = new PornInfo();
        }
        return pornInfo;
    }

    public void setPornInfo(PornInfo pornInfo) {
        this.pornInfo = pornInfo;
    }

    public TerroristInfo getTerroristInfo() {
        if (terroristInfo == null) {
            terroristInfo = new TerroristInfo();
        }
        return terroristInfo;
    }

    public void setTerroristInfo(TerroristInfo terroristInfo) {
        this.terroristInfo = terroristInfo;
    }

    public PoliticsInfo getPoliticsInfo() {
        if (politicsInfo == null) {
            politicsInfo = new PoliticsInfo();
        }
        return politicsInfo;
    }

    public void setPoliticsInfo(PoliticsInfo politicsInfo) {
        this.politicsInfo = politicsInfo;
    }

    public AdsInfo getAdsInfo() {
        if (adsInfo == null) {
            adsInfo = new AdsInfo();
        }
        return adsInfo;
    }

    public void setAdsInfo(AdsInfo adsInfo) {
        this.adsInfo = adsInfo;
    }

    public String getStartByte() {
        return startByte;
    }

    public void setStartByte(String startByte) {
        this.startByte = startByte;
    }

    public AbuseInfo getAbuseInfo() {
        if (abuseInfo == null) {
            abuseInfo = new AbuseInfo();
        }
        return abuseInfo;
    }

    public void setAbuseInfo(AbuseInfo abuseInfo) {
        this.abuseInfo = abuseInfo;
    }

    public IllegalInfo getIllegalInfo() {
        if (illegalInfo == null) {
            illegalInfo = new IllegalInfo();
        }
        return illegalInfo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOffsetTime() {
        return offsetTime;
    }

    public void setOffsetTime(String offsetTime) {
        this.offsetTime = offsetTime;
    }

    public void setIllegalInfo(IllegalInfo illegalInfo) {
        this.illegalInfo = illegalInfo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public TeenagerInfo getTeenagerInfo() {
        if (teenagerInfo == null) {
            teenagerInfo = new TeenagerInfo();
        }
        return teenagerInfo;
    }

    public void setTeenagerInfo(TeenagerInfo teenagerInfo) {
        this.teenagerInfo = teenagerInfo;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SectionInfo{");
        sb.append("startByte='").append(startByte).append('\'');
        sb.append(", pornInfo=").append(pornInfo);
        sb.append(", terroristInfo=").append(terroristInfo);
        sb.append(", politicsInfo=").append(politicsInfo);
        sb.append(", adsInfo=").append(adsInfo);
        sb.append(", abuseInfo=").append(abuseInfo);
        sb.append(", illegalInfo=").append(illegalInfo);
        sb.append(", text='").append(text).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", duration='").append(duration).append('\'');
        sb.append(", offsetTime='").append(offsetTime).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", result='").append(result).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
