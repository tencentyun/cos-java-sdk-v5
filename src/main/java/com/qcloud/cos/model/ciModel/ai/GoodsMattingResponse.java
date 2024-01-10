package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.model.CosServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.InputStream;


@XStreamAlias("Response")
public class GoodsMattingResponse extends CosServiceResult {
    private InputStream content;

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }
}
