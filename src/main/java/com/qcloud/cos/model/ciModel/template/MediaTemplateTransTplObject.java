package com.qcloud.cos.model.ciModel.template;

import com.qcloud.cos.model.ciModel.job.*;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @descript 媒体模板响应实体类。 注释详情请参见 https://cloud.tencent.com/document/product/460/46989
 */
public class MediaTemplateTransTplObject  {
    /**
     * 容器格式
     */
    @XStreamAlias("Container")
    private MediaContainerObject container = new MediaContainerObject();
    /**
     * 视频信息
     */
    @XStreamAlias("Video")
    private MediaVideoObject video = new MediaVideoObject();
    /**
     * 时间区间
     */
    @XStreamAlias("TimeInterval")
    private MediaTimeIntervalObject timeInterval = new MediaTimeIntervalObject();

    /**
     * 时间区间
     */
    @XStreamAlias("Audio")
    private MediaAudioObject audio = new MediaAudioObject();

    /**
     * 截图
     */
    @XStreamAlias("Snapshot")
    private MediaSnapshotObject snapshot;

    @XStreamAlias("TransConfig")
    private MediaTransConfigObject transConfig;

    public MediaContainerObject getContainer() {
        return container;
    }

    public void setContainer(MediaContainerObject container) {
        this.container = container;
    }

    public MediaVideoObject getVideo() {
        return video;
    }

    public void setVideo(MediaVideoObject video) {
        this.video = video;
    }

    public MediaTimeIntervalObject getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(MediaTimeIntervalObject timeInterval) {
        this.timeInterval = timeInterval;
    }

    public MediaSnapshotObject getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(MediaSnapshotObject snapshot) {
        this.snapshot = snapshot;
    }

    public MediaAudioObject getAudio() {
        return audio;
    }

    public void setAudio(MediaAudioObject audio) {
        this.audio = audio;
    }

    public MediaTransConfigObject getTransConfig() {
        return transConfig;
    }

    public void setTransConfig(MediaTransConfigObject transConfig) {
        this.transConfig = transConfig;
    }

    @Override
    public String toString() {
        return "MediaTemplateTransTplObject{" +
                "container=" + container +
                ", video=" + video +
                ", timeInterval=" + timeInterval +
                ", audio=" + audio +
                ", snapshot=" + snapshot +
                '}';
    }
}
