package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

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