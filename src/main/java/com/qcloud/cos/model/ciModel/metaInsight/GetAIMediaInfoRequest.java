package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qcloud.cos.internal.CIServiceRequest;


public class GetAIMediaInfoRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetName;

    /**
     *对象文件名，需包含 COS 完整路径。格式为 cos://<BucketName>/<Path>，例如 cos://examplebucket-1250000000/test.mp4。;是否必传：是
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
