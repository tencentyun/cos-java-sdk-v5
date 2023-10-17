package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.model.ciModel.job.PostSpeechRecognitionResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("SpeechRecognitionResult")
public class SpeechRecognitionResult {
    /**
     * 音频时长(秒)
     */
    @XStreamAlias("AudioTime")
    private String audioTime;

    /**
     * 语音识别结果
     */
    @XStreamAlias("Result")
    private String result;

    /**
     * 极速语音识别结果
     */
    @XStreamImplicit(itemFieldName = "FlashResult")
    private List<FlashResult> flashResult;

    /**
     * 识别结果详情，包含每个句子中的词时间偏移，一般用于生成字幕的场景。(语音识别请求中ResTextFormat=1时该字段不为空)注意：此字段可能为空，表示取不到有效值。
     */
    @XStreamImplicit(itemFieldName = "ResultDetail")
    private List<PostSpeechRecognitionResponse.ResultDetail> resultDetail;

    public String getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(String audioTime) {
        this.audioTime = audioTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<FlashResult> getFlashResult() {
        return flashResult;
    }

    public void setFlashResult(List<FlashResult> flashResult) {
        this.flashResult = flashResult;
    }

//    public List<PostSpeechRecognitionResponse.ResultDetail> getResultDetail() {
//        return resultDetail;
//    }
//
//    public void setResultDetail(List<PostSpeechRecognitionResponse.ResultDetail> resultDetail) {
//        this.resultDetail = resultDetail;
//    }

}
