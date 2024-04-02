package com.qcloud.cos.model.ciModel.template;

import com.qcloud.cos.internal.CIServiceRequest;
import com.qcloud.cos.model.ciModel.job.*;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @descript 本类为模板实体类。 注释详情请参见 https://cloud.tencent.com/document/product/460/46989
 */
@XStreamAlias("Request")
public class MediaTemplateRequest extends CIServiceRequest implements Serializable {
    /**
     * 模板id
     */
    @XStreamAlias("TemplateId")
    private String templateId;

    /**
     * 动图模板 : Animation 截图模板Snapshot
     * 水印模板 : Watermark 截图模板Transcode
     */
    @XStreamAlias("Tag")
    private String tag;

    /**
     * 模板名称 仅支持中文、英文、数字、_、-和*
     */
    @XStreamAlias("Name")
    private String name;

    /**
     * 容器格式
     */
    @XStreamAlias("Container")
    private MediaContainerObject container;

    /**
     * 视频信息
     */
    @XStreamAlias("Video")
    private MediaVideoObject video;

    /**
     * 音频信息
     */
    @XStreamAlias("Audio")
    private MediaAudioObject audio;

    /**
     * 视频信息
     */
    @XStreamAlias("TransConfig")
    private MediaTransConfigObject transConfig;

    /**
     * 时间区间
     */
    @XStreamAlias("TimeInterval")
    private MediaTimeIntervalObject timeInterval;

    /**
     * 截图
     */
    @XStreamAlias("Snapshot")
    private MediaSnapshotObject snapshot;

    /**
     * 水印
     */
    @XStreamAlias("Watermark")
    private MediaWatermark watermark;

    /**
     * 拼接参数
     */
    @XStreamAlias("ConcatTemplate")
    private MediaConcatTemplateObject concat;

    @XStreamAlias("AudioMix")
    private MediaAudioMixObject audioMix;

    @XStreamAlias("AudioMixArray")
    private List<MediaAudioMixObject> audioMixArray;

    @XStreamAlias("VideoTargetRec")
    private VideoTargetRec videoTargetRec;

    @XStreamAlias("VideoEnhance")
    private VideoEnhance videoEnhance;

    /**
     * 第几页
     */
    @XStreamAlias("PageNumber")
    private String pageNumber;

    /**
     * 每页个数
     */
    @XStreamAlias("PageSize")
    private String pageSize;

    /**
     * Official，Custom，默认值：Custom
     */
    @XStreamAlias("Category")
    private String category;

    /**
     * 模板 ID，以,符号分割字符串
     */
    @XStreamAlias("Ids")
    private String ids;

    @XStreamAlias("Mode")
    private String mode;

    @XStreamAlias("Codec")
    private String codec;

    @XStreamAlias("VoiceType")
    private String voiceType;

    @XStreamAlias("Volume")
    private String volume;

    @XStreamAlias("Speed")
    private String speed;

    /**
     * 精彩集锦场景
     * 取值范围：Soccer/Video，默认值为Video
     */
    @XStreamAlias("Scene")
    private String scene;

    /**
     * 1. 默认自动分析时长
     * 2. 单位为秒
     * 3. 支持 float 格式，执行精度精确到毫秒
     */
    @XStreamAlias("Duration")
    private String duration;
    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

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

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MediaContainerObject getContainer() {
        if (container == null) container = new MediaContainerObject();
        return container;
    }

    public void setContainer(MediaContainerObject container) {
        this.container = container;
    }

    public MediaTimeIntervalObject getTimeInterval() {
        if (timeInterval == null) timeInterval = new MediaTimeIntervalObject();
        return timeInterval;
    }

    public void setTimeInterval(MediaTimeIntervalObject timeInterval) {
        this.timeInterval = timeInterval;
    }

    public MediaVideoObject getVideo() {
        if (video == null) video = new MediaVideoObject();
        return video;
    }

    public void setVideo(MediaVideoObject video) {
        this.video = video;
    }

    public MediaSnapshotObject getSnapshot() {
        if (snapshot == null) snapshot = new MediaSnapshotObject();
        return snapshot;
    }

    public void setSnapshot(MediaSnapshotObject snapshot) {
        this.snapshot = snapshot;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public MediaAudioObject getAudio() {
        if (audio == null) {
            audio = new MediaAudioObject();
        }
        return audio;
    }

    public void setAudio(MediaAudioObject audio) {
        this.audio = audio;
    }

    public MediaTransConfigObject getTransConfig() {
        if (transConfig == null) {
            transConfig = new MediaTransConfigObject();
        }
        return transConfig;
    }

    public void setTransConfig(MediaTransConfigObject transConfig) {
        this.transConfig = transConfig;
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

    public MediaConcatTemplateObject getConcat() {
        if (concat == null) {
            concat = new MediaConcatTemplateObject();
        }
        return concat;
    }

    public void setConcat(MediaConcatTemplateObject concat) {
        this.concat = concat;
    }

    public MediaAudioMixObject getAudioMix() {
        if (audioMix == null) {
            audioMix = new MediaAudioMixObject();
        }
        return audioMix;
    }

    public void setAudioMix(MediaAudioMixObject audioMix) {
        this.audioMix = audioMix;
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

    public VideoEnhance getVideoEnhance() {
        if (videoEnhance == null) {
            videoEnhance = new VideoEnhance();
        }
        return videoEnhance;
    }

    public void setVideoEnhance(VideoEnhance videoEnhance) {
        this.videoEnhance = videoEnhance;
    }

    public List<MediaAudioMixObject> getAudioMixArray() {
        if (audioMixArray == null) {
            audioMixArray = new ArrayList<>();
        }
        return audioMixArray;
    }

    public void setAudioMixArray(List<MediaAudioMixObject> audioMixArray) {
        this.audioMixArray = audioMixArray;
    }

    @Override
    public String toString() {
        return "MediaTemplateRequest{" + "templateId='" + templateId + '\'' + ", tag='" + tag + '\'' + ", name='" + name + '\'' + ", container=" + container + ", video=" + video + ", audio=" + audio + ", transConfig=" + transConfig + ", timeInterval=" + timeInterval + ", snapshot=" + snapshot + ", waterMark=" + watermark + ", pageNumber='" + pageNumber + '\'' + ", pageSize='" + pageSize + '\'' + ", category='" + category + '\'' + ", ids='" + ids + '\'' + '}';
    }
}
