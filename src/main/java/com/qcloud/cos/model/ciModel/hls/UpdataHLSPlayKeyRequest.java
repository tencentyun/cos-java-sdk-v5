package com.qcloud.cos.model.ciModel.hls;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Request")
public class UpdataHLSPlayKeyRequest extends CIServiceRequest {

    /**
     *主播放密钥（masterPlayKey与backupPlayKey必须存在一个）;是否必传：是
     */
    @XStreamOmitField
    private String masterPlayKey;

    public String getMasterPlayKey() { return masterPlayKey; }

    public void setMasterPlayKey(String masterPlayKey) { this.masterPlayKey = masterPlayKey; }

    /**
     *备播放密钥;是否必传：是
     */
    @XStreamAlias("backupPlayKey")
    private String backupPlayKey;

    public String getBackupPlayKey() { return backupPlayKey; }

    public void setBackupPlayKey(String backupPlayKey) { this.backupPlayKey = backupPlayKey; }

    


}
