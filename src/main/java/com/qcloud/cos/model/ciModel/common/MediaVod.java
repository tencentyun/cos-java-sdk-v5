package com.qcloud.cos.model.ciModel.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaVod {
    @XStreamAlias("FileId")
    private String fileId;
    @XStreamAlias("SubAppId")
    private String subAppId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }
}
