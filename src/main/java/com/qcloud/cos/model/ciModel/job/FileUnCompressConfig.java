package com.qcloud.cos.model.ciModel.job;

public class FileUnCompressConfig {
    private String prefix;
    private String prefixReplaced;

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
