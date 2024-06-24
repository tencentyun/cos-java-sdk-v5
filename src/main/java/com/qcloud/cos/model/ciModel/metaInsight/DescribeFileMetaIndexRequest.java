package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qcloud.cos.internal.CIServiceRequest;


public class DescribeFileMetaIndexRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetname;

    /**
     *资源标识字段，表示需要建立索引的文件地址，当前仅支持COS上的文件，字段规则：cos:///，其中BucketName表示COS存储桶名称，ObjectKey表示文件完整路径，例如：cos://examplebucket-1250000000/test1/img.jpg。 注意： 1、仅支持本账号内的COS文件 2、不支持HTTP开头的地址 3、需UrlEncode;是否必传：是
     */
    private String uri;

    public String getDatasetname() { return datasetname; }

    public void setDatasetname(String datasetname) { this.datasetname = datasetname; }

    public String getUri() { return uri; }

    public void setUri(String uri) { this.uri = uri; }



}
