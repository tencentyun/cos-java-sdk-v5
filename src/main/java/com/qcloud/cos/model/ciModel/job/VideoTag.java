package com.qcloud.cos.model.ciModel.job;

public class VideoTag {
    /**
     * 场景类型，可选择视频标签的运用场景，不同的运用场景使用的算法、输入输出等都会有所差异
     * 当前版本只适配 Stream 场景
     */
    private String scenario;

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }
}
