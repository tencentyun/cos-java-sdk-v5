package com.qcloud.cos.model.ciModel.mediaInfo;

import com.qcloud.cos.internal.CIServiceRequest;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 媒体信息接口 请求实体
 */
public class MediaInfoRequest extends CIServiceRequest implements Serializable {
    @XStreamAlias("Input")
    private MediaInputObject input;

    public MediaInputObject getInput() {
        if (input==null)
            input = new MediaInputObject();
        return input;
    }

    public void setInput(MediaInputObject input) {
        this.input = input;
    }
}
