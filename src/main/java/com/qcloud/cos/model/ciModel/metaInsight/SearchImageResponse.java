package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;


public class SearchImageResponse extends CiServiceResult {

    /**
     *图像检索识别结果信息列表。
     */
    private List<ImageResult> imageResult;

    /**
     *请求ID。
     */
    private String requestId;

    public List<ImageResult> getImageResult() { return imageResult; }

    public void setImageResult(List<ImageResult> imageResult) { this.imageResult = imageResult; }

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    
}
