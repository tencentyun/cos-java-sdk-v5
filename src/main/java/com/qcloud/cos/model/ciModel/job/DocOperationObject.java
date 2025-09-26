package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.common.MediaOutputObject;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.List;

/**
 * 文档预览任务规则实体 https://cloud.tencent.com/document/product/460/46942
 */
public class DocOperationObject {

    /**
     * 文件的输出地址 (cos桶相对的输出路径)
     */
    private MediaOutputObject output;
    /**
     * 文档预览任务参数
     */
    private DocProcessObject docProcessObject;

    private DocWatermark docWatermarkObject;

    /**
     * 文档预览任务参数
     */
    private DocProcessResult docProcessResult;

    private String UserData;

    public MediaOutputObject getOutput() {
        if (output == null) {
            output = new MediaOutputObject();
        }
        return output;
    }

    public void setOutput(MediaOutputObject output) {
        this.output = output;
    }

    public DocProcessObject getDocProcessObject() {
        if (docProcessObject == null) {
            docProcessObject = new DocProcessObject();
        }
        return docProcessObject;
    }

    public void setDocProcessObject(DocProcessObject docProcessObject) {
        this.docProcessObject = docProcessObject;
    }

    public DocProcessResult getDocProcessResult() {
        if (docProcessResult == null) {
            docProcessResult = new DocProcessResult();
        }
        return docProcessResult;
    }

    public void setDocProcessResult(DocProcessResult docProcessResult) {
        this.docProcessResult = docProcessResult;
    }

    public String getUserData() {
        return UserData;
    }

    public void setUserData(String userData) {
        UserData = userData;
    }

    public DocWatermark getDocWatermarkObject() {
        if (docWatermarkObject == null) {
            docWatermarkObject = new DocWatermark();
        }
        return docWatermarkObject;
    }
    public void setDocWatermarkObject(DocWatermark docWatermarkObject) {
        this.docWatermarkObject = docWatermarkObject;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DocOperationObject{");
        sb.append("output=").append(output);
        sb.append(", docProcessObject=").append(docProcessObject);
        sb.append(", docWatermarkObject=").append(docWatermarkObject);
        sb.append(", docProcessResult=").append(docProcessResult);
        sb.append(", UserData='").append(UserData).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
