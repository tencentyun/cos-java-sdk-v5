package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;


public class IngestStatusResponse extends CiServiceResult {

    /**
     *任务状态，例如 Success、Failed。
     */
    private String status;

    /**
     *任务详情。
     */
    private JobsDetail jobsDetail;

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public JobsDetail getJobsDetail() { return jobsDetail; }

    public void setJobsDetail(JobsDetail jobsDetail) { this.jobsDetail = jobsDetail; }


    public static class JobsDetail {
        /**
         *任务结果错误码，Success 表示成功，其他表示失败。
         */
        private String code;

        /**
         *任务结果描述信息。
         */
        private String message;

        public String getCode() { return code; }

        public void setCode(String code) { this.code = code; }

        public String getMessage() { return message; }

        public void setMessage(String message) { this.message = message; }

    }

}
