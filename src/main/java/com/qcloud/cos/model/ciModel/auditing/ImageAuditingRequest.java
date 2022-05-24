package com.qcloud.cos.model.ciModel.auditing;


import com.qcloud.cos.internal.CosServiceRequest;

/**
 * 图片审核请求实体 参数详情参考：https://cloud.tencent.com/document/product/460/37318
 */
public class ImageAuditingRequest extends CosServiceRequest {
    /**
     * 审核类型，拥有 porn（涉黄识别）、terrorism（涉暴恐识别）、politics（涉政识别）、ads（广告识别）四种。用户可选择多种识别类型，
     * 例如 detectType=porn,ads 表示对图片进行涉黄及广告审核
     * 为空时则表示审核全部类型
     */
    private String detectType;

    /**
     * 操作的bucket名称
     */
    private String bucketName;

    /**
     * ObjectKey 对象在存储桶中的位置及名称
     * 例如根目录下pic文件夹中的1.jpg文件   pic/1.jpg
     */
    private String objectKey;

    /**
     * 截帧频率，GIF 图或长图检测专用，默认值为5，表示只会检测 GIF 图或长图的第一帧
     */
    private int interval = 5;

    /**
     * 最大截帧数量，GIF 图或长图检测专用，默认值为5，表示只取 GIF 的第1帧图片进行审核，或长图不进行切分识别
     */
    private int maxFrames = 5;

    /**
     * 审核策略的唯一标识，由后台自动生成，在控制台中对应为 Biztype 值
     */
    private String bizType;

    /**
     * 您可以通过填写detect-url审核任意公网可访问的图片链接
     * 不填写detect-url时，后台会默认审核ObjectKey
     * 填写了detect-url时，后台会审核detect-url链接，无需再填写ObjectKey
     * detect-url示例：http://www.example.com/abc.jpg
     */
    private String detectUrl;

    /**
     * 对于超过大小限制的图片，可通过该参数选择是否需要压缩图片后再审核
     * 压缩为后台默认操作，会产生额外的 基础图片处理用量
     * 取值为：0（不压缩），1（压缩）。默认为0
     * 注意：最大支持压缩32MB的图片。
     */
    private String largeImageDetect;

    /**
     * 图片标识，该字段在结果中返回原始内容，长度限制为512字节
     */
    private String dataId;

    /**
     * 任务id 用于查询
     */
    private String jobId;

    /**
     * 是否开启异步审核 0 不开启 1开启  默认为0
     */
    private String async;


    public String getDetectType() {
        return detectType;
    }

    public void setDetectType(String detectType) {
        this.detectType = detectType;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }


    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getMaxFrames() {
        return maxFrames;
    }

    public void setMaxFrames(int maxFrames) {
        this.maxFrames = maxFrames;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getDetectUrl() {
        return detectUrl;
    }

    public void setDetectUrl(String detectUrl) {
        this.detectUrl = detectUrl;
    }

    public String getLargeImageDetect() {
        return largeImageDetect;
    }

    public void setLargeImageDetect(String largeImageDetect) {
        this.largeImageDetect = largeImageDetect;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getAsync() {
        return async;
    }

    public void setAsync(String async) {
        this.async = async;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImageAuditingRequest{");
        sb.append("detectType='").append(detectType).append('\'');
        sb.append(", bucketName='").append(bucketName).append('\'');
        sb.append(", objectKey='").append(objectKey).append('\'');
        sb.append(", interval=").append(interval);
        sb.append(", maxFrames=").append(maxFrames);
        sb.append(", bizType='").append(bizType).append('\'');
        sb.append(", detectUrl='").append(detectUrl).append('\'');
        sb.append(", largeImageDetect='").append(largeImageDetect).append('\'');
        sb.append(", dataId='").append(dataId).append('\'');
        sb.append(", jobId='").append(jobId).append('\'');
        sb.append(", async='").append(async).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
