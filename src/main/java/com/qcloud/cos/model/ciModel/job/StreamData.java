package com.qcloud.cos.model.ciModel.job;

public class StreamData {
    private VideoTagData data;
    private String subErrCode;
    private String subErrMsg;

    public VideoTagData getData() {
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
