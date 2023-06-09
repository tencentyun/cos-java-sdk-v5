package com.qcloud.cos.model.ciModel.auditing;


import com.qcloud.cos.internal.CIServiceRequest;
import com.qcloud.cos.model.ciModel.auditing.StorageConf;

import java.util.List;
import java.util.Map;

/**
 * 视频审核请求实体 参数详情参考：https://cloud.tencent.com/document/product/460/46427
 */
public class VideoAuditingRequest extends CIServiceRequest {
    /**
     * 审核类型，拥有 porn（涉黄识别）、terrorist（涉暴恐识别）、politics（涉政识别）、ads（广告识别）四种。用户可选择多种识别类型，
     * 例如 detectType=porn,ads 表示对图片进行涉黄及广告审核
     */
    private Conf conf;
    private StorageConf storageConf;

    private AuditingInputObject input;

    private String jobId;

    private String type;

    @Override
    public Map<String, List<String>> getCustomQueryParameters() {
        return super.getCustomQueryParameters();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public AuditingInputObject getInput() {
        if (input == null) {
            input = new AuditingInputObject();
        }
        return input;
    }

    public void setInput(AuditingInputObject input) {
        this.input = input;
    }

    public Conf getConf() {
        if (conf == null) {
            conf = new Conf();
        }
        return conf;
    }

    public void setConf(Conf conf) {
        this.conf = conf;
    }

    public StorageConf getStorageConf() {
        if (storageConf == null) {
            storageConf = new StorageConf();
        }
        return storageConf;
    }

    public void setStorageConf(StorageConf storageConf) {
        this.storageConf = storageConf;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VideoAuditingRequest{");
        sb.append("conf=").append(conf);
        sb.append(", storageConf=").append(storageConf);
        sb.append(", input=").append(input);
        sb.append(", jobId='").append(jobId).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
