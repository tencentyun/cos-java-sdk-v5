package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;


public class DescribeFileMetaIndexResponse extends CiServiceResult {

    /**
     *请求ID。
     */
    private String requestId;

    /**
     *文件元数据的结构体。实际返回的数据可能并不包含该结构体的所有属性，这和您索引该文件时选用的工作流模板配置以及文件本身的内容有关。
     */
    private List<File> files;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public List<File> getFiles() { return files; }

    public void setFiles(List<File> files) { this.files = files; }

    
}
