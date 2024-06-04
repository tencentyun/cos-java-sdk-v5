package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("Request")
public class RecognizeLogoRequest extends CosServiceRequest {

    /**
     *数据万象处理能力，Logo识别固定为RecognizeLogo;是否必传：是
     */
    @XStreamOmitField
    private String ciProcess = "RecognizeLogo";

    /**
     *待检查图片url，需要进行urlencode;是否必传：是
     */
    @XStreamOmitField
    private String detectUrl;


    private String bucketName;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getCiProcess() { return ciProcess; }

    public void setCiProcess(String ciProcess) { this.ciProcess = ciProcess; }

    public String getDetectUrl() { return detectUrl; }

    public void setDetectUrl(String detectUrl) { this.detectUrl = detectUrl; }



}
