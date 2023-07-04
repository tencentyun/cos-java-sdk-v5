package com.qcloud.cos.model.ciModel.job;

public class QualityEstimateConfig {
    /**
     * 旋转角度, 只支持 0 90 180 270
     */
    private String rotate;
    /**
     * 分析模式
     * general: 普通分析, 只返回视频质量分数
     * vqaPlus: 返回更详细的分析结果
     * 设置为vqaPlus时，Rotate 参数无效
     */
    private String mode;

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
