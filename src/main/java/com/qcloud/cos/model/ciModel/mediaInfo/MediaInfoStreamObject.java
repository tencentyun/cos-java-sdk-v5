package com.qcloud.cos.model.ciModel.mediaInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * MediaInfo 格式详情实体类 详情见：https://cloud.tencent.com/document/product/460/38935
 */
public class MediaInfoStreamObject implements Serializable {
    @XStreamAlias("Format")
    private MediaFormat format;
    @XStreamAlias("Stream")
    private MediaInfoStream stream;

    public MediaFormat getFormat() {
        if (format == null) {
            format = new MediaFormat();
        }
        return format;
    }

    public void setFormat(MediaFormat format) {
        this.format = format;
    }

    public MediaInfoStream getStream() {
        if (stream == null) {
            stream = new MediaInfoStream();
        }
        return stream;
    }

    public void setStream(MediaInfoStream stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        return "MediaInfoObjcet{" +
                "format=" + format +
                ", stream=" + stream +
                '}';
    }
}
