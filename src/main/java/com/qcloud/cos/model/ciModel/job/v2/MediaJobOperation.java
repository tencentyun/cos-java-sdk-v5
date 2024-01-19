package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.ExtractDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.MediaAnimationObject;
import com.qcloud.cos.model.ciModel.job.MediaConcatTemplateObject;
import com.qcloud.cos.model.ciModel.job.MediaDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.MediaPicProcessTemplateObject;
import com.qcloud.cos.model.ciModel.job.MediaRemoveWaterMark;
import com.qcloud.cos.model.ciModel.job.MediaResult;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeObject;
import com.qcloud.cos.model.ciModel.job.MediaTtsConfig;
import com.qcloud.cos.model.ciModel.job.PicProcessResult;
import com.qcloud.cos.model.ciModel.job.QualityEstimate;
import com.qcloud.cos.model.ciModel.job.QualityEstimateConfig;
import com.qcloud.cos.model.ciModel.job.Subtitles;
import com.qcloud.cos.model.ciModel.job.TtsTpl;
import com.qcloud.cos.model.ciModel.job.VideoEnhance;
import com.qcloud.cos.model.ciModel.job.VideoTag;
import com.qcloud.cos.model.ciModel.job.VideoTagResult;
import com.qcloud.cos.model.ciModel.job.VideoTargetRec;
import com.qcloud.cos.model.ciModel.job.VocalScore;
import com.qcloud.cos.model.ciModel.job.VocalScoreResult;
import com.qcloud.cos.model.ciModel.job.VoiceSeparate;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoObjcet;
import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;
import com.qcloud.cos.model.ciModel.template.MediaSmartCoverObject;
import com.qcloud.cos.model.ciModel.template.MediaSnapshotObject;
import com.qcloud.cos.model.ciModel.template.MediaVideoMontageObject;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 媒体处理 operation实体 https://cloud.tencent.com/document/product/460/48234
 */

public class MediaJobOperation {
    @XStreamAlias("TemplateId")
    private String templateId;

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

    @XStreamAlias("Animation")
    private MediaAnimationObject mediaAnimation;

    @XStreamAlias("Info")
    private MediaInfoObjcet mediaInfo;

    @XStreamAlias("RemoveWaterMark")
    private MediaRemoveWaterMark removeWatermark;

    @XStreamAlias("Watermark")
    private MediaWatermark watermark;

    @XStreamAlias("Transcode")
    private MediaTranscodeObject transcode;

    @XStreamImplicit(itemFieldName = "WatermarkTemplateId")
    private List<String> watermarkTemplateId;

    @XStreamImplicit(itemFieldName = "WatermarkList")
    private List<MediaWatermark> watermarkList;

    @XStreamAlias("ConcatTemplate")
    private MediaConcatTemplateObject mediaConcatTemplate;

    @XStreamAlias("Snapshot")
    private MediaSnapshotObject snapshot;

    @XStreamAlias("Segment")
    private MediaSegmentObject segment;

    @XStreamAlias("SmartCover")
    private MediaSmartCoverObject smartCover;

    @XStreamAlias("VideoMontage")
    private MediaVideoMontageObject videoMontage;

    @XStreamAlias("DigitalWatermark")
    private MediaDigitalWatermark digitalWatermark;

    @XStreamAlias("ExtractDigitalWatermark")
    private ExtractDigitalWatermark extractDigitalWatermark;

    @XStreamAlias("PicProcessTemplate")
    private MediaPicProcessTemplateObject picProcess;

    @XStreamAlias("Result")
    private MediaResult mediaResult;

    @XStreamAlias("PicProcessResult")
    private PicProcessResult picProcessResult;

    @XStreamAlias("VideoTargetRec")
    private VideoTargetRec videoTargetRec;

    @XStreamAlias("VoiceSeparate")
    private VoiceSeparate voiceSeparate;

    @XStreamAlias("TimeInterval")
    private MediaTimeIntervalObject timeInterval;

    @XStreamAlias("TtsConfig")
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

    @XStreamAlias("SoundHoundResult")
    private SoundHoundResult soundHoundResult;

    @XStreamAlias("SegmentVideoBody")
    private SegmentVideoBody segmentVideoBody;

    @XStreamAlias("SpeechRecognition")
    private SpeechRecognition speechRecognition;

    @XStreamAlias("SpeechRecognitionResult")
    private SpeechRecognitionResult speechRecognitionResult;

    @XStreamAlias("FillConcat")
    private FillConcat fillConcat;

    @XStreamAlias("SplitVideoParts")
    private SplitVideoParts splitVideoParts;

    @XStreamAlias("DnaConfig")
    private DnaConfig dnaConfig;

    @XStreamAlias("SplitVideoInfoResult")
    private SplitVideoInfoResult splitVideoInfoResult;

    @XStreamAlias("DNAResult")
    private DNAResult dnaResult;

    public DNAResult getDnaResult() {
        return dnaResult;
    }

    public void setDnaResult(DNAResult dnaResult) {
        this.dnaResult = dnaResult;
    }

    public DnaConfig getDnaConfig() {
        if (dnaConfig == null) {
            dnaConfig = new DnaConfig();
        }
        return dnaConfig;
    }

    public void setDnaConfig(DnaConfig dnaConfig) {
        this.dnaConfig = dnaConfig;
    }

    public SplitVideoParts getSplitVideoParts() {
        if (splitVideoParts == null) {
            splitVideoParts = new SplitVideoParts();
        }
        return splitVideoParts;
    }

