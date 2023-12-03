package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class FileUnCompressConfig {
    @XStreamAlias("Prefix")
    private String prefix;
    @XStreamAlias("PrefixReplaced")
    private String prefixReplaced;
    @XStreamAlias("UnCompressKey")
    private String unCompressKey;
    @XStreamAlias("Mode")
    private String mode;
    @XStreamAlias("DownloadConfig")
    private DownloadConfig downloadConfig;

    public String getUnCompressKey() {
        return unCompressKey;
    }

    public void setUnCompressKey(String unCompressKey) {
        this.unCompressKey = unCompressKey;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public DownloadConfig getDownloadConfig() {
        if (downloadConfig == null) {
            downloadConfig = new DownloadConfig();
        }
        return downloadConfig;
    }

    public void setDownloadConfig(DownloadConfig downloadConfig) {
        this.downloadConfig = downloadConfig;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefixReplaced() {
        return prefixReplaced;
    }

    public void setPrefixReplaced(String prefixReplaced) {
        this.prefixReplaced = prefixReplaced;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileUnCompressConfig{");
        sb.append("prefix='").append(prefix).append('\'');
        sb.append(", prefixReplaced='").append(prefixReplaced).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
