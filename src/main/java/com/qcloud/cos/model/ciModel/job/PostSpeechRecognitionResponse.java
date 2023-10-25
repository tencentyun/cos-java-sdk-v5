package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.job.v2.SpeechRecognition;
import com.qcloud.cos.model.ciModel.job.v2.SpeechRecognitionResult;
import com.qcloud.cos.model.ciModel.job.v2.WordList;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Response")
public class PostSpeechRecognitionResponse {

    /**
     * 任务的详细信息
     */
    @XStreamImplicit(itemFieldName = "JobsDetail")
    private List<JobsDetail> jobsDetail;

    public List<JobsDetail> getJobsDetail() {
        if (jobsDetail == null) {
            jobsDetail = new ArrayList<>();
        }
        return jobsDetail;
    }

    public void setJobsDetail(List<JobsDetail> jobsDetail) {
        this.jobsDetail = jobsDetail;
    }


    @XStreamAlias("JobsDetail")
    public static class JobsDetail {
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
         * 新创建任务的 ID
         */
        @XStreamAlias("JobId")
        private String jobId;

        /**
         * 新创建任务的 Tag：SpeechRecognition
         */
        @XStreamAlias("Tag")
        private String tag;

        /**
         * 任务状态Submitted：已提交，待执行Running：执行中Success：执行成功Failed：执行失败Pause：任务暂停，当暂停队列时，待执行的任务会变为暂停状态Cancel：任务被取消执行
         */
        @XStreamAlias("State")
        private String state;

        /**
         * 任务的创建时间
         */
        @XStreamAlias("CreationTime")
        private String creationTime;

        /**
         * 任务的开始时间
         */
        @XStreamAlias("StartTime")
        private String startTime;

        /**
         * 任务的结束时间
         */
        @XStreamAlias("EndTime")
        private String endTime;

        /**
         * 任务所属的 队列 ID
         */
        @XStreamAlias("QueueId")
        private String queueId;

        /**
         * 同请求中的 Request.Input 节点
         */
        @XStreamAlias("Input")
        private Input input;

        /**
         * 该任务的规则
         */
        @XStreamAlias("Operation")
        private Operation operation;

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

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getQueueId() {
            return queueId;
        }

        public void setQueueId(String queueId) {
            this.queueId = queueId;
        }

        public Input getInput() {
            return input;
        }

        public void setInput(Input input) {
            this.input = input;
        }

        public Operation getOperation() {
            return operation;
        }

        public void setOperation(Operation operation) {
            this.operation = operation;
        }

    }

    @XStreamAlias("Input")
    public static class Input {
        /**
         * 文件路径
         */
        @XStreamAlias("Object")
        private String object;

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

    }

    @XStreamAlias("Operation")
    public static class Operation {
        /**
         * 任务的模板 ID
         */
        @XStreamAlias("TemplateId")
        private String templateId;

        /**
         * 任务的模板名称, 当 TemplateId 存在时返回
         */
        @XStreamAlias("TemplateName")
        private String templateName;

        /**
         * 同请求中的 Request.Operation.SpeechRecognition
         */
        @XStreamAlias("SpeechRecognition")
        private SpeechRecognition speechRecognition;

        /**
         * 同请求中的 Request.Operation.Output
         */
        @XStreamAlias("Output")
        private Output output;

        /**
         * 透传用户信息
         */
        @XStreamAlias("UserData")
        private String userData;

        /**
         * 任务优先级
         */
        @XStreamAlias("JobLevel")
        private String jobLevel;

        /**
         * 语音识别任务结果，没有时不返回
         */
        @XStreamAlias("SpeechRecognitionResult")
        private SpeechRecognitionResult speechRecognitionResult;

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public String getTemplateName() {
            return templateName;
        }

        public void setTemplateName(String templateName) {
            this.templateName = templateName;
        }

        public SpeechRecognition getSpeechRecognition() {
            return speechRecognition;
        }

        public void setSpeechRecognition(SpeechRecognition speechRecognition) {
            this.speechRecognition = speechRecognition;
        }

        public Output getOutput() {
            return output;
        }

        public void setOutput(Output output) {
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

        public SpeechRecognitionResult getSpeechRecognitionResult() {
            return speechRecognitionResult;
        }

        public void setSpeechRecognitionResult(SpeechRecognitionResult speechRecognitionResult) {
            this.speechRecognitionResult = speechRecognitionResult;
        }

    }

    @XStreamAlias("Output")
    public static class Output {
        /**
         * 存储桶的地域
         */
        @XStreamAlias("Region")
        private String region;

        /**
         * 存储结果的存储桶
         */
        @XStreamAlias("Bucket")
        private String bucket;

