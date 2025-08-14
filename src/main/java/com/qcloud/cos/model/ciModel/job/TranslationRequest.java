package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("Request")
public class TranslationRequest extends CIServiceRequest implements Serializable {

    @XStreamAlias("Tag")
    private String tag;
    @XStreamAlias("Input")
    private TranslationInput translationInput;
    @XStreamAlias("Operation")
    private TranslationOperation translationOperation;
    @XStreamAlias("CallBack")
    private String callBack;
    @XStreamAlias("CallBackFormat")
    private String callBackFormat;
    @XStreamAlias("CallBackType")
    private String callBackType;
    @XStreamAlias("CallBackMqConfig")
    private CallBackMqConfig callBackMqConfig;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public TranslationInput getInput() {
        if (translationInput == null) {
            translationInput = new TranslationInput();
        }
        return translationInput;
    }

    public void setInput(TranslationInput translationInput) {
        this.translationInput = translationInput;
    }

    public TranslationOperation getOperation() {
        if (translationOperation == null) {
            translationOperation = new TranslationOperation();
        }
        return translationOperation;
    }

    public void setOperation(TranslationOperation translationOperation) {
        this.translationOperation = translationOperation;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
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

    public CallBackMqConfig getCallBackMqConfig() {
        if (callBackMqConfig == null) {
            callBackMqConfig = new CallBackMqConfig();
        }
        return callBackMqConfig;
    }

    public void setCallBackMqConfig(CallBackMqConfig callBackMqConfig) {
        this.callBackMqConfig = callBackMqConfig;
    }
}
