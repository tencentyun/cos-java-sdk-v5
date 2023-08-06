package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class SoundHoundResult {
    @XStreamImplicit(itemFieldName = "SongList")
    private List<Song> songList;

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    private class Song {
        private String inlier;
        private String singerName;
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
}
