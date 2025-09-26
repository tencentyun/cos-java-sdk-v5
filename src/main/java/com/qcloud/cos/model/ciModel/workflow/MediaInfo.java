
package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("MediaInfo")
public class MediaInfo {
    @XStreamAlias("Video")
    private VideoInfo video;

    @XStreamAlias("Audio")
    private AudioInfo audio;

    @XStreamAlias("Format")
    private FormatInfo format;

    public VideoInfo getVideo() {
        if (video == null) {
            video = new VideoInfo();
        }
        return video;
    }

    public void setVideo(VideoInfo video) {
        this.video = video;
    }

    public AudioInfo getAudio() {
        if (audio == null) {
            audio = new AudioInfo();
        }
        return audio;
    }

    public void setAudio(AudioInfo audio) {
        this.audio = audio;
    }

    public FormatInfo getFormat() {
        if (format == null) {
            format = new FormatInfo();
        }
        return format;
    }

    public void setFormat(FormatInfo format) {
        this.format = format;
    }
}