package com.qcloud.cos.model.ciModel.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 输入文件在cos中的位置
 * 例 cos根目录下的1.txt文件  则object = 1.txt
 * cos根目录下test文件夹中的1.txt文件 object = test/1.txt
 */
public class MediaInputObject {
    @XStreamAlias("Object")
    private String object;
    @XStreamAlias("Url")
    private String url;
    @XStreamAlias("Vod")
    private MediaVod vod;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MediaVod getVod() {
        if (vod == null) {
            vod = new MediaVod();
        }
        return vod;
    }

    public void setVod(MediaVod vod) {
        this.vod = vod;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaInputObject{");
        sb.append("object='").append(object).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", vod=").append(vod);
        sb.append('}');
        return sb.toString();
    }
}