        /**
         * 结果文件的名称
         */
        @XStreamAlias("Object")
        private String object;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

    }

    public static class SentenceList {
        /**
         * 句子/段落级别文本
         */
        @XStreamAlias("text")
        private String text;

        /**
         * 开始时间
         */
        @XStreamAlias("start_time")
        private Integer startTime;

        /**
         * 结束时间
         */
        @XStreamAlias("end_time")
        private Integer endTime;

        /**
         * 说话人 Id（请求中如果设置了 speaker_diarization，可以按照 speaker_id 来区分说话人）
         */
        @XStreamAlias("speaker_id")
        private Integer speakerId;

        /**
         * 词级别的识别结果列表
         */
        @XStreamImplicit(itemFieldName = "word_list")
        private List<WordList> wordList;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Integer getStartTime() {
            return startTime;
        }

        public void setStartTime(Integer startTime) {
            this.startTime = startTime;
        }

        public Integer getEndTime() {
            return endTime;
        }

        public void setEndTime(Integer endTime) {
            this.endTime = endTime;
        }

        public Integer getSpeakerId() {
            return speakerId;
        }

        public void setSpeakerId(Integer speakerId) {
            this.speakerId = speakerId;
        }

        public List<WordList> getWordList() {
            return wordList;
        }

        public void setWordList(List<WordList> wordList) {
            this.wordList = wordList;
        }
    }

    @XStreamAlias("ResultDetail")
    public static class ResultDetail {
        /**
         * 单句最终识别结果
         */
        @XStreamAlias("FinalSentence")
        private String finalSentence;

        /**
         * 单句中间识别结果，使用空格拆分为多个词
         */
        @XStreamAlias("SliceSentence")
        private String sliceSentence;

        /**
         * 单句开始时间（毫秒）
         */
        @XStreamAlias("StartMs")
        private String startMs;

        /**
         * 单句结束时间（毫秒）
         */
        @XStreamAlias("EndMs")
        private String endMs;

        /**
         * 单句中词个数
         */
        @XStreamAlias("WordsNum")
        private String wordsNum;

        /**
         * 单句语速，单位：字数/秒
         */
        @XStreamAlias("SpeechSpeed")
        private String speechSpeed;

        /**
         * 声道或说话人 Id（请求中如果设置了 speaker_diarization或者ChannelNum为双声道，可区分说话人或声道）
         */
        @XStreamAlias("SpeakerId")
        private String speakerId;

        /**
         * 单句中词详情
         */
        @XStreamImplicit(itemFieldName = "Words")
        private List<Words> words;

        public String getFinalSentence() {
            return finalSentence;
        }

        public void setFinalSentence(String finalSentence) {
            this.finalSentence = finalSentence;
        }

        public String getSliceSentence() {
            return sliceSentence;
        }

        public void setSliceSentence(String sliceSentence) {
            this.sliceSentence = sliceSentence;
        }

        public String getStartMs() {
            return startMs;
        }

        public void setStartMs(String startMs) {
            this.startMs = startMs;
        }

        public String getEndMs() {
            return endMs;
        }

        public void setEndMs(String endMs) {
            this.endMs = endMs;
        }

        public String getWordsNum() {
            return wordsNum;
        }

        public void setWordsNum(String wordsNum) {
            this.wordsNum = wordsNum;
        }

        public String getSpeechSpeed() {
            return speechSpeed;
        }

        public void setSpeechSpeed(String speechSpeed) {
            this.speechSpeed = speechSpeed;
        }

        public String getSpeakerId() {
            return speakerId;
        }

        public void setSpeakerId(String speakerId) {
            this.speakerId = speakerId;
        }

        public List<Words> getWords() {
            return words;
        }

        public void setWords(List<Words> words) {
            this.words = words;
        }

    }

    @XStreamAlias("Words")
    public static class Words {
        /**
         * 词文本
         */
        @XStreamAlias("Word")
        private String word;

        /**
         * 在句子中的开始时间偏移量
         */
        @XStreamAlias("OffsetStartMs")
        private String offsetStartMs;

        /**
         * 在句子中的结束时间偏移量
         */
        @XStreamAlias("OffsetEndMs")
        private String offsetEndMs;

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getOffsetStartMs() {
            return offsetStartMs;
        }

        public void setOffsetStartMs(String offsetStartMs) {
            this.offsetStartMs = offsetStartMs;
        }

        public String getOffsetEndMs() {
            return offsetEndMs;
        }

        public void setOffsetEndMs(String offsetEndMs) {
            this.offsetEndMs = offsetEndMs;
        }

    }
}
