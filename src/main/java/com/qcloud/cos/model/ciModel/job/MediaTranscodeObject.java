package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 媒体处理 任务转码实体 https://cloud.tencent.com/document/product/460/48234
 */
public class MediaTranscodeObject extends MediaVideoCommon {
    /**
     * 容器格式	例:mp4
     */
    @XStreamAlias("Container")
    private MediaContainerObject container;
    /**
     * 视频信息	不传 Video，相当于删除视频信息
     */
    @XStreamAlias("Video")
    private MediaTranscodeVideoObject video;
    /**
     * 音频信息
     */
    @XStreamAlias("Audio")
    private MediaAudioObject audio;
    /**
     * 转码配置
     */
    @XStreamAlias("TransConfig")
    private MediaTransConfigObject transConfig;
    /**
     * 时间区间
     */
    @XStreamAlias("TimeInterval")
    private MediaTimeIntervalObject timeInterval;

    /**
     * 混音配置
     */
    @XStreamAlias("AudioMix")
    private MediaAudioMixObject audioMix;

    @XStreamImplicit(itemFieldName = "AudioMix")
    private List<MediaAudioMixObject> audioMixArray;

    public MediaContainerObject getContainer() {
        if (container == null) {
            container = new MediaContainerObject();
        }
        return container;
    }

    public void setContainer(MediaContainerObject container) {
        this.container = container;
    }

    public MediaTranscodeVideoObject getVideo() {
        if (video == null) {
            video = new MediaTranscodeVideoObject();
        }
        return video;
    }

    public void setVideo(MediaTranscodeVideoObject video) {
        this.video = video;
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

    public MediaTimeIntervalObject getTimeInterval() {
        if (timeInterval == null) {
            timeInterval = new MediaTimeIntervalObject();
        }
        return timeInterval;
    }

    public void setTimeInterval(MediaTimeIntervalObject timeInterval) {
        this.timeInterval = timeInterval;
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
        final StringBuffer sb = new StringBuffer("MediaTranscodeObject{");
        sb.append("container=").append(container);
        sb.append(", video=").append(video);
        sb.append(", audio=").append(audio);
        sb.append(", transConfig=").append(transConfig);
        sb.append(", timeInterval=").append(timeInterval);
        sb.append('}');
        return sb.toString();
    }
}
