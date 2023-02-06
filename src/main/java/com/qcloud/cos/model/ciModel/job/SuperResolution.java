package com.qcloud.cos.model.ciModel.job;

public class SuperResolution {
    private String resolution;
    private String enableScaleUp;
    private String version;

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getEnableScaleUp() {
        return enableScaleUp;
    }

    public void setEnableScaleUp(String enableScaleUp) {
        this.enableScaleUp = enableScaleUp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SuperResolution{");
        sb.append("resolution='").append(resolution).append('\'');
        sb.append(", enableScaleUp='").append(enableScaleUp).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
