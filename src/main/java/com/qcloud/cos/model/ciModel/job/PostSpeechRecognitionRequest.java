package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Request")
public class PostSpeechRecognitionRequest extends CIServiceRequest {

    /**
     * 创建任务的 Tag：SpeechRecognition;是否必传：是
     */
    @XStreamAlias("Tag")
    private String tag;

    /**
     * 待操作的对象信息;是否必传：是
     */
    @XStreamAlias("Input")
    private Input input;

    /**
     * 操作规则;是否必传：是
     */
    @XStreamAlias("Operation")
    private Operation operation;

    /**
     * 任务回调格式，JSON 或 XML，默认 XML，优先级高于队列的回调格式;是否必传：否
     */
    @XStreamAlias("CallBackFormat")
    private String callBackFormat;

    /**
     * 任务回调类型，Url 或 TDMQ，默认 Url，优先级高于队列的回调类型;是否必传：否
     */
    @XStreamAlias("CallBackType")
    private String callBackType;

    /**
     * 任务回调地址，优先级高于队列的回调地址。设置为 no 时，表示队列的回调地址不产生回调;是否必传：否
     */
    @XStreamAlias("CallBack")
    private String callBack;

    /**
     * 任务回调TDMQ配置，当 CallBackType 为 TDMQ 时必填。详情见 CallBackMqConfig;是否必传：否
     */
    @XStreamAlias("CallBackMqConfig")
    private CallBackMqConfig callBackMqConfig;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Input getInput() {
        if (input == null) {
            input = new Input();
        }
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Operation getOperation() {
        if (operation == null) {
            operation = new Operation();
        }
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getCallBackFormat() {
        return callBackFormat;
    }

    public void setCallBackFormat(String callBackFormat) {
        this.callBackFormat = callBackFormat;
    }

    public String getCallBackType() {
        return callBackType;
    }

    public void setCallBackType(String callBackType) {
        this.callBackType = callBackType;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public CallBackMqConfig getCallBackMqConfig() {
        if (callBackMqConfig == null) {
            callBackMqConfig = new CallBackMqConfig();
        }
        return callBackMqConfig;
    }

    public void setCallBackMqConfig(CallBackMqConfig callBackMqConfig) {
        this.callBackMqConfig = callBackMqConfig;
    }

    @XStreamAlias("Input")
    public class Input {
        /**
         * 文件路径;是否必传：否
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
         * 语音识别模板 ID;是否必传：否
         */
        @XStreamAlias("TemplateId")
        private String templateId;

        /**
         * 语音识别参数，同创建语音识别模板接口中的 Request.SpeechRecognition﻿;是否必传：否
         */
        @XStreamAlias("SpeechRecognition")
        private SpeechRecognition speechRecognition;

        /**
         * 结果输出配置;是否必传：是
         */
        @XStreamAlias("Output")
        private Output output;

        /**
         * 透传用户信息, 可打印的 ASCII 码, 长度不超过1024;是否必传：否
         */
        @XStreamAlias("UserData")
        private String userData;

        /**
         * 任务优先级，级别限制：0 、1 、2 。级别越大任务优先级越高，默认为0;是否必传：否
         */
        @XStreamAlias("JobLevel")
        private String jobLevel;

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public SpeechRecognition getSpeechRecognition() {
            if (speechRecognition == null) {
                speechRecognition = new SpeechRecognition();
            }
            return speechRecognition;
        }

        public void setSpeechRecognition(SpeechRecognition speechRecognition) {
            this.speechRecognition = speechRecognition;
        }

        public Output getOutput() {
            if (output == null) {
                output = new Output();
            }
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

    }

    @XStreamAlias("Output")
    public class Output {
        /**
         * 存储桶的地域;是否必传：是
         */
        @XStreamAlias("Region")
        private String region;

        /**
         * 存储结果的存储桶;是否必传：是
         */
        @XStreamAlias("Bucket")
        private String bucket;

        /**
         * 结果文件的名称;是否必传：是
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

    @XStreamAlias("CallBackMqConfig")
    public class CallBackMqConfig {
        /**
         * 消息队列所属园区，目前支持园区 sh（上海）、bj（北京）、gz（广州）、cd（成都）、hk（中国香港）;是否必传：是
         */
        @XStreamAlias("MqRegion")
        private String mqRegion;

        /**
         * 消息队列使用模式，默认 Queue ：主题订阅：Topic队列服务: Queue;是否必传：是
         */
        @XStreamAlias("MqMode")
        private String mqMode;

        /**
         * TDMQ 主题名称;是否必传：是
         */
        @XStreamAlias("MqName")
        private String mqName;

        public String getMqRegion() {
            return mqRegion;
        }

        public void setMqRegion(String mqRegion) {
            this.mqRegion = mqRegion;
        }

        public String getMqMode() {
            return mqMode;
        }

        public void setMqMode(String mqMode) {
            this.mqMode = mqMode;
        }

        public String getMqName() {
            return mqName;
        }

        public void setMqName(String mqName) {
            this.mqName = mqName;
        }

    }

    @XStreamAlias("SpeechRecognition")
    public static class SpeechRecognition {
        /**
         * 开启极速ASR，取值 true/false;是否必传：否
         */
        @XStreamAlias("FlashAsr")
        private String flashAsr;

        /**
         * 引擎模型类型，分为电话场景和非电话场景。电话场景：8k_zh：电话 8k 中文普通话通用（可用于双声道音频）8k_zh_s：电话 8k 中文普通话话者分离（仅适用于单声道音频）8k_en：电话 8k 英语 非电话场景： 6k_zh：16k 中文普通话通用16k_zh_video：16k 音视频领域16k_en：16k 英语16k_ca：16k 粤语16k_ja：16k 日语16k_zh_edu：中文教育16k_en_edu：英文教育16k_zh_medical：医疗16k_th：泰语16k_zh_dialect：多方言，支持23种方言极速 ASR 支持8k_zh、16k_zh、16k_en、16k_zh_video、16k_zh_dialect、16k_ms（马来语）、16k_zh-PY（中英粤）;是否必传：是
         */
        @XStreamAlias("EngineModelType")
        private String engineModelType;

        /**
         * 语音声道数：1 表示单声道。EngineModelType为非电话场景仅支持单声道2 表示双声道（仅支持 8k_zh 引擎模型 双声道应分别对应通话双方）仅���持非极速ASR，为非极速ASR时，该参数必填;是否必传：否
         */
        @XStreamAlias("ChannelNum")
        private String channelNum;

        /**
         * 识别结果返回形式：0：识别结果文本（含分段时间戳）1：词级别粒度的详细识别结果，不含标点，含语速值（词时间戳列表，一般用于生成字幕场景）2：词级别粒度的详细识别结果（包含标点、语速值）3：标点符号分段，包含每段时间戳，特别适用于字幕场景（包含词级时间、标点、语速值）仅支持非极速ASR;是否必传：否
         */
        @XStreamAlias("ResTextFormat")
        private String resTextFormat;

        /**
         * 是否过滤脏词（目前支持中文普通话引擎）0：不过滤脏词1：过滤脏词2：将脏词替换为 *;是否必传：否
         */
        @XStreamAlias("FilterDirty")
        private String filterDirty;

        /**
         * 是否过滤语气词（目前支持中文普通话引擎）：0 表示不过滤语气词1 表示部分过滤2 表示严格过滤 ;是否必传：否
         */
        @XStreamAlias("FilterModal")
        private String filterModal;

        /**
         * 是否进行阿拉伯数字智能转换（目前支持中文普通话引擎）0：不转换，直接输出中文数字1：根据场景智能转换为阿拉伯数字3 ：打开数学相关数字转换仅支持非极速ASR;是否必传：否
         */
        @XStreamAlias("ConvertNumMode")
        private String convertNumMode;

        /**
         * 是否开启说话人分离0：不开启1：开启(仅支持8k_zh，16k_zh，16k_zh_video，单声道音频)8k电话场景建议使用双声道来区分通话双方，设置ChannelNum=2即可，不用开启说话人分离。;是否必传：否
         */
        @XStreamAlias("SpeakerDiarization")
        private String speakerDiarization;

        /**
         * 说话人分离人数（需配合开启说话人分离使用），取值范围：[0, 10]0 代表自动分离（目前仅支持≤6个人）1-10代表指定说话人数分离仅支持非极速ASR;是否必传：否
         */
        @XStreamAlias("SpeakerNumber")
        private String speakerNumber;

        /**
         * 是否过滤标点符号（目前支持中文普通话引擎）0：不过滤。1：过滤句末标点2：过滤所有标点;是否必传：否
         */
        @XStreamAlias("FilterPunc")
        private String filterPunc;

        /**
         * 输出文件类型，可选txt、srt极速ASR仅支持txt非极速Asr且ResTextFormat为3时仅支持txt;是否必传：否
         */
        @XStreamAlias("OutputFileType")
        private String outputFileType;

        /**
         * 极速ASR音频格式，支持 wav、pcm、ogg-opus、speex、silk、mp3、m4a、aac极速ASR时，该参数必填;是否必传：否
         */
        @XStreamAlias("Format")
        private String format;

        /**
         * 是否识别首个声道0：识别所有声道1：识别首个声道仅支持极速ASR;是否必传：否
         */
        @XStreamAlias("FirstChannelOnly")
        private String firstChannelOnly;

        /**
         * 是否显示词级别时间戳0：不显示1：显示，不包含标点时间戳2：显示，包含标点时间戳仅支持极速ASR;是否必传：否
         */
        @XStreamAlias("WordInfo")
        private String wordInfo;

        /**
         * 单标点最多字数，取值范围：[6，40]默认值为 0 表示不开启该功能该参数可用于字幕生成场景，控制单行字幕最大字数当FlashAsr为false时，仅ResTextFormat为3时参数有效;是否必传：否
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
