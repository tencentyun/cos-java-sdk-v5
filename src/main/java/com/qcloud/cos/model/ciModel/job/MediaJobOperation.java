package com.qcloud.cos.model.ciModel.job;

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
    private MediaSnapshotObject snapshot = new MediaSnapshotObject();

    @XStreamAlias("MediaSegment")
    private MediaSegmentObject segment = new MediaSegmentObject();

    @XStreamAlias("MediaSmartCover")
    private MediaSmartCoverObject smartCover = new MediaSmartCoverObject();

    @XStreamAlias("MediaVideoMontage")
    private MediaVideoMontageObject videoMontage = new MediaVideoMontageObject();

    @XStreamAlias("DigitalWatermark")
    private MediaDigitalWatermark digitalWatermark = new MediaDigitalWatermark();

    @XStreamAlias("ExtractDigitalWatermark")
    private ExtractDigitalWatermark extractDigitalWatermark = new ExtractDigitalWatermark();

    @XStreamAlias("MediaPicProcessTemplate")
    private MediaPicProcessTemplateObject picProcess = new MediaPicProcessTemplateObject();

    @XStreamAlias("MediaResult")
    private MediaResult mediaResult = new MediaResult();

    @XStreamAlias("PicProcessResult")
    private PicProcessResult picProcessResult = new PicProcessResult();

    @XStreamAlias("VideoTargetRec")
    private VideoTargetRec videoTargetRec = new VideoTargetRec();

    @XStreamAlias("VoiceSeparate")
    private VoiceSeparate voiceSeparate = new VoiceSeparate();

    @XStreamAlias("MediaTimeInterval")
    private MediaTimeIntervalObject timeInterval = new MediaTimeIntervalObject();

    @XStreamAlias("MediaTtsConfig")
    private MediaTtsConfig ttsConfig = new MediaTtsConfig();

    @XStreamAlias("VideoEnhance")
    private VideoEnhance videoEnhance = new VideoEnhance();

    @XStreamAlias("TtsTpl")
    private TtsTpl ttsTpl = new TtsTpl();

    @XStreamAlias("Subtitles")
    private Subtitles subtitles = new Subtitles();

    @XStreamAlias("VideoTag")
    private VideoTag videoTag = new VideoTag();

    @XStreamAlias("VideoTagResult")
    private VideoTagResult videoTagResult = new VideoTagResult();

    @XStreamAlias("QualityEstimateConfig")
    private QualityEstimateConfig qualityEstimateConfig = new QualityEstimateConfig();

    @XStreamAlias("QualityEstimate")
    private QualityEstimate qualityEstimate = new QualityEstimate();

    @XStreamAlias("VocalScore")
    private VocalScore vocalScore = new VocalScore();

    @XStreamAlias("VocalScoreResult")
    private VocalScoreResult vocalScoreResult = new VocalScoreResult();

    public VocalScoreResult getVocalScoreResult() {
        return vocalScoreResult;
    }

    public void setVocalScoreResult(VocalScoreResult vocalScoreResult) {
        this.vocalScoreResult = vocalScoreResult;
    }

    public VocalScore getVocalScore() {
        return vocalScore;
    }

    public void setVocalScore(VocalScore vocalScore) {
        this.vocalScore = vocalScore;
    }

    public MediaTimeIntervalObject getTimeInterval() {
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
        return digitalWatermark;
    }

    public void setDigitalWatermark(MediaDigitalWatermark digitalWatermark) {
        this.digitalWatermark = digitalWatermark;
    }

    public ExtractDigitalWatermark getExtractDigitalWatermark() {
        return extractDigitalWatermark;
    }

    public void setExtractDigitalWatermark(ExtractDigitalWatermark extractDigitalWatermark) {
        this.extractDigitalWatermark = extractDigitalWatermark;
    }

    public MediaSnapshotObject getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(MediaSnapshotObject snapshot) {
        this.snapshot = snapshot;
    }

    public MediaSegmentObject getSegment() {
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
        return smartCover;
    }

    public void setSmartCover(MediaSmartCoverObject smartCover) {
        this.smartCover = smartCover;
    }

    public MediaVideoMontageObject getVideoMontage() {
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
        return mediaResult;
    }

    public void setMediaResult(MediaResult mediaResult) {
        this.mediaResult = mediaResult;
    }

    public PicProcessResult getPicProcessResult() {
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

    public VoiceSeparate getVoiceSeparate() {
        return voiceSeparate;
    }

    public void setVoiceSeparate(VoiceSeparate voiceSeparate) {
        this.voiceSeparate = voiceSeparate;
    }

    public MediaTtsConfig getTtsConfig() {
        return ttsConfig;
    }

    public void setTtsConfig(MediaTtsConfig ttsConfig) {
        this.ttsConfig = ttsConfig;
    }

    public TtsTpl getTtsTpl() {
        return ttsTpl;
    }

    public void setTtsTpl(TtsTpl ttsTpl) {
        this.ttsTpl = ttsTpl;
    }

    public VideoEnhance getVideoEnhance() {
        return videoEnhance;
    }

    public void setVideoEnhance(VideoEnhance videoEnhance) {
        this.videoEnhance = videoEnhance;
    }

    public Subtitles getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(Subtitles subtitles) {
        this.subtitles = subtitles;
    }

    public VideoTag getVideoTag() {
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
