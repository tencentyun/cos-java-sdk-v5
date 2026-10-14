package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qcloud.cos.internal.CIServiceRequest;


public class MediaFaceSearchRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetName;

    /**
     *用于人脸查找的外部人脸图片地址，需包含 COS 完整路径。格式为 cos://<BucketName>/<Path>。;是否必传：是
     */
    @JsonProperty("URI")
    private String uRI;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    @JsonProperty("URI")
    public String getURI() { return uRI; }

    @JsonProperty("URI")
    public void setURI(String uRI) { this.uRI = uRI; }

}
