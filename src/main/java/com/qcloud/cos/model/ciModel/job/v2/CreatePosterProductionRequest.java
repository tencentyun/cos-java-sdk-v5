package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Request")
public class CreatePosterProductionRequest extends CIServiceRequest {

    /**
     *创建任务的 Tag：PosterProduction;是否必传：是
     */
    @XStreamAlias("Tag")
    private String tag = "PosterProduction";

    /**
     *待操作的媒体信息;是否必传：是
     */
    @XStreamAlias("Input")
    private ObjectInput input;

    /**
     *操作规则;是否必传：是
     */
    @XStreamAlias("Operation")
    private PosterProductionOperation operation;

    /**
     *任务所在的队列 ID;是否必传：否
     */
    @XStreamAlias("QueueId")
    private String queueId;

    /**
     *任务回调格式，JSON 或 XML，默认 XML，优先级高于队列的回调格式;是否必传：否
     */
    @XStreamAlias("CallBackFormat")
    private String callBackFormat;

    /**
     *任务回调类型，Url 或 TDMQ，默认 Url，优先级高于队列的回调类型;是否必传：否
     */
    @XStreamAlias("CallBackType")
    private String callBackType;

    /**
     *任务回调地址，优先级高于队列的回调地址。设置为 no 时，表示队列的回调地址不产生回调;是否必传：否
     */
    @XStreamAlias("CallBack")
    private String callBack;

    /**
     *任务回调TDMQ配置，当 CallBackType 为 TDMQ 时必填。详情见 CallBackMqConfig;是否必传：否
     */
    @XStreamAlias("CallBackMqConfig")
    private CallBackMqConfig callBackMqConfig;

    public String getTag() { return tag; }

    public void setTag(String tag) { this.tag = tag; }

    public ObjectInput getInput() {
        if(input == null){
            input = new ObjectInput();
        }
        return input;
    }

    public void setInput(ObjectInput input) { this.input = input; }

    public PosterProductionOperation getOperation() {
        if(operation == null){
            operation = new PosterProductionOperation();
        }
        return operation;
    }

    public void setOperation(PosterProductionOperation operation) { this.operation = operation; }

    public String getQueueId() { return queueId; }

    public void setQueueId(String queueId) { this.queueId = queueId; }

    public String getCallBackFormat() { return callBackFormat; }

    public void setCallBackFormat(String callBackFormat) { this.callBackFormat = callBackFormat; }

    public String getCallBackType() { return callBackType; }

    public void setCallBackType(String callBackType) { this.callBackType = callBackType; }

    public String getCallBack() { return callBack; }

    public void setCallBack(String callBack) { this.callBack = callBack; }

    public CallBackMqConfig getCallBackMqConfig() { 
        if(callBackMqConfig == null){
            callBackMqConfig = new CallBackMqConfig(); 
        }
        return callBackMqConfig;
    }

    public void setCallBackMqConfig(CallBackMqConfig callBackMqConfig) { this.callBackMqConfig = callBackMqConfig; }

    
    @XStreamAlias("CallBackMqConfig")
    public static class CallBackMqConfig{
        /**
         *消息队列所属园区，目前支持园区 sh（上海）、bj（北京）、gz（广州）、cd（成都）、hk（中国香港）;是否必传：是
         */
        @XStreamAlias("MqRegion")
        private String mqRegion;

        /**
         *消息队列使用模式，默认 Queue ：主题订阅：Topic队列服务: Queue;是否必传：是
         */
        @XStreamAlias("MqMode")
        private String mqMode;

        /**
         *TDMQ 主题名称;是否必传：是
         */
        @XStreamAlias("MqName")
        private String mqName;

        public String getMqRegion() { return mqRegion; }

        public void setMqRegion(String mqRegion) { this.mqRegion = mqRegion; }

        public String getMqMode() { return mqMode; }

        public void setMqMode(String mqMode) { this.mqMode = mqMode; }

        public String getMqName() { return mqName; }

        public void setMqName(String mqName) { this.mqName = mqName; }

    }

    @XStreamAlias("Input")
    public static class ObjectInput{
        /**
         *媒体文件名;是否必传：是
         */
        @XStreamAlias("Object")
        private String object;

        public String getObject() { return object; }

        public void setObject(String object) { this.object = object; }

    }

    @XStreamAlias("Operation")
    public static class PosterProductionOperation{
        /**
         *指定该任务的参数;是否必传：否
         */
        @XStreamAlias("PosterProduction")
        private TemplateInfo posterProduction;

        /**
         *结果输出地址;是否必传：是
         */
        @XStreamAlias("Output")
        private Output output;

        /**
         *透传用户信息, 可打印的 ASCII 码, 长度不超过1024;是否必传：否
         */
        @XStreamAlias("UserData")
        private String userData;

        /**
         *任务优先级，级别限制：0 、1 、2 。级别越大任务优先级越高，默认为0;是否必传：否
         */
        @XStreamAlias("JobLevel")
        private String jobLevel;

        public TemplateInfo getPosterProduction() {
            if(posterProduction == null){
                posterProduction = new TemplateInfo();
            }
            return posterProduction;
        }

        public void setPosterProduction(TemplateInfo posterProduction) { this.posterProduction = posterProduction; }

        public Output getOutput() { 
            if(output == null){
                output = new Output(); 
            }
            return output;
        }

        public void setOutput(Output output) { this.output = output; }

        public String getUserData() { return userData; }

        public void setUserData(String userData) { this.userData = userData; }

        public String getJobLevel() { return jobLevel; }

        public void setJobLevel(String jobLevel) { this.jobLevel = jobLevel; }

    }

    @XStreamAlias("Output")
    public static class Output{
        /**
         *存储桶的地域;是否必传：是
         */
        @XStreamAlias("Region")
        private String region;

        /**
         *存储结果的存储桶;是否必传：是
         */
        @XStreamAlias("Bucket")
        private String bucket;

        /**
         *输出结果的文件名;是否必传：是
         */
        @XStreamAlias("Object")
        private String object;

        public String getRegion() { return region; }

        public void setRegion(String region) { this.region = region; }

        public String getBucket() { return bucket; }

        public void setBucket(String bucket) { this.bucket = bucket; }

        public String getObject() { return object; }

        public void setObject(String object) { this.object = object; }

    }

    @XStreamAlias("PosterProduction")
    public static class TemplateInfo{

        /**
         *模板 ID;
         */
        @XStreamAlias("TemplateId")
        private String templateId;

        @XStreamAlias("Info")
        private PosterProductionInfo info;

        public String getTemplateId() { return templateId; }

        public void setTemplateId(String templateId) { this.templateId = templateId; }

        public PosterProductionInfo getInfo() {
            if(info == null){
                info = new PosterProductionInfo();
            }
            return info;
        }

        public void setInfo(PosterProductionInfo info) {
            this.info = info;
        }
    }
}