package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 媒体处理拼接模板实体类
 */
public class MediaConcatTemplateObject {
    @XStreamImplicit(itemFieldName = "ConcatFragment")
    private List<MediaConcatFragmentObject> concatFragmentList;

    @XStreamAlias("Audio")
    private MediaAudioObject audio;

    @XStreamAlias("Video")
    private MediaVideoObject video;

    @XStreamAlias("Container")
    private MediaContainerObject container;

    @XStreamAlias("AudioMix")
    private MediaAudioMixObject audioMix;

    @XStreamImplicit(itemFieldName = "AudioMixArray")
    private List<MediaAudioMixObject> audioMixArray;

    @XStreamAlias("Index")
    private String index;

    @XStreamAlias("SceneChangeInfo")
    private SceneChangeInfo sceneChangeInfo;
    /**
     * 简单拼接方式（不转码直接拼接），其他的视频和音频参数失效 true、false
     * 默认为false
     */
    @XStreamAlias("DirectConcat")
    private String directConcat;

    public List<MediaConcatFragmentObject> getConcatFragmentList() {
        if (concatFragmentList == null) {
            concatFragmentList = new ArrayList<>();
        }
        return concatFragmentList;
    }

    public void setConcatFragmentList(List<MediaConcatFragmentObject> concatFragmentList) {
        this.concatFragmentList = concatFragmentList;
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

    public MediaVideoObject getVideo() {
        if (video == null) {
            video = new MediaVideoObject();
        }
        return video;
    }

    public void setVideo(MediaVideoObject video) {
        this.video = video;
    }

    public MediaContainerObject getContainer() {
        if (container == null) {
            container = new MediaContainerObject();
        }
        return container;
    }

    public void setContainer(MediaContainerObject container) {
        this.container = container;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDirectConcat() {
        return directConcat;
    }

    public void setDirectConcat(String directConcat) {
        this.directConcat = directConcat;
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
        if (audioMixArray == null){
            return audioMixArray = new ArrayList<>();
        }
        return audioMixArray;
    }

    public void setAudioMixArray(List<MediaAudioMixObject> audioMixArray) {
        this.audioMixArray = audioMixArray;
    }

    public SceneChangeInfo getSceneChangeInfo() {
        if (sceneChangeInfo == null) {
            sceneChangeInfo = new SceneChangeInfo();
        }
        return sceneChangeInfo;
    }

    public void setSceneChangeInfo(SceneChangeInfo sceneChangeInfo) {
        this.sceneChangeInfo = sceneChangeInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaConcatTemplateObject{");
        sb.append("concatFragmentList=").append(concatFragmentList);
        sb.append(", audio=").append(audio);
        sb.append(", video=").append(video);
        sb.append(", container=").append(container);
        sb.append(", audioMix=").append(audioMix);
        sb.append(", audioMixArray=").append(audioMixArray);
        sb.append(", index='").append(index).append('\'');
        sb.append(", sceneChangeInfo=").append(sceneChangeInfo);
        sb.append(", directConcat='").append(directConcat).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
