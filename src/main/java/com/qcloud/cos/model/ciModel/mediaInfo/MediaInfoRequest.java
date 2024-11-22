package com.qcloud.cos.model.ciModel.mediaInfo;

import com.qcloud.cos.internal.CIServiceRequest;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 媒体信息接口 请求实体
 */
@XStreamAlias("Request")
public class MediaInfoRequest extends CIServiceRequest implements Serializable {
    @XStreamAlias("BucketName")
    private String bucketName;
    @XStreamAlias("Input")
    private MediaInputObject input;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public MediaInputObject getInput() {
        if (input==null)
            input = new MediaInputObject();
        return input;
    }

    public void setInput(MediaInputObject input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "MediaInfoRequest{" +
                "input=" + input +
                '}';
    }
}
