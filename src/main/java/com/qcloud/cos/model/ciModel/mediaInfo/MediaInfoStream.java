package com.qcloud.cos.model.ciModel.mediaInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * MediaInfo 格式详情实体类 详情见：https://cloud.tencent.com/document/product/460/38935
 */
public class MediaInfoStream {
    /**
     * 视频信息
     */
    @XStreamImplicit(itemFieldName = "Video")
    private List<MediaInfoVideo> mediaInfoVideoList;
    /**
     * 音频信息
     */
    @XStreamImplicit(itemFieldName = "Audio")
    private List<MediaInfoAudio> mediaInfoAudioList;
    /**
     * 字幕信息
     */
    @XStreamAlias("Subtitle")
    private MediaInfoSubtitle subtitle;

    public List<MediaInfoVideo> getMediaInfoVideoList() {
        if (mediaInfoVideoList == null) {
            mediaInfoVideoList = new ArrayList<>();
        }
        return mediaInfoVideoList;
    }

    public void setMediaInfoVideoList(List<MediaInfoVideo> mediaInfoVideoList) {
        this.mediaInfoVideoList = mediaInfoVideoList;
    }

    public List<MediaInfoAudio> getMediaInfoAudioList() {
        if (mediaInfoAudioList == null) {
            mediaInfoAudioList = new ArrayList<>();
        }
        return mediaInfoAudioList;
    }

    public void setMediaInfoAudioList(List<MediaInfoAudio> mediaInfoAudioList) {
        this.mediaInfoAudioList = mediaInfoAudioList;
    }

    public MediaInfoSubtitle getSubtitle() {
        if (subtitle == null) {
            subtitle = new MediaInfoSubtitle();
        }
        return subtitle;
    }

    public void setSubtitle(MediaInfoSubtitle subtitle) {
        this.subtitle = subtitle;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MediaStream{");
        sb.append("mediaInfoVideoList=").append(mediaInfoVideoList);
        sb.append(", mediaInfoAudioList=").append(mediaInfoAudioList);
        sb.append(", subtitle=").append(subtitle);
        sb.append('}');
        return sb.toString();
    }
}
