package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaSong {
    @XStreamAlias("Inlier")
    private String inlier;
    @XStreamAlias("SingerName")
    private String singerName;
    @XStreamAlias("SongName")
    private String songName;

    public String getInlier() {
        return inlier;
    }

    public void setInlier(String inlier) {
        this.inlier = inlier;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
