package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("Request")
public class CreateAIObjectDetectJobRequest extends CosServiceRequest {

    private String bucketName;

    @XStreamOmitField
    private String objectKey;

    @XStreamOmitField
    private String ciProcess = "AIObjectDetect";

    @XStreamOmitField
    private String detectUrl;

    // ============ 图像超分 AISuperResolution 特有参数 ============
    /**
     * 目标图片的放大倍数，支持2、4，默认为2
     * 仅在 ciProcess=AISuperResolution 时生效
     */
    @XStreamOmitField
    private Integer magnify;

    // ============ 图像增强 AIEnhanceImage 特有参数 ============
    /**
     * 去噪强度值，取值范围为 0 - 10，默认值为3。值为0时不进行去噪
     * 仅在 ciProcess=AIEnhanceImage 时生效
     */
    @XStreamOmitField
    private Integer denoise;

    /**
     * 锐化强度值，取值范围为 0 - 10，默认值为4。值为0时不进行锐化
     * 仅在 ciProcess=AIEnhanceImage 时生效
     */
    @XStreamOmitField
    private Integer sharpen;

    // ============ 图像智能裁剪 AIImageCrop 特有参数 ============
    /**
     * 需要裁剪区域的宽度，与 height 共同组成所需裁剪的图片宽高比例
     * 仅在 ciProcess=AIImageCrop 时生效
     */
    @XStreamOmitField
    private Integer width;

    /**
     * 需要裁剪区域的高度，与 width 共同组成所需裁剪的图片宽高比例
     * 仅在 ciProcess=AIImageCrop 时生效
     */
    @XStreamOmitField
    private Integer height;

    /**
     * 是否严格按照 width 和 height 的值进行输出。
     * 取值为0时，宽高比例会简化为最简分数；取值为1时，输出图片的宽度等于 width，高度等于 height。默认值为0
     * 仅在 ciProcess=AIImageCrop 时生效
     */
    @XStreamOmitField
    private Integer fixed;

    // ============ 图像修复 ImageRepair 特有参数 ============
    /**
     * 遮罩（图像中需要去除的区域位置）图片地址，需要经过 URL 安全的 Base64 编码
     * 仅在 ciProcess=ImageRepair 时生效
     */
    @XStreamOmitField
    private String maskPic;

    /**
     * 遮罩多边形坐标，需要经过 URL 安全的 Base64 编码。MaskPoly 同时与 MaskPic 填写时，优先采用 MaskPic 的值
     * 仅在 ciProcess=ImageRepair 时生效
     */
    @XStreamOmitField
    private String maskPoly;

    // ============ 人脸特效 face-effect / 人脸智能美颜 face-beautify 特有参数 ============
    /**
     * 人脸特效类型：face-beautify（人脸美颜）、face-gender-transformation（人脸性别转换）、
     * face-age-transformation（人脸年龄变化）、face-segmentation（人像分割）
     * 仅在 ciProcess=face-effect 时生效
     */
    @XStreamOmitField
    private String type;

    /**
     * 美白程度，取值范围[0,100]。0不美白，100代表最高程度。默认值30
     * 仅在 ciProcess=face-effect 且 type=face-beautify 时生效
     */
    @XStreamOmitField
    private Integer whitening;

    /**
     * 磨皮程度，取值范围[0,100]。0不磨皮，100代表最高程度。默认值10
     * 仅在 ciProcess=face-effect 且 type=face-beautify 时生效
     */
    @XStreamOmitField
    private Integer smoothing;

    /**
     * 瘦脸程度，取值范围[0,100]。0不瘦脸，100代表最高程度。默认值70
     * 仅在 ciProcess=face-effect 且 type=face-beautify 时生效
     */
    @XStreamOmitField
    private Integer faceLifting;

    /**
     * 大眼程度，取值范围[0,100]。0不大眼，100代表最高程度。默认值70
     * 仅在 ciProcess=face-effect 且 type=face-beautify 时生效
     */
    @XStreamOmitField
    private Integer eyeEnlarging;

    /**
     * 选择转换方向，0：男变女，1：女变男。无默认值
     * 仅在 ciProcess=face-effect 且 type=face-gender-transformation 时生效
     */
    @XStreamOmitField
    private Integer gender;

    /**
     * 变化到的人脸年龄，取值范围为[10, 80]。无默认值
     * 仅在 ciProcess=face-effect 且 type=face-age-transformation 时生效
     */
    @XStreamOmitField
    private Integer age;

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

    public String getCiProcess() {
        return ciProcess;
    }

    public void setCiProcess(String ciProcess) {
        this.ciProcess = ciProcess;
    }

    public String getDetectUrl() {
        return detectUrl;
    }

    public void setDetectUrl(String detectUrl) {
        this.detectUrl = detectUrl;
    }

    public Integer getMagnify() {
        return magnify;
    }

    public void setMagnify(Integer magnify) {
        this.magnify = magnify;
    }

    public Integer getDenoise() {
        return denoise;
    }

    public void setDenoise(Integer denoise) {
        this.denoise = denoise;
    }

    public Integer getSharpen() {
        return sharpen;
    }

    public void setSharpen(Integer sharpen) {
        this.sharpen = sharpen;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public String getMaskPic() {
        return maskPic;
    }

    public void setMaskPic(String maskPic) {
        this.maskPic = maskPic;
    }

    public String getMaskPoly() {
        return maskPoly;
    }

    public void setMaskPoly(String maskPoly) {
        this.maskPoly = maskPoly;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWhitening() {
        return whitening;
    }

    public void setWhitening(Integer whitening) {
        this.whitening = whitening;
    }

    public Integer getSmoothing() {
        return smoothing;
    }

    public void setSmoothing(Integer smoothing) {
        this.smoothing = smoothing;
    }

    public Integer getFaceLifting() {
        return faceLifting;
    }

    public void setFaceLifting(Integer faceLifting) {
        this.faceLifting = faceLifting;
    }

    public Integer getEyeEnlarging() {
        return eyeEnlarging;
    }

    public void setEyeEnlarging(Integer eyeEnlarging) {
        this.eyeEnlarging = eyeEnlarging;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CreateAIObjectDetectJobRequest{" +
                "bucketName='" + bucketName + '\'' +
                ", objectKey='" + objectKey + '\'' +
                ", ciProcess='" + ciProcess + '\'' +
                ", detectUrl='" + detectUrl + '\'' +
                ", magnify=" + magnify +
                ", denoise=" + denoise +
                ", sharpen=" + sharpen +
                ", width=" + width +
                ", height=" + height +
                ", fixed=" + fixed +
                ", maskPic='" + maskPic + '\'' +
                ", maskPoly='" + maskPoly + '\'' +
                ", type='" + type + '\'' +
                ", whitening=" + whitening +
                ", smoothing=" + smoothing +
                ", faceLifting=" + faceLifting +
                ", eyeEnlarging=" + eyeEnlarging +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }
}