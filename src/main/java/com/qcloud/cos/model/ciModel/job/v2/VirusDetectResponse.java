package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public class VirusDetectResponse extends CiServiceResult  {

    /**
     * 病毒检测任务的详细信息
     */
    @XStreamAlias("JobsDetail")
    private VirusDetectJobsDetail jobsDetail;

    public VirusDetectJobsDetail getJobsDetail() {
        if (jobsDetail == null) {
            jobsDetail = new VirusDetectJobsDetail();
        }
        return jobsDetail;
    }

    public void setJobsDetail(VirusDetectJobsDetail jobsDetail) {
        this.jobsDetail = jobsDetail;
    }

    @Override
    public String toString() {
        return "VirusDetectResponse{" +
                "jobsDetail=" + jobsDetail +
                '}';
    }

    /**
     * 病毒检测任务详情类
     */
    @XStreamAlias("JobsDetail")
    public static class VirusDetectJobsDetail {
        /**
         * 本次病毒检测任务的 ID
         */
        @XStreamAlias("JobId")
        private String jobId;

        /**
         * 病毒检测任务的状态
         * 值为 Submitted（已提交检测）、Success（检测成功）、Failed（检测失败）、Auditing（检测中）其中一个
         */
        @XStreamAlias("State")
        private String state;

        /**
         * 病毒检测任务的创建时间
         */
        @XStreamAlias("CreationTime")
        private String creationTime;

        /**
         * 本次检测的文件名称
         */
        @XStreamAlias("Object")
        private String object;

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        @Override
        public String toString() {
            return "VirusDetectJobsDetail{" +
                    "jobId='" + jobId + '\'' +
                    ", state='" + state + '\'' +
                    ", creationTime='" + creationTime + '\'' +
                    ", object='" + object + '\'' +
                    '}';
        }
    }

}
