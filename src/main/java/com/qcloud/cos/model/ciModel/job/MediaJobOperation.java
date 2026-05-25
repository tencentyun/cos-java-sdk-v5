package com.qcloud.cos.model.ciModel.job;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoObjcet;
import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;
import com.qcloud.cos.model.ciModel.template.MediaSmartCoverObject;
import com.qcloud.cos.model.ciModel.template.MediaSnapshotObject;
import com.qcloud.cos.model.ciModel.template.MediaVideoMontageObject;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

/**
 * 媒体处理 operation实体 https://cloud.tencent.com/document/product/460/48234
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaJobOperation {
    @XStreamAlias("TemplateId")
    private String templateId;

    @XStreamAlias("Version")
    private String version;

    @XStreamAlias("JobLevel")
    private String jobLevel;

    @XStreamAlias("UserData")
    private String UserData;

    @XStreamAlias("TemplateName")
    private String templateName;

    @XStreamAlias("DecryptKey")
    private String decryptKey;

    @XStreamAlias("DecryptIv")
    private String decryptIv;

    @XStreamAlias("DecryptMode")
    private String decryptMode;

    @XStreamAlias("EncryptKey")
    private String encryptKey;

    @XStreamAlias("EncryptIv")
    private String encryptIv;

    @XStreamAlias("EncryptMode")
    private String encryptMode;
    @XStreamAlias("SnapshotPrefix")
    private String snapshotPrefix;

    @XStreamAlias("FreeTranscode")
    private String freeTranscode;

    @XStreamAlias("Output")
    private MediaOutputObject output;

    @XStreamAlias("MediaAnimation")
    private MediaAnimationObject mediaAnimation;

    @XStreamAlias("MediaInfo")
    private MediaInfoObjcet mediaInfo;

    @XStreamAlias("MediaRemoveWaterMark")
    private MediaRemoveWaterMark removeWatermark;

    @XStreamAlias("MediaWatermark")
    private MediaWatermark watermark;

    @XStreamAlias("MediaTranscode")
    private MediaTranscodeObject transcode;

    @XStreamAlias("WatermarkTemplateId")
    private List<String> watermarkTemplateId;

    @XStreamAlias("WatermarkList")
    private List<MediaWatermark> watermarkList;

    @XStreamAlias("MediaConcatTemplate")
    private MediaConcatTemplateObject mediaConcatTemplate;

    @XStreamAlias("MediaSnapshot")
    private MediaSnapshotObject snapshot;

    @XStreamAlias("MediaSegment")
    private MediaSegmentObject segment;

    @XStreamAlias("MediaSmartCover")
    private MediaSmartCoverObject smartCover;

    @XStreamAlias("MediaVideoMontage")
    private MediaVideoMontageObject videoMontage;

    @XStreamAlias("DigitalWatermark")
    private MediaDigitalWatermark digitalWatermark;

    @XStreamAlias("ExtractDigitalWatermark")
    private ExtractDigitalWatermark extractDigitalWatermark;

    @XStreamAlias("MediaPicProcessTemplate")
    private MediaPicProcessTemplateObject picProcess;

    @XStreamAlias("MediaResult")
    private MediaResult mediaResult;

    @XStreamAlias("PicProcessResult")
    private PicProcessResult picProcessResult;

    @XStreamAlias("VideoTargetRec")
    private VideoTargetRec videoTargetRec;

    @XStreamAlias("VoiceSeparate")
    private VoiceSeparate voiceSeparate;

    @XStreamAlias("MediaTimeInterval")
    private MediaTimeIntervalObject timeInterval;

    @XStreamAlias("MediaTtsConfig")
    private MediaTtsConfig ttsConfig;

    @XStreamAlias("VideoEnhance")
    private VideoEnhance videoEnhance;

    @XStreamAlias("TtsTpl")
    private TtsTpl ttsTpl;

    @XStreamAlias("Subtitles")
    private Subtitles subtitles;

    @XStreamAlias("VideoTag")
    private VideoTag videoTag;

    @XStreamAlias("VideoTagResult")
    private VideoTagResult videoTagResult;

    @XStreamAlias("QualityEstimateConfig")
    private QualityEstimateConfig qualityEstimateConfig;

    @XStreamAlias("QualityEstimate")
    private QualityEstimate qualityEstimate;

    @XStreamAlias("VocalScore")
    private VocalScore vocalScore;

    @XStreamAlias("VocalScoreResult")
    private VocalScoreResult vocalScoreResult;

    public VocalScoreResult getVocalScoreResult() {
        if (vocalScoreResult == null) {
            vocalScoreResult = new VocalScoreResult();
        }
        return vocalScoreResult;
    }

    public void setVocalScoreResult(VocalScoreResult vocalScoreResult) {
        this.vocalScoreResult = vocalScoreResult;
    }

    public VocalScore getVocalScore() {
        if (vocalScore == null) {
            vocalScore = new VocalScore();
        }
        return vocalScore;
    }

    public void setVocalScore(VocalScore vocalScore) {
        this.vocalScore = vocalScore;
    }

    public MediaTimeIntervalObject getTimeInterval() {
        if (timeInterval == null) {
            timeInterval = new MediaTimeIntervalObject();
        }
        return timeInterval;
    }

    public void setTimeInterval(MediaTimeIntervalObject timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public MediaOutputObject getOutput() {
        if (output == null) {
            output = new MediaOutputObject();
        }
        return output;
    }

    public void setOutput(MediaOutputObject output) {
        this.output = output;
    }

    public MediaAnimationObject getMediaAnimation() {
        if (mediaAnimation == null) {
            mediaAnimation = new MediaAnimationObject();
        }
        return mediaAnimation;
    }

    public MediaInfoObjcet getMediaInfo() {
        if (mediaInfo == null) {
            mediaInfo = new MediaInfoObjcet();
        }
        return mediaInfo;
    }

    public void setMediaInfo(MediaInfoObjcet mediaInfo) {
        this.mediaInfo = mediaInfo;
    }

    public MediaRemoveWaterMark getRemoveWatermark() {
        if (removeWatermark == null) {
            removeWatermark = new MediaRemoveWaterMark();
        }
        return removeWatermark;
    }

    public void setRemoveWatermark(MediaRemoveWaterMark removeWatermark) {
        this.removeWatermark = removeWatermark;
    }

    public void setMediaAnimation(MediaAnimationObject mediaAnimation) {
        this.mediaAnimation = mediaAnimation;
    }

    public MediaWatermark getWatermark() {
        if (watermark == null) {
            watermark = new MediaWatermark();
        }
        return watermark;
    }

    public void setWatermark(MediaWatermark watermark) {
        this.watermark = watermark;
    }


    public List<String> getWatermarkTemplateId() {
        if (watermarkTemplateId == null) {
            watermarkTemplateId = new ArrayList<>();
        }
        return watermarkTemplateId;
    }

    public void setWatermarkTemplateId(List<String> watermarkTemplateId) {
        this.watermarkTemplateId = watermarkTemplateId;
    }

    public MediaConcatTemplateObject getMediaConcatTemplate() {
        if (mediaConcatTemplate == null) {
            mediaConcatTemplate = new MediaConcatTemplateObject();
        }
        return mediaConcatTemplate;
    }

    public void setMediaConcatTemplate(MediaConcatTemplateObject mediaConcatTemplate) {
        this.mediaConcatTemplate = mediaConcatTemplate;
    }

    public MediaTranscodeObject getTranscode() {
        if (transcode == null) {
            transcode = new MediaTranscodeObject();
        }
        return transcode;
    }

    public void setTranscode(MediaTranscodeObject transcode) {
        this.transcode = transcode;
    }

    public MediaDigitalWatermark getDigitalWatermark() {
        if (digitalWatermark == null) {
            digitalWatermark = new MediaDigitalWatermark();
        }
        return digitalWatermark;
    }

    public void setDigitalWatermark(MediaDigitalWatermark digitalWatermark) {
        this.digitalWatermark = digitalWatermark;
    }

    public ExtractDigitalWatermark getExtractDigitalWatermark() {
        if (extractDigitalWatermark == null) {
            extractDigitalWatermark = new ExtractDigitalWatermark();
        }
        return extractDigitalWatermark;
    }

    public void setExtractDigitalWatermark(ExtractDigitalWatermark extractDigitalWatermark) {
        this.extractDigitalWatermark = extractDigitalWatermark;
    }

    @JsonIgnore
    public MediaSnapshotObject getSnapshot() {
        if (snapshot == null) {
            snapshot = new MediaSnapshotObject();
        }
        return snapshot;
    }

    public void setSnapshot(MediaSnapshotObject snapshot) {
        this.snapshot = snapshot;
    }

    @JsonIgnore
    public MediaSegmentObject getSegment() {
        if (segment == null) {
            segment = new MediaSegmentObject();
        }
        return segment;
    }

    public void setSegment(MediaSegmentObject segment) {
        this.segment = segment;
    }

    public List<MediaWatermark> getWatermarkList() {
        if (watermarkList == null) {
            watermarkList = new ArrayList<>();
        }
        return watermarkList;
    }

    public void setWatermarkList(List<MediaWatermark> watermarkList) {
        this.watermarkList = watermarkList;
    }

    public MediaSmartCoverObject getSmartCover() {
        if (smartCover == null) {
            smartCover = new MediaSmartCoverObject();
        }
        return smartCover;
    }

    public void setSmartCover(MediaSmartCoverObject smartCover) {
        this.smartCover = smartCover;
    }

    public MediaVideoMontageObject getVideoMontage() {
        if (videoMontage == null) {
            videoMontage = new MediaVideoMontageObject();
        }
        return videoMontage;
    }

    public void setVideoMontage(MediaVideoMontageObject videoMontage) {
        this.videoMontage = videoMontage;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public MediaPicProcessTemplateObject getPicProcess() {
        if (picProcess == null) {
            picProcess = new MediaPicProcessTemplateObject();
        }
        return picProcess;
    }

    public void setPicProcess(MediaPicProcessTemplateObject picProcess) {
        this.picProcess = picProcess;
    }

    public String getUserData() {
        return UserData;
    }

    public void setUserData(String userData) {
        UserData = userData;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public MediaResult getMediaResult() {
        if (mediaResult == null) {
            mediaResult = new MediaResult();
        }
        return mediaResult;
    }

    public void setMediaResult(MediaResult mediaResult) {
        this.mediaResult = mediaResult;
    }

    public PicProcessResult getPicProcessResult() {
        if (picProcessResult == null) {
            picProcessResult = new PicProcessResult();
        }
        return picProcessResult;
    }

    public void setPicProcessResult(PicProcessResult picProcessResult) {
        this.picProcessResult = picProcessResult;
    }

    public String getDecryptKey() {
        return decryptKey;
    }

    public void setDecryptKey(String decryptKey) {
        this.decryptKey = decryptKey;
    }

    public VideoTargetRec getVideoTargetRec() {
        if (videoTargetRec == null) {
            videoTargetRec = new VideoTargetRec();
        }
        return videoTargetRec;
    }

    public void setVideoTargetRec(VideoTargetRec videoTargetRec) {
        this.videoTargetRec = videoTargetRec;
    }

    @JsonIgnore
    public VoiceSeparate getVoiceSeparate() {
        if (voiceSeparate == null) {
            voiceSeparate = new VoiceSeparate();
        }
        return voiceSeparate;
    }

    public void setVoiceSeparate(VoiceSeparate voiceSeparate) {
        this.voiceSeparate = voiceSeparate;
    }

    public MediaTtsConfig getTtsConfig() {
        if (ttsConfig == null) {
            ttsConfig = new MediaTtsConfig();
        }
        return ttsConfig;
    }

    public void setTtsConfig(MediaTtsConfig ttsConfig) {
        this.ttsConfig = ttsConfig;
    }

    public TtsTpl getTtsTpl() {
        if (ttsTpl == null) {
            ttsTpl = new TtsTpl();
        }
        return ttsTpl;
    }

    public void setTtsTpl(TtsTpl ttsTpl) {
        this.ttsTpl = ttsTpl;
    }

    public VideoEnhance getVideoEnhance() {
        if (videoEnhance == null) {
            videoEnhance = new VideoEnhance();
        }
        return videoEnhance;
    }

    public void setVideoEnhance(VideoEnhance videoEnhance) {
        this.videoEnhance = videoEnhance;
    }

    public Subtitles getSubtitles() {
        if (subtitles == null) {
            subtitles = new Subtitles();
        }
        return subtitles;
    }

    public void setSubtitles(Subtitles subtitles) {
        this.subtitles = subtitles;
    }

    public VideoTag getVideoTag() {
        if (videoTag == null) {
            videoTag = new VideoTag();
        }
        return videoTag;
    }

    public void setVideoTag(VideoTag videoTag) {
        this.videoTag = videoTag;
    }

    public String getDecryptIv() {
        return decryptIv;
    }

    public void setDecryptIv(String decryptIv) {
        this.decryptIv = decryptIv;
    }

    public String getDecryptMode() {
        return decryptMode;
    }

    public void setDecryptMode(String decryptMode) {
        this.decryptMode = decryptMode;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getEncryptIv() {
        return encryptIv;
    }

    public void setEncryptIv(String encryptIv) {
        this.encryptIv = encryptIv;
    }

    public String getEncryptMode() {
        return encryptMode;
    }

    public void setEncryptMode(String encryptMode) {
        this.encryptMode = encryptMode;
    }

    public VideoTagResult getVideoTagResult() {
        if (videoTagResult == null) {
            videoTagResult = new VideoTagResult();
        }
        return videoTagResult;
    }

    public void setVideoTagResult(VideoTagResult videoTagResult) {
        this.videoTagResult = videoTagResult;
    }

    public QualityEstimateConfig getQualityEstimateConfig() {
        if (qualityEstimateConfig == null) {
            qualityEstimateConfig = new QualityEstimateConfig();
        }
        return qualityEstimateConfig;
    }

    public void setQualityEstimateConfig(QualityEstimateConfig qualityEstimateConfig) {
        this.qualityEstimateConfig = qualityEstimateConfig;
    }

    public QualityEstimate getQualityEstimate() {
        if (qualityEstimate == null) {
            qualityEstimate = new QualityEstimate();
        }
        return qualityEstimate;
    }

    public void setQualityEstimate(QualityEstimate qualityEstimate) {
        this.qualityEstimate = qualityEstimate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFreeTranscode() {
        return freeTranscode;
    }

    public void setFreeTranscode(String freeTranscode) {
        this.freeTranscode = freeTranscode;
    }

    public String getSnapshotPrefix() {
        return snapshotPrefix;
    }

    public void setSnapshotPrefix(String snapshotPrefix) {
        this.snapshotPrefix = snapshotPrefix;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaJobOperation{");
        sb.append("templateId='").append(templateId).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", jobLevel='").append(jobLevel).append('\'');
        sb.append(", UserData='").append(UserData).append('\'');
        sb.append(", templateName='").append(templateName).append('\'');
        sb.append(", decryptKey='").append(decryptKey).append('\'');
        sb.append(", decryptIv='").append(decryptIv).append('\'');
        sb.append(", decryptMode='").append(decryptMode).append('\'');
        sb.append(", encryptKey='").append(encryptKey).append('\'');
        sb.append(", encryptIv='").append(encryptIv).append('\'');
        sb.append(", encryptMode='").append(encryptMode).append('\'');
        sb.append(", output=").append(output);
        sb.append(", mediaAnimation=").append(mediaAnimation);
        sb.append(", mediaInfo=").append(mediaInfo);
        sb.append(", removeWatermark=").append(removeWatermark);
        sb.append(", watermark=").append(watermark);
        sb.append(", transcode=").append(transcode);
        sb.append(", watermarkTemplateId=").append(watermarkTemplateId);
        sb.append(", watermarkList=").append(watermarkList);
        sb.append(", mediaConcatTemplate=").append(mediaConcatTemplate);
        sb.append(", snapshot=").append(snapshot);
        sb.append(", segment=").append(segment);
        sb.append(", smartCover=").append(smartCover);
        sb.append(", videoMontage=").append(videoMontage);
        sb.append(", digitalWatermark=").append(digitalWatermark);
        sb.append(", extractDigitalWatermark=").append(extractDigitalWatermark);
        sb.append(", picProcess=").append(picProcess);
        sb.append(", mediaResult=").append(mediaResult);
        sb.append(", picProcessResult=").append(picProcessResult);
        sb.append(", videoTargetRec=").append(videoTargetRec);
        sb.append(", voiceSeparate=").append(voiceSeparate);
        sb.append(", timeInterval=").append(timeInterval);
        sb.append(", ttsConfig=").append(ttsConfig);
        sb.append(", videoEnhance=").append(videoEnhance);
        sb.append(", ttsTpl=").append(ttsTpl);
        sb.append(", subtitles=").append(subtitles);
        sb.append(", videoTag=").append(videoTag);
        sb.append(", videoTagResult=").append(videoTagResult);
        sb.append(", qualityEstimateConfig=").append(qualityEstimateConfig);
        sb.append(", qualityEstimate=").append(qualityEstimate);
        sb.append(", vocalScore=").append(vocalScore);
        sb.append(", vocalScoreResult=").append(vocalScoreResult);
        sb.append('}');
        return sb.toString();
    }
}
