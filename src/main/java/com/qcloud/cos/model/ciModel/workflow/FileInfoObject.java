
package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("FileInfo")
public class FileInfoObject {
    @XStreamAlias("BasicInfo")
    private BasicInfo basicInfo;

    @XStreamAlias("MediaInfo")
    private MediaInfo mediaInfo;

    @XStreamAlias("ImageInfo")
    private ImageInfo imageInfo;

    public BasicInfo getBasicInfo() {
        if (basicInfo == null) {
            basicInfo = new BasicInfo();
        }
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public MediaInfo getMediaInfo() {
        if (mediaInfo == null) {
            mediaInfo = new MediaInfo();
        }
        return mediaInfo;
    }

    public void setMediaInfo(MediaInfo mediaInfo) {
        this.mediaInfo = mediaInfo;
    }

    public ImageInfo getImageInfo() {
        if (imageInfo == null) {
            imageInfo = new ImageInfo();
        }
        return imageInfo;
    }

    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    @Override
    public String toString() {
        return "FileInfoObject{" +
                "basicInfo=" + basicInfo +
                ", mediaInfo=" + mediaInfo +
                ", imageInfo=" + imageInfo +
                '}';
    }
}