package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("Request")
public class SearchPersonFaceRequest extends CosServiceRequest {

    /**
     * {ObjectKey}
     */
    @XStreamOmitField
    private String objectKey;
    private String bucketName;

    /**
     * 出参 Score 中，只有超过 FaceMatchThreshold值的结果才会返回。默认为50;是否必传：否
     */
    @XStreamOmitField
    private Integer faceMatchThreshold;

    /**
     * 是否返回人员具体信息。0 为关闭，1 为开启。默认为 0。其他非0非1值默认为0
     * 示例值：0;是否必传：否
     */
    @XStreamOmitField
    private Integer needPersonInfo;

    /**
     * 图片质量控制。
     * 0: 不进行控制；
     * 1:较低的质量要求，图像存在非常模糊，眼睛鼻子嘴巴遮挡至少其中一种或多种的情况；
     * 2: 一般的质量要求，图像存在偏亮，偏暗，模糊或一般模糊，眉毛遮挡，脸颊遮挡，下巴遮挡，至少其中三种的情况；
     * 3: 较高的质量要求，图像存在偏亮，偏暗，一般模糊，眉毛遮挡，脸颊遮挡，下巴遮挡，其中一到两种的情况；
     * 4: 很高的质量要求，各个维度均为最好或最多在某一维度上存在轻微问题；
     * 默认 0。;是否必传：否
     */
    @XStreamOmitField
    private Integer qualityControl;

    /**
     * 是否开启图片旋转识别支持。0为不开启，1为开启。默认为0。本参数的作用为，当图片中的人脸被旋转且图片没有exif信息时，如果不开启图片旋转识别支持则无法正确检测、识别图片中的人脸。若您确认图片包含exif信息或者您确认输入图中人脸不会出现被旋转情况，请不要开启本参数。开启后，整体耗时将可能增加数百毫秒。
     * 示例值：0;是否必传：否
     */
    @XStreamOmitField
    private Integer needRotateDetection;

    public Integer getFaceMatchThreshold() {
        return faceMatchThreshold;
    }

    public void setFaceMatchThreshold(Integer faceMatchThreshold) {
        this.faceMatchThreshold = faceMatchThreshold;
    }

    public Integer getNeedPersonInfo() {
        return needPersonInfo;
    }

    public void setNeedPersonInfo(Integer needPersonInfo) {
        this.needPersonInfo = needPersonInfo;
    }

    public Integer getQualityControl() {
        return qualityControl;
    }

    public void setQualityControl(Integer qualityControl) {
        this.qualityControl = qualityControl;
    }

    public Integer getNeedRotateDetection() {
        return needRotateDetection;
    }

    public void setNeedRotateDetection(Integer needRotateDetection) {
        this.needRotateDetection = needRotateDetection;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
