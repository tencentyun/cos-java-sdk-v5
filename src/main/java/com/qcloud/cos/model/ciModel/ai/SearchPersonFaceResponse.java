package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.model.CosServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("Response")
public class SearchPersonFaceResponse extends CosServiceResult {

    /**
     *请求的唯一 ID
     */
    @XStreamAlias("RequestId")
    private String requestId;

    /**
     *搜索的人员库中包含的人脸数。
示例值：1
     */
    @XStreamAlias("FaceNum")
    private Integer faceNum;

    /**
     *图片信息
     */
    @XStreamAlias("Results")
    private Results results;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public Integer getFaceNum() { return faceNum; }

    public void setFaceNum(Integer faceNum) { this.faceNum = faceNum; }

    public Results getResults() { return results; }

    public void setResults(Results results) { this.results = results; }

    
}
