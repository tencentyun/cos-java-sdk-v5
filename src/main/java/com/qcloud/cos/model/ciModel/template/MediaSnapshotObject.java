package com.qcloud.cos.model.ciModel.template;


public class MediaSnapshotObject {

    /**
     * 模式
     */
    private String mode;
    /**
     * 开始时间
     */
    private String start;
    /**
     * 截图频率
     */
    private String timeInterval;
    /**
     * 截图数量
     */
    private String count;
    /**
     * 视频原始宽度
     */
    private String width;
    /**
     * 视频原始高度
     */
    private String height;

    /**
     * 每秒帧数
     */
    private String fps;
    /**
     * 截图图片处理参数 详情参考 https://cloud.tencent.com/document/product/460/36540
     * 例如: imageMogr2/format/png
     */
    private String ciParam;
    /**
     * 是否强制检查截图个数
     * 使用自定义间隔模式截图时，视频时长不够截取Count个截图，可以转为平均截图模式截取Count个截图
     */
    private String isCheckCount;
    /**
     * 是否开启黑屏检测 true/false
     */
    private String isCheckBlack;
    /**
     * 截图黑屏检测参数
     * 当IsCheckBlack=true时有效
     * 值参考范围[30，100]，表示黑色像素的占比值，值越小，黑色占比越小
     * Start>0，参数设置无效，不做过滤黑屏
     * Start =0 参数有效，截帧的开始时间为第一帧非黑屏开始
     */
    private String blackLevel;

    /**
     * 截图黑屏检测参数
     * 当IsCheckBlack=true时有效
     * 判断像素点是否为黑色点的阈值，取值范围：[0，255]
     */
    private String pixelBlackThreshold;

    /**
     * 截图输出模式参数
     * 值范围：{OnlySnapshot, OnlySprite, SnapshotAndSprite}
     * OnlySnapshot 表示仅输出截图模式 OnlySprite 表示仅输出雪碧图模式 SnapshotAndSprite 表示输出截图与雪碧图模式
     */
    private String snapshotOutMode;

    /**
     * 雪碧图输出配置
     */
    private SpriteSnapshotConfig snapshotConfig = new SpriteSnapshotConfig();

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getFps() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps = fps;
    }

    public String getCiParam() {
        return ciParam;
    }

    public void setCiParam(String ciParam) {
        this.ciParam = ciParam;
    }

    public String getIsCheckCount() {
        return isCheckCount;
    }

    public void setIsCheckCount(String isCheckCount) {
        this.isCheckCount = isCheckCount;
    }

    public String getIsCheckBlack() {
        return isCheckBlack;
    }

    public void setIsCheckBlack(String isCheckBlack) {
        this.isCheckBlack = isCheckBlack;
    }

    public String getBlackLevel() {
        return blackLevel;
    }

    public void setBlackLevel(String blackLevel) {
        this.blackLevel = blackLevel;
    }

    public String getPixelBlackThreshold() {
        return pixelBlackThreshold;
    }

    public void setPixelBlackThreshold(String pixelBlackThreshold) {
        this.pixelBlackThreshold = pixelBlackThreshold;
    }

    public String getSnapshotOutMode() {
        return snapshotOutMode;
    }

    public void setSnapshotOutMode(String snapshotOutMode) {
        this.snapshotOutMode = snapshotOutMode;
    }

    public SpriteSnapshotConfig getSnapshotConfig() {
        return snapshotConfig;
    }

    public void setSnapshotConfig(SpriteSnapshotConfig snapshotConfig) {
        this.snapshotConfig = snapshotConfig;
    }

    @Override
    public String toString() {
        return "MediaSnapshotObject{" +
                "mode='" + mode + '\'' +
                ", start='" + start + '\'' +
                ", timeInterval='" + timeInterval + '\'' +
                ", count='" + count + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", fps='" + fps + '\'' +
                '}';
    }
}
