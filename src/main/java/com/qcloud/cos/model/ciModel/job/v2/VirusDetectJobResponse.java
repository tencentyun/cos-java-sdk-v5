
package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 * 病毒检测任务查询结果响应类
 * 用于查询已提交的病毒检测任务的详细结果
 */
@XStreamAlias("Response")
public class VirusDetectJobResponse extends CiServiceResult {

    /**
     * 病毒检测任务的详细信息
     */
    @XStreamAlias("JobsDetail")
    private VirusDetectJobDetail jobsDetail;

    public VirusDetectJobDetail getJobsDetail() {
        if (jobsDetail == null) {
            jobsDetail = new VirusDetectJobDetail();
        }
        return jobsDetail;
    }

    public void setJobsDetail(VirusDetectJobDetail jobsDetail) {
        this.jobsDetail = jobsDetail;
    }

    @Override
    public String toString() {
        return "VirusDetectJobResponse{" +
                "jobsDetail=" + jobsDetail +
                '}';
    }

    /**
     * 病毒检测任务详情类
     */
    @XStreamAlias("JobsDetail")
    public static class VirusDetectJobDetail {
        /**
         * 错误码，只有 State 为 Failed 时有意义
         */
        @XStreamAlias("Code")
        private String code;

        /**
         * 错误描述，只有 State 为 Failed 时有意义
         */
        @XStreamAlias("Message")
        private String message;

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
         * 检测的文件为存储在 COS 中的文件时，该字段表示本次检测的文件名称
         */
        @XStreamAlias("Object")
        private String object;

        /**
         * 检测的文件为一条文件链接时，该字段表示本次检测的文件链接
         */
        @XStreamAlias("Url")
        private String url;

        /**
         * 该字段表示本次判定的检测结果，您可以根据该结果，进行后续的操作
         * 有效值：normal（检测正常），block（检测到文件含有病毒）
         */
        @XStreamAlias("Suggestion")
        private String suggestion;

        /**
         * 本次检测的详细信息
         */
        @XStreamAlias("DetectDetail")
        private VirusDetectDetail detectDetail;

        // Getter and Setter methods
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }

        public VirusDetectDetail getDetectDetail() {
            if (detectDetail == null) {
                detectDetail = new VirusDetectDetail();
            }
            return detectDetail;
        }

        public void setDetectDetail(VirusDetectDetail detectDetail) {
            this.detectDetail = detectDetail;
        }

        @Override
        public String toString() {
            return "VirusDetectJobDetail{" +
                    "code='" + code + '\'' +
                    ", message='" + message + '\'' +
                    ", jobId='" + jobId + '\'' +
                    ", state='" + state + '\'' +
                    ", creationTime='" + creationTime + '\'' +
                    ", object='" + object + '\'' +
                    ", url='" + url + '\'' +
                    ", suggestion='" + suggestion + '\'' +
                    ", detectDetail=" + detectDetail +
                    '}';
        }
    }

    /**
     * 病毒检测详细信息类
     */
    @XStreamAlias("DetectDetail")
    public static class VirusDetectDetail {
        /**
         * 单个文件的检测结果，可能有多个结果
         * 使用@XStreamImplicit注解处理数组结构，而不是@XStreamAlias
         */
        @XStreamImplicit(itemFieldName = "Result")
        private List<VirusDetectResult> result;

        public List<VirusDetectResult> getResult() {
            return result;
        }

        public void setResult(List<VirusDetectResult> result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "VirusDetectDetail{" +
                    "result=" + result +
                    '}';
        }
    }

    /**
     * 病毒检测结果类
     */
    @XStreamAlias("Result")
    public static class VirusDetectResult {
        /**
         * 文件名称
         */
        @XStreamAlias("FileName")
        private String fileName;

        /**
         * 检测到的病毒名称
         */
        @XStreamAlias("VirusName")
        private String virusName;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getVirusName() {
            return virusName;
        }

        public void setVirusName(String virusName) {
            this.virusName = virusName;
        }

        @Override
        public String toString() {
            return "VirusDetectResult{" +
                    "fileName='" + fileName + '\'' +
                    ", virusName='" + virusName + '\'' +
                    '}';
        }
    }
}