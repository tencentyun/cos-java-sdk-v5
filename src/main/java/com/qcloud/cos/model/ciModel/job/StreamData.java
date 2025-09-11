package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class StreamData {
    @XStreamAlias("Data")
    private VideoTagData data;
    @XStreamAlias("SubErrCode")
    private String subErrCode;
    @XStreamAlias("SubErrMsg")
    private String subErrMsg;

    public VideoTagData getData() {
        if (data == null) {
            data = new VideoTagData();
        }
        return data;
    }

    public void setData(VideoTagData data) {
        this.data = data;
    }

    public String getSubErrCode() {
        return subErrCode;
    }

    public void setSubErrCode(String subErrCode) {
        this.subErrCode = subErrCode;
    }

    public String getSubErrMsg() {
        return subErrMsg;
    }

    public void setSubErrMsg(String subErrMsg) {
        this.subErrMsg = subErrMsg;
    }
}
