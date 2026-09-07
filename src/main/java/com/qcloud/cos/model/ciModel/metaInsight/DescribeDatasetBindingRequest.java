package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;


public class DescribeDatasetBindingRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetname;

    /**
     * 资源标识字段，表示需要查询与数据集绑定关系的资源，当前仅支持 COS 存储桶，
     * 字段规则：{@code cos://<BucketName>}，其中 BucketName 表示 COS 存储桶名称，
     * 例如（需要进行 URL Encode）：{@code cos%3A%2F%2Fexample-1250000000}。
     * 是否必传：是
     */
    private String uri;

    public String getDatasetname() { return datasetname; }

    public void setDatasetname(String datasetname) { this.datasetname = datasetname; }

    public String getUri() { return uri; }

    public void setUri(String uri) { this.uri = uri; }



}