package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoObjcet;
import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;
import com.qcloud.cos.model.ciModel.template.MediaSmartCoverObject;
import com.qcloud.cos.model.ciModel.template.MediaSnapshotObject;
import com.qcloud.cos.model.ciModel.template.MediaVideoMontageObject;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;

import java.util.ArrayList;
import java.util.List;

/**
 * 媒体处理 operation实体 https://cloud.tencent.com/document/product/460/48234
 */

public class MediaJobOperation {
    private String templateId;
    private String jobLevel;
    private String UserData;
    private String templateName;
    private MediaOutputObject output;
    private MediaAnimationObject mediaAnimation;
    private MediaInfoObjcet mediaInfo;
    private MediaRemoveWaterMark removeWatermark;
    private MediaWatermark watermark;
    private MediaTranscodeObject transcode;
    private List<String> watermarkTemplateId;
    private List<MediaWatermark> watermarkList;
    private MediaConcatTemplateObject mediaConcatTemplate;
    private MediaSnapshotObject snapshot = new MediaSnapshotObject();
    private MediaSegmentObject segment = new MediaSegmentObject();
    private MediaSmartCoverObject smartCover = new MediaSmartCoverObject();
    private MediaVideoMontageObject videoMontage = new MediaVideoMontageObject();
    private MediaDigitalWatermark digitalWatermark = new MediaDigitalWatermark();
    private ExtractDigitalWatermark extractDigitalWatermark = new ExtractDigitalWatermark();
    private MediaPicProcessTemplateObject picProcess = new MediaPicProcessTemplateObject();
    private MediaResult mediaResult = new MediaResult();
    private PicProcessResult picProcessResult = new PicProcessResult();

    public MediaJobOperation() {
        this.output = new MediaOutputObject();
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
        if (picProcess ==null ){
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaJobOperation{");
        sb.append("templateId='").append(templateId).append('\'');
        sb.append(", jobLevel='").append(jobLevel).append('\'');
        sb.append(", UserData='").append(UserData).append('\'');
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
        sb.append('}');
        return sb.toString();
    }
}