    public void setSplitVideoParts(SplitVideoParts splitVideoParts) {
        this.splitVideoParts = splitVideoParts;
    }

    public SplitVideoInfoResult getSplitVideoInfoResult() {
        return splitVideoInfoResult;
    }

    public void setSplitVideoInfoResult(SplitVideoInfoResult splitVideoInfoResult) {
        this.splitVideoInfoResult = splitVideoInfoResult;
    }

    public FillConcat getFillConcat() {
        if (fillConcat == null) {
            fillConcat = new FillConcat();
        }
        return fillConcat;
    }

    public void setFillConcat(FillConcat fillConcat) {
        this.fillConcat = fillConcat;
    }

    public SegmentVideoBody getSegmentVideoBody() {
        if (segmentVideoBody == null) {
            segmentVideoBody = new SegmentVideoBody();
        }
        return segmentVideoBody;
    }

    public void setSegmentVideoBody(SegmentVideoBody segmentVideoBody) {
        this.segmentVideoBody = segmentVideoBody;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
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

    public String getDecryptKey() {
        return decryptKey;
    }

    public void setDecryptKey(String decryptKey) {
        this.decryptKey = decryptKey;
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

    public SoundHoundResult getSoundHoundResult() {
        return soundHoundResult;
    }

    public void setSoundHoundResult(SoundHoundResult soundHoundResult) {
        this.soundHoundResult = soundHoundResult;
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

    public void setMediaAnimation(MediaAnimationObject mediaAnimation) {
        this.mediaAnimation = mediaAnimation;
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

    public MediaWatermark getWatermark() {
        if (watermark == null) {
            watermark = new MediaWatermark();
        }
        return watermark;
    }

    public void setWatermark(MediaWatermark watermark) {
        this.watermark = watermark;
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

    public List<String> getWatermarkTemplateId() {
        if (watermarkTemplateId == null) {
            watermarkTemplateId = new ArrayList<>();
        }
        return watermarkTemplateId;
    }

    public void setWatermarkTemplateId(List<String> watermarkTemplateId) {
        this.watermarkTemplateId = watermarkTemplateId;
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

    public MediaConcatTemplateObject getMediaConcatTemplate() {
        if (mediaConcatTemplate == null) {
            mediaConcatTemplate = new MediaConcatTemplateObject();
        }
        return mediaConcatTemplate;
    }

    public void setMediaConcatTemplate(MediaConcatTemplateObject mediaConcatTemplate) {
        this.mediaConcatTemplate = mediaConcatTemplate;
    }

    public MediaSnapshotObject getSnapshot() {
        if (snapshot == null) {
            snapshot = new MediaSnapshotObject();
        }
        return snapshot;
    }

    public void setSnapshot(MediaSnapshotObject snapshot) {
        this.snapshot = snapshot;
    }

    public MediaSegmentObject getSegment() {
        if (segment == null) {
            segment = new MediaSegmentObject();
        }
        return segment;
    }

    public void setSegment(MediaSegmentObject segment) {
        this.segment = segment;
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

    public MediaPicProcessTemplateObject getPicProcess() {
        if (picProcess == null) {
            picProcess = new MediaPicProcessTemplateObject();
        }
        return picProcess;
    }

    public void setPicProcess(MediaPicProcessTemplateObject picProcess) {
        this.picProcess = picProcess;
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
        if (voiceSeparate == null) {
            voiceSeparate = new VoiceSeparate();
        }
        return voiceSeparate;
    }

    public void setVoiceSeparate(VoiceSeparate voiceSeparate) {
        this.voiceSeparate = voiceSeparate;
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

    public MediaTtsConfig getTtsConfig() {
        if (ttsConfig == null) {
            ttsConfig = new MediaTtsConfig();
        }
        return ttsConfig;
    }

    public void setTtsConfig(MediaTtsConfig ttsConfig) {
        this.ttsConfig = ttsConfig;
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

    public TtsTpl getTtsTpl() {
        if (ttsTpl == null) {
            ttsTpl = new TtsTpl();
        }
        return ttsTpl;
    }

    public void setTtsTpl(TtsTpl ttsTpl) {
        this.ttsTpl = ttsTpl;
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

    public VocalScore getVocalScore() {
        if (vocalScore == null) {
            vocalScore = new VocalScore();
        }
        return vocalScore;
    }

    public void setVocalScore(VocalScore vocalScore) {
        this.vocalScore = vocalScore;
    }

    public VocalScoreResult getVocalScoreResult() {
        if (vocalScoreResult == null) {
            vocalScoreResult = new VocalScoreResult();
        }
        return vocalScoreResult;
    }

    public void setVocalScoreResult(VocalScoreResult vocalScoreResult) {
        this.vocalScoreResult = vocalScoreResult;
    }

    public SoundHoundResult getSoundHound() {
        if (soundHoundResult == null) {
            soundHoundResult = new SoundHoundResult();
        }
        return soundHoundResult;
    }

    public void setSoundHound(SoundHoundResult soundHoundResult) {
        this.soundHoundResult = soundHoundResult;
    }

    public SpeechRecognition getSpeechRecognition() {
        return speechRecognition;
    }

    public void setSpeechRecognition(SpeechRecognition speechRecognition) {
        this.speechRecognition = speechRecognition;
    }

    public SpeechRecognitionResult getSpeechRecognitionResult() {
        return speechRecognitionResult;
    }

    public void setSpeechRecognitionResult(SpeechRecognitionResult speechRecognitionResult) {
        this.speechRecognitionResult = speechRecognitionResult;
    }
}
