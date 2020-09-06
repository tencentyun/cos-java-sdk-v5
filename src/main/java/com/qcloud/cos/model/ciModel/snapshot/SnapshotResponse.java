package com.qcloud.cos.model.ciModel.snapshot;

import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 媒体截图请求实体  详情见：https://cloud.tencent.com/document/product/460/38934
 */
public class SnapshotResponse implements Serializable {
    /**
     * 截图保存的位置信息
     */
    @XStreamAlias("Output")
    private MediaOutputObject output;

    public MediaOutputObject getOutput() {
        if (output==null)
            this.output = new MediaOutputObject();
        return output;
    }

    public void setOutput(MediaOutputObject output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "SnapshotResponse{" +
                "output=" + output +
                '}';
    }
}
