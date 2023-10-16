package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.job.v2.SpeechRecognitionResult;
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
        if (jobsDetail != null) {
            jobsDetail = new ArrayList<>();
        }
        return jobsDetail;
    }

    public void setJobsDetail(List<JobsDetail> jobsDetail) {
        this.jobsDetail = jobsDetail;
    }


    @XStreamAlias("JobsDetail")
    public class JobsDetail {
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
    public class Input {
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
    public class Operation {
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
    public class Output {
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

    public class SentenceList {
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

    public class WordList {
        /**
         * 词级别文本
         */
        @XStreamAlias("word")
        private String word;

        /**
         * 开始时间
         */
        @XStreamAlias("start_time")
        private Integer start_time;

        /**
         * 结束时间
         */
        @XStreamAlias("end_time")
        private Integer end_time;

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public Integer getStart_time() {
            return start_time;
        }

        public void setStart_time(Integer start_time) {
            this.start_time = start_time;
        }

        public Integer getEnd_time() {
            return end_time;
        }

        public void setEnd_time(Integer end_time) {
            this.end_time = end_time;
        }

    }

    @XStreamAlias("ResultDetail")
    public class ResultDetail {
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
    public class Words {
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

    @XStreamAlias("SpeechRecognition")
    public class SpeechRecognition {
        /**
         * 开启极速ASR，取值 true/false
         */
        @XStreamAlias("FlashAsr")
        private String flashAsr;

        /**
         * 引擎模型类型，分为电话场景和非电话场景。电话场景：8k_zh：电话 8k 中文普通话通用（可用于双声道音频）8k_zh_s：电话 8k 中文普通话话者分离（仅适用于单声道音频）8k_en：电话 8k 英语 非电话场景： 6k_zh：16k 中文普通话通用16k_zh_video：16k 音视频领域16k_en：16k 英语16k_ca：16k 粤语16k_ja：16k 日语16k_zh_edu：中文教育16k_en_edu：英文教育16k_zh_medical：医疗16k_th：泰语16k_zh_dialect：多方言，支持23种方言极速 ASR 支持8k_zh、16k_zh、16k_en、16k_zh_video、16k_zh_dialect、16k_ms（马来语）、16k_zh-PY（中英粤）
         */
        @XStreamAlias("EngineModelType")
        private String engineModelType;

        /**
         * 语音声道数：1 表示单声道。EngineModelType为非电话场景仅支持单声道2 表示双声道（仅支持 8k_zh 引擎模型 双声道应分别对应通话双方）仅���持非极速ASR，为非极速ASR时，该参数必填
         */
        @XStreamAlias("ChannelNum")
        private String channelNum;

        /**
         * 识别结果返回形式：0：识别结果文本（含分段时间戳）1：词级别粒度的详细识别结果，不含标点，含语速值（词时间戳列表，一般用于生成字幕场景）2：词级别粒度的详细识别结果（包含标点、语速值）3：标点符号分段，包含每段时间戳，特别适用于字幕场景（包含词级时间、标点、语速值）仅支持非极速ASR
         */
        @XStreamAlias("ResTextFormat")
        private String resTextFormat;

        /**
         * 是否过滤脏词（目前支持中文普通话引擎）0：不过滤脏词1：过滤脏词2：将脏词替换为 *
         */
        @XStreamAlias("FilterDirty")
        private String filterDirty;

        /**
         * 是否过滤语气词（目前支持中文普通话引擎）：0 表示不过滤语气词1 表示部分过滤2 表示严格过滤
         */
        @XStreamAlias("FilterModal")
        private String filterModal;

        /**
         * 是否进行阿拉伯数字智能转换（目前支持中文普通话引擎）0：不转换，直接输出中文数字1：根据场景智能转换为阿拉伯数字3 ：打开数学相关数字转换仅支持非极速ASR
         */
        @XStreamAlias("ConvertNumMode")
        private String convertNumMode;

        /**
         * 是否开启说话人分离0：不开启1：开启(仅支持8k_zh，16k_zh，16k_zh_video，单声道音频)8k电话场景建议使用双声道来区分通话双方，设置ChannelNum=2即可，不用开启说话人分离。
         */
        @XStreamAlias("SpeakerDiarization")
        private String speakerDiarization;

        /**
         * 说话人分离人数（需配合开启说话人分离使用），取值范围：[0, 10]0 代表自动分离（目前仅支持≤6个人）1-10代表指定说话人数分离仅支持非极速ASR
         */
        @XStreamAlias("SpeakerNumber")
        private String speakerNumber;

        /**
         * 是否过滤标点符号（目前支持中文普通话引擎）0：不过滤。1：过滤句末标点2：过滤所有标点
         */
        @XStreamAlias("FilterPunc")
        private String filterPunc;

        /**
         * 输出文件类型，可选txt、srt极速ASR仅支持txt非极速Asr且ResTextFormat为3时仅支持txt
         */
        @XStreamAlias("OutputFileType")
        private String outputFileType;

        /**
         * 极速ASR音频格式，支持 wav、pcm、ogg-opus、speex、silk、mp3、m4a、aac极速ASR时，该参数必填
         */
        @XStreamAlias("Format")
        private String format;

        /**
         * 是否识别首个声道0：识别所有声道1：识别首个声道仅支持极速ASR
         */
        @XStreamAlias("FirstChannelOnly")
        private String firstChannelOnly;

        /**
         * 是否显示词级别时间戳0：不显示1：显示，不包含标点时间戳2：显示，包含标点时间戳仅支持极速ASR
         */
        @XStreamAlias("WordInfo")
        private String wordInfo;

        /**
         * 单标点最多字数，取值范围：[6，40]默认值为 0 表示不开启该功能该参数可用于字幕生成场景，控制单行字幕最大字数当FlashAsr为false时，仅ResTextFormat为3时参数有效
         */
        @XStreamAlias("SentenceMaxLength")
        private String sentenceMaxLength;

        public String getFlashAsr() {
            return flashAsr;
        }

        public void setFlashAsr(String flashAsr) {
            this.flashAsr = flashAsr;
        }

        public String getEngineModelType() {
            return engineModelType;
        }

        public void setEngineModelType(String engineModelType) {
            this.engineModelType = engineModelType;
        }

        public String getChannelNum() {
            return channelNum;
        }

        public void setChannelNum(String channelNum) {
            this.channelNum = channelNum;
        }

        public String getResTextFormat() {
            return resTextFormat;
        }

        public void setResTextFormat(String resTextFormat) {
            this.resTextFormat = resTextFormat;
        }

        public String getFilterDirty() {
            return filterDirty;
        }

        public void setFilterDirty(String filterDirty) {
            this.filterDirty = filterDirty;
        }

        public String getFilterModal() {
            return filterModal;
        }

        public void setFilterModal(String filterModal) {
            this.filterModal = filterModal;
        }

        public String getConvertNumMode() {
            return convertNumMode;
        }

        public void setConvertNumMode(String convertNumMode) {
            this.convertNumMode = convertNumMode;
        }

        public String getSpeakerDiarization() {
            return speakerDiarization;
        }

        public void setSpeakerDiarization(String speakerDiarization) {
            this.speakerDiarization = speakerDiarization;
        }

        public String getSpeakerNumber() {
            return speakerNumber;
        }

        public void setSpeakerNumber(String speakerNumber) {
            this.speakerNumber = speakerNumber;
        }

        public String getFilterPunc() {
            return filterPunc;
        }

        public void setFilterPunc(String filterPunc) {
            this.filterPunc = filterPunc;
        }

        public String getOutputFileType() {
            return outputFileType;
        }

        public void setOutputFileType(String outputFileType) {
            this.outputFileType = outputFileType;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getFirstChannelOnly() {
            return firstChannelOnly;
        }

        public void setFirstChannelOnly(String firstChannelOnly) {
            this.firstChannelOnly = firstChannelOnly;
        }

        public String getWordInfo() {
            return wordInfo;
        }

        public void setWordInfo(String wordInfo) {
            this.wordInfo = wordInfo;
        }

        public String getSentenceMaxLength() {
            return sentenceMaxLength;
        }

        public void setSentenceMaxLength(String sentenceMaxLength) {
            this.sentenceMaxLength = sentenceMaxLength;
        }

    }

}
