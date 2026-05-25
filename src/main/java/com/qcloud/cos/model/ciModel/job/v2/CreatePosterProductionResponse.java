package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

@XStreamAlias("Response")
public class CreatePosterProductionResponse extends CiServiceResult {

    /**
     *任务的详细信息
     */
    @XStreamAlias("JobsDetail")
    @JsonProperty("JobsDetail")
    private PosterProductionJobsDetail jobsDetail;

    public PosterProductionJobsDetail getJobsDetail() { return jobsDetail; }

    public void setJobsDetail(PosterProductionJobsDetail jobsDetail) { this.jobsDetail = jobsDetail; }

    
    @XStreamAlias("PosterProductionJobsDetail")
    public class PosterProductionJobsDetail {
        /**
         *错误码，只有 State 为 Failed 时有意义
         */
        @XStreamAlias("Code")
        @JsonProperty("Code")
        private String code;

        /**
         *错误描述，只有 State 为 Failed 时有意义
         */
        @XStreamAlias("Message")
        @JsonProperty("Message")
        private String message;

        /**
         *新创建任务的 ID
         */
        @XStreamAlias("JobId")
        @JsonProperty("JobId")
        private String jobId;

        /**
         *新创建任务的 Tag：PicProcess
         */
        @XStreamAlias("Tag")
        @JsonProperty("Tag")
        private String tag;

        /**
         *任务的状态，为 Submitted、Running、Success、Failed、Pause、Cancel 其中一个
         */
        @XStreamAlias("State")
        @JsonProperty("State")
        private String state;

        /**
         *任务的创建时间
         */
        @XStreamAlias("CreationTime")
        @JsonProperty("CreationTime")
        private String creationTime;

        /**
         *任务的开始时间
         */
        @XStreamAlias("StartTime")
        @JsonProperty("StartTime")
        private String startTime;

        /**
         *任务的结束时间
         */
        @XStreamAlias("EndTime")
        @JsonProperty("EndTime")
        private String endTime;

        /**
         *任务所属的队列 ID
         */
        @XStreamAlias("QueueId")
        @JsonProperty("QueueId")
        private String queueId;

        /**
         *任务所属的队列类型
         */
        @XStreamAlias("QueueType")
        @JsonProperty("QueueType")
        private String queueType;

        /**
         *该任务的输入资源地址
         */
        @XStreamAlias("Input")
        @JsonProperty("Input")
        private Input input;

        /**
         *该任务的规则
         */
        @XStreamAlias("Operation")
        @JsonProperty("Operation")
        private PosterProductionResponseOperation operation;

        public String getCode() { return code; }

        public void setCode(String code) { this.code = code; }

        public String getMessage() { return message; }

        public void setMessage(String message) { this.message = message; }

        public String getJobId() { return jobId; }

        public void setJobId(String jobId) { this.jobId = jobId; }

        public String getTag() { return tag; }

        public void setTag(String tag) { this.tag = tag; }

        public String getState() { return state; }

        public void setState(String state) { this.state = state; }

        public String getCreationTime() { return creationTime; }

        public void setCreationTime(String creationTime) { this.creationTime = creationTime; }

        public String getStartTime() { return startTime; }

        public void setStartTime(String startTime) { this.startTime = startTime; }

        public String getEndTime() { return endTime; }

        public void setEndTime(String endTime) { this.endTime = endTime; }

        public String getQueueId() { return queueId; }

        public void setQueueId(String queueId) { this.queueId = queueId; }

        public String getQueueType() { return queueType; }

        public void setQueueType(String queueType) { this.queueType = queueType; }

        public Input getInput() { return input; }

        public void setInput(Input input) { this.input = input; }

        public PosterProductionResponseOperation getOperation() { return operation; }

        public void setOperation(PosterProductionResponseOperation operation) { this.operation = operation; }

    }

    @XStreamAlias("Input")
    public class Input {
        /**
         *存储桶的地域
         */
        @XStreamAlias("Region")
        @JsonProperty("Region")
        private String region;

        /**
         *存储结果的存储桶
         */
        @XStreamAlias("Bucket")
        @JsonProperty("Bucket")
        private String bucket;

        /**
         *输出结果的文件名
         */
        @XStreamAlias("Object")
        @JsonProperty("Object")
        private String object;

        public String getRegion() { return region; }

        public void setRegion(String region) { this.region = region; }

        public String getBucket() { return bucket; }

        public void setBucket(String bucket) { this.bucket = bucket; }

        public String getObject() { return object; }

        public void setObject(String object) { this.object = object; }

    }

    @XStreamAlias("PosterProductionResponseOperation")
    public class PosterProductionResponseOperation {
        /**
         *任务的模板 ID
         */
        @XStreamAlias("TemplateId")
        @JsonProperty("TemplateId")
        private String templateId;

        /**
         *任务的模板名称, 当 TemplateId 存在时返回
         */
        @XStreamAlias("TemplateName")
        @JsonProperty("TemplateName")
        private String templateName;

        /**
         *海报合成参数
         */
        @XStreamAlias("PosterProduction")
        @JsonProperty("PosterProduction")
        private PosterProduction posterProduction;

        /**
         *同请求中的 Request.Operation.Output
         */
        @XStreamAlias("Output")
        @JsonProperty("Output")
        private Output output;

        /**
         *透传用户信息
         */
        @XStreamAlias("UserData")
        @JsonProperty("UserData")
        private String userData;

        /**
         *任务优先级，级别限制：0 、1 、2 。级别越大任务优先级越高，默认为0
         */
        @XStreamAlias("JobLevel")
        @JsonProperty("JobLevel")
        private String jobLevel;

        public String getTemplateId() { return templateId; }

        public void setTemplateId(String templateId) { this.templateId = templateId; }

        public String getTemplateName() { return templateName; }

        public void setTemplateName(String templateName) { this.templateName = templateName; }

        public PosterProduction getPosterProduction() { return posterProduction; }

        public void setPosterProduction(PosterProduction posterProduction) { this.posterProduction = posterProduction; }

        public Output getOutput() { return output; }

        public void setOutput(Output output) { this.output = output; }

        public String getUserData() { return userData; }

        public void setUserData(String userData) { this.userData = userData; }

        public String getJobLevel() { return jobLevel; }

        public void setJobLevel(String jobLevel) { this.jobLevel = jobLevel; }

    }

    @XStreamAlias("Output")
    public class Output {
        /**
         *存储桶的地域
         */
        @XStreamAlias("Region")
        @JsonProperty("Region")
        private String region;

        /**
         *存储结果的存储桶
         */
        @XStreamAlias("Bucket")
        @JsonProperty("Bucket")
        private String bucket;

        /**
         *输出结果的文件名
         */
        @XStreamAlias("Object")
        @JsonProperty("Object")
        private String object;

        public String getRegion() { return region; }

        public void setRegion(String region) { this.region = region; }

        public String getBucket() { return bucket; }

        public void setBucket(String bucket) { this.bucket = bucket; }

        public String getObject() { return object; }

        public void setObject(String object) { this.object = object; }

    }

    @XStreamAlias("PosterProduction")
    public class PosterProduction {
        /**
         *海报合成任务模板 ID
         */
        @XStreamAlias("TemplateId")
        @JsonProperty("TemplateId")
        private String templateId;

        public String getTemplateId() { return templateId; }

        public void setTemplateId(String templateId) { this.templateId = templateId; }

    }

}