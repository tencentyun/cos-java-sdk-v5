package com.qcloud.cos.model.ciModel.hls;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public class UpdataHLSPlayKeyResponse extends CiServiceResult {

    /**
     *请求的唯一 ID
     */
    @XStreamAlias("RequestId")
    private String requestId;

    /**
     *保存播放密钥的容器
     */
    @XStreamAlias("PlayKeyList")
    private PlayKeyList playKeyList;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public PlayKeyList getPlayKeyList() { return playKeyList; }

    public void setPlayKeyList(PlayKeyList playKeyList) { this.playKeyList = playKeyList; }

    
}
