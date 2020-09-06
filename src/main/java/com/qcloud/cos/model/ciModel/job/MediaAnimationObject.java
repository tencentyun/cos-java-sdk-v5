package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaAnimationObject {
    @XStreamAlias("Container")
    private MediaContainerObject container;
    @XStreamAlias("Video")
    private MediaVideoObject video;
    @XStreamAlias("TimeInterval")
    private MediaTimeIntervalObject timeInterval;

    public MediaContainerObject getContainer() {
        if (container==null){
            container= new MediaContainerObject();
        }
        return container;
    }

    public void setContainer(MediaContainerObject container) {
        this.container = container;
    }

    public MediaVideoObject getVideo() {
        if (video==null){
            video= new MediaVideoObject();
        }
        return video;
    }

    public void setVideo(MediaVideoObject video) {
        this.video = video;
    }

    public MediaTimeIntervalObject getTimeInterval() {
        if (timeInterval==null){
            timeInterval= new MediaTimeIntervalObject();
        }
        return timeInterval;
    }

    public void setTimeInterval(MediaTimeIntervalObject timeInterval) {
        this.timeInterval = timeInterval;
    }
}
