package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class SoundHoundResult {
    @XStreamImplicit(itemFieldName = "SongList")
    private List<MediaSong> songList;

    public List<MediaSong> getSongList() {
        return songList;
    }

    public void setSongList(List<MediaSong> songList) {
        this.songList = songList;
    }

}
