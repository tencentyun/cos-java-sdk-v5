package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

public class FileCompressConfig {
    @XStreamAlias("Flatten")
    private String flatten;
    @XStreamAlias("Format")
    private String format;
    @XStreamAlias("Type")
    private String type;
    @XStreamAlias("CompressKey")
    private String compressKey;
    @XStreamAlias("UrlList")
    private String urlList;
    @XStreamAlias("Prefix")
    private String prefix;
    @XStreamAlias("IgnoreError")
    private String ignoreError;
    @XStreamImplicit(itemFieldName = "Key")
    private List<String> key;

    @XStreamImplicit(itemFieldName = "KeyConfig")
    private List<KeyConfig> keyConfigList;

    public List<KeyConfig> getKeyConfigList() {
        if (keyConfigList == null) {
            keyConfigList = new ArrayList<>();
        }
        return keyConfigList;
    }

    public void setKeyConfigList(List<KeyConfig> keyConfigList) {
        this.keyConfigList = keyConfigList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompressKey() {
        return compressKey;
    }

    public void setCompressKey(String compressKey) {
        this.compressKey = compressKey;
    }

    public String getIgnoreError() {
        return ignoreError;
    }

    public void setIgnoreError(String ignoreError) {
        this.ignoreError = ignoreError;
    }

    public String getFlatten() {
        return flatten;
    }

    public void setFlatten(String flatten) {
        this.flatten = flatten;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getUrlList() {
        return urlList;
    }

    public void setUrlList(String urlList) {
        this.urlList = urlList;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<String> getKey() {
        if (key == null) {
            key = new ArrayList<>();
        }
        return key;
    }

    public void setKey(List<String> key) {
        this.key = key;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileCompressConfig{");
        sb.append("flatten='").append(flatten).append('\'');
        sb.append(", format='").append(format).append('\'');
        sb.append(", urlList='").append(urlList).append('\'');
        sb.append(", prefix='").append(prefix).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
