package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 媒体处理 任务列表响应实体 https://cloud.tencent.com/document/product/460/48234
 */
@XStreamAlias("Response")
public class FileProcessJobResponse extends CiServiceResult {

    @XStreamAlias("JobsDetail")
    private FileProcessJobDetail jobDetail;

    public FileProcessJobDetail getJobDetail() {
        if (jobDetail == null) {
            jobDetail = new FileProcessJobDetail();
        }
        return jobDetail;
    }

    public void setJobDetail(FileProcessJobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }
}


