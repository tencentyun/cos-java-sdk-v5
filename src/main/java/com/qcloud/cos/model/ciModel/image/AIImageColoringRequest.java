package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("Request")
public class AIImageColoringRequest extends CosServiceRequest {

    private String bucket;

    @XStreamOmitField
    private String objectKey = "";

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    /**
     * 数据万象处理能力，图片上色参固定为AIImageColoring。;是否必传：是
     */
    @XStreamOmitField
    private String ciProcess;

    /**
     * 待上色图片url，需要进行urlencode，与ObjectKey二选其一，如果同时存在，则默认以ObjectKey为准;是否必传：否
     */
    @XStreamOmitField
    private String detectUrl;

    public String getCiProcess() {
        return ciProcess;
    }

    public void setCiProcess(String ciProcess) {
        this.ciProcess = ciProcess;
    }

    public String getDetectUrl() {
        return detectUrl;
    }

    public void setDetectUrl(String detectUrl) {
        this.detectUrl = detectUrl;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
