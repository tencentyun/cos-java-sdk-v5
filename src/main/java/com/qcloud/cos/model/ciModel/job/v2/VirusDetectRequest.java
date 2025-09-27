package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 病毒检测任务请求类
 * https://cloud.tencent.com/document/product/436/63961
 */
@XStreamAlias("Request")
public class VirusDetectRequest extends CIServiceRequest{

    @XStreamAlias("Input")
    private VirusDetectInput input;

    @XStreamAlias("Conf")
    private VirusDetectConf conf;

    public VirusDetectInput getInput() {
        if (input == null) {
            input = new VirusDetectInput();
        }
        return input;
    }

    public void setInput(VirusDetectInput input) {
        this.input = input;
    }

    public VirusDetectConf getConf() {
        if (conf == null) {
            conf = new VirusDetectConf();
        }
        return conf;
    }

    public void setConf(VirusDetectConf conf) {
        this.conf = conf;
    }

    @Override public String toString() {
    return "VirusDetectRequest{" +
            "input=" + input +
            ", conf=" + conf +
            '}';
}}
