package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;


public class CreateDatasetBindingRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetName;

    /**
     *资源标识字段，表示需要与数据集绑定的资源，当前仅支持COS存储桶，字段规则：cos://<BucketName>，其中BucketName表示COS存储桶名称，例如：cos://examplebucket-1250000000;是否必传：是
     */
    @JsonProperty("URI")
    private String uRI;

    /**
     * 建立绑定后，以何种方式进行文件索引特征的提取，有效值：0，表示仅对增量文件建立索引；
     * 1，表示在对增量文件建立索引的同时，也会对存储桶中存量文件建立索引，存量文件索引建立的时长与存量文件数有关；默认值为0。
     */
    @JsonProperty("Mode")
    private String mode;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public String getURI() { return uRI; }

    public void setURI(String uRI) { this.uRI = uRI; }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
