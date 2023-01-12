package com.qcloud.cos.model.ciModel.common;

public class BatchInputObject extends MediaInputObject {
   private String manifest;
   private String urlFile;
   private String prefix;

    public String getManifest() {
        return manifest;
    }

    public void setManifest(String manifest) {
        this.manifest = manifest;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BatchInputObject{");
        sb.append("manifest='").append(manifest).append('\'');
        sb.append(", urlFile='").append(urlFile).append('\'');
        sb.append(", prefix='").append(prefix).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
