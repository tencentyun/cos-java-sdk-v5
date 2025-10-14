
package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 文档AIGC元数据处理任务操作实体
 * https://cloud.tencent.com/document/product/460/123076
 */
public class DocAIGCMetadataOperation implements Serializable {
    
    /**
     * 当 Tag 为 DocAIGCMetadata 时有效，用于指定文档 AIGC 标识内容的参数
     */
    @XStreamAlias("DocAIGCMetadata")
    private DocAIGCMetadataConfig docAIGCMetadata;
    
    /**
     * 结果输出地址
     */
    @XStreamAlias("Output")
    private DocAIGCMetadataOutput output;
    
    /**
     * 透传用户信息，同 CreateMediaJobs 接口中的 Request.Operation.UserData
     */
    @XStreamAlias("UserData")
    private String userData;
    
    /**
     * 任务优先级，级别限制：0 、1 、2。级别越大任务优先级越高，默认为0
     */
    @XStreamAlias("JobLevel")
    private String jobLevel;

    public DocAIGCMetadataOperation() {
        this.docAIGCMetadata = new DocAIGCMetadataConfig();
        this.output = new DocAIGCMetadataOutput();
    }

    public DocAIGCMetadataConfig getDocAIGCMetadata() {
        if (docAIGCMetadata == null) {
            docAIGCMetadata = new DocAIGCMetadataConfig();
        }
        return docAIGCMetadata;
    }

    public void setDocAIGCMetadata(DocAIGCMetadataConfig docAIGCMetadata) {
        this.docAIGCMetadata = docAIGCMetadata;
    }

    public DocAIGCMetadataOutput getOutput() {
        if (output == null) {
            output = new DocAIGCMetadataOutput();
        }
        return output;
    }

    public void setOutput(DocAIGCMetadataOutput output) {
        this.output = output;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    @Override
    public String toString() {
        return "DocAIGCMetadataOperation{" +
                "docAIGCMetadata=" + docAIGCMetadata +
                ", output=" + output +
                ", userData='" + userData + '\'' +
                ", jobLevel='" + jobLevel + '\'' +
                '}';
    }
}