package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qcloud.cos.internal.CIServiceRequest;


public class MediaFaceClipSearchRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetName;

    /**
     *需要进行人脸精确定位的媒资文件地址，取值为人脸粗搜（MediaFaceSearch）返回的 UriList 中的某一内部 URI，需包含完整的 COS 路径。;是否必传：是
     */
    @JsonProperty("URI")
    private String uRI;

    /**
     *人脸 ID，取值为人脸粗搜（MediaFaceSearch）返回的人脸 ID，用于在指定 URI 内部进行精确匹配。;是否必传：是
     */
    private String faceId;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    @JsonProperty("URI")
    public String getURI() { return uRI; }

    @JsonProperty("URI")
    public void setURI(String uRI) { this.uRI = uRI; }

    public String getFaceId() { return faceId; }

    public void setFaceId(String faceId) { this.faceId = faceId; }

}
