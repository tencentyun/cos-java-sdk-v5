package com.qcloud.cos.model.ciModel.hls;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("PlayKeyList")
public class PlayKeyList {

    /**
     *主播放密钥
     */
    @XStreamAlias("MasterPlayKey")
    private String masterPlayKey;

    /**
     *备播放密钥
     */
    @XStreamAlias("BackupPlayKey")
    private String backupPlayKey;

    public String getMasterPlayKey() { return masterPlayKey; }

    public void setMasterPlayKey(String masterPlayKey) { this.masterPlayKey = masterPlayKey; }

    public String getBackupPlayKey() { return backupPlayKey; }

    public void setBackupPlayKey(String backupPlayKey) { this.backupPlayKey = backupPlayKey; }



}
