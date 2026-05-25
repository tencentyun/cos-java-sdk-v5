package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.ai.CreateAIObjectDetectJobRequest;
import com.qcloud.cos.model.ciModel.ai.CreateAIObjectDetectJobResponse;
import com.qcloud.cos.utils.Jackson;

/**
 * AI 内容识别通用接口 Demo
 * 通过 createAIObjectDetectJob 方法，设置不同的 ciProcess 参数，可以调用以下功能：
 * - AIObjectDetect: 图像主体检测 https://cloud.tencent.com/document/product/460/106462
 * - AISuperResolution: 图像超分 https://cloud.tencent.com/document/product/460/83793
 * - AIEnhanceImage: 图像增强 https://cloud.tencent.com/document/product/460/83792
 * - AIImageCrop: 图像智能裁剪 https://cloud.tencent.com/document/product/460/83791
 * - ImageRepair: 图像修复 https://cloud.tencent.com/document/product/460/79042
 * - AIImageQuality: 图片质量评分 https://cloud.tencent.com/document/product/460/63228
 * - face-effect: 人脸特效 https://cloud.tencent.com/document/product/460/47197
 * - AIBodyRecognition: 人体识别 https://cloud.tencent.com/document/product/460/83196
 * - face-beautify: 人脸智能美颜 https://cloud.tencent.com/document/product/460/102258
 */
public class CreateAIObjectDetectJobDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        detectObjectWithCOSObject(client);
//        detectObjectWithExternalUrl(client);
//        superResolution(client);
//        enhanceImage(client);
//        imageCrop(client);
//        faceBeautify(client);
//        faceGenderTransformation(client);
//        faceAgeTransformation(client);
//        imageQuality(client);
//        bodyRecognition(client);
    }

    /**
     * 图像主体检测 - 使用COS中的图片
     * 图片支持格式：PNG、JPG、JPEG。
     * 图片大小：所下载图片经 Base64 编码后不超过4MB。
     * 图片像素：建议大于50*50像素，否则影响识别效果。
     * ObjectKey 对象位置或DetectUrl 图片url 需二选一
     */
    public static void detectObjectWithCOSObject(COSClient client) {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("test-image.jpg");
        // ciProcess 默认为 AIObjectDetect，无需额外设置

        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("AI Object Detect Response: " + Jackson.toJsonString(response));
    }

    /**
     * 图像主体检测 - 使用外部URL
     */
    public static void detectObjectWithExternalUrl(COSClient client) {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName("demo-1234567890");
        // 设置图片url（SDK会自动进行URL编码，不需要手动编码）
        request.setDetectUrl("https://demo-1234567890.cos.ap-chongqing.myqcloud.com/test-image.jpg");

        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("AI Object Detect with URL Response: " + Jackson.toJsonString(response));
    }

    /**
     * 图像超分 - 将图片放大到指定倍数
     * ciProcess: AISuperResolution
     * 特有参数: magnify - 放大倍数，支持2、4，默认为2
     */
    public static void superResolution(COSClient client) {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("low-res-image.jpg");
        request.setCiProcess("AISuperResolution");
        // 设置放大倍数为4倍
        request.setMagnify(4);

        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("Super Resolution Response: " + Jackson.toJsonString(response));
    }

    /**
     * 图像增强 - 对图像进行去噪和锐化处理
     * ciProcess: AIEnhanceImage
     * 特有参数: denoise - 去噪强度(0-10)，sharpen - 锐化强度(0-10)
     */
    public static void enhanceImage(COSClient client) {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("noisy-image.jpg");
        request.setCiProcess("AIEnhanceImage");
        // 设置去噪强度为5
        request.setDenoise(5);
        // 设置锐化强度为6
        request.setSharpen(6);

        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("Enhance Image Response: " + Jackson.toJsonString(response));
    }

    /**
     * 图像智能裁剪 - 按指定宽高比例智能裁剪图片
     * ciProcess: AIImageCrop
     * 特有参数: width - 裁剪宽度, height - 裁剪高度, fixed - 是否严格按宽高输出(0或1)
     */
    public static void imageCrop(COSClient client) {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("landscape.jpg");
        request.setCiProcess("AIImageCrop");
        // 设置裁剪宽高比例为 16:9
        request.setWidth(16);
        request.setHeight(9);
        // 设置为严格按照宽高输出
        request.setFixed(1);

        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("Image Crop Response: " + Jackson.toJsonString(response));
    }

    /**
     * 人脸美颜 - 对人脸进行美白、磨皮、瘦脸、大眼处理
     * ciProcess: face-effect, type: face-beautify
     * 特有参数: whitening(0-100), smoothing(0-100), faceLifting(0-100), eyeEnlarging(0-100)
     */
    public static void faceBeautify(COSClient client) {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("portrait.jpg");
        request.setCiProcess("face-effect");
        request.setType("face-beautify");
        // 美白程度 0-100，默认30
        request.setWhitening(50);
        // 磨皮程度 0-100，默认10
        request.setSmoothing(30);
        // 瘦脸程度 0-100，默认70
        request.setFaceLifting(60);
        // 大眼程度 0-100，默认70
        request.setEyeEnlarging(50);

        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("Face Beautify Response: " + Jackson.toJsonString(response));
    }

    /**
     * 人脸性别转换 - 将人脸进行性别转换
     * ciProcess: face-effect, type: face-gender-transformation
     * 特有参数: gender - 转换方向(0:男变女, 1:女变男)
     */
    public static void faceGenderTransformation(COSClient client) {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("portrait.jpg");
        request.setCiProcess("face-effect");
        request.setType("face-gender-transformation");
        // 0: 男变女, 1: 女变男
        request.setGender(0);

        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("Face Gender Transformation Response: " + Jackson.toJsonString(response));
    }

    /**
     * 人脸年龄变化 - 将人脸变化到指定年龄
     * ciProcess: face-effect, type: face-age-transformation
     * 特有参数: age - 目标年龄(10-80)
     */
    public static void faceAgeTransformation(COSClient client) {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("portrait.jpg");
        request.setCiProcess("face-effect");
        request.setType("face-age-transformation");
        // 变化到的人脸年龄，取值范围 10-80
        request.setAge(25);

        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("Face Age Transformation Response: " + Jackson.toJsonString(response));
    }

    /**
     * 图片质量评分 - 对图片进行质量评估
     * ciProcess: AIImageQuality
     * 无额外特有参数
     */
    public static void imageQuality(COSClient client) {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("test-image.jpg");
        request.setCiProcess("AIImageQuality");

        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("Image Quality Response: " + Jackson.toJsonString(response));
    }

    /**
     * 人体识别 - 识别图片中的人体
     * ciProcess: AIBodyRecognition
     * 无额外特有参数
     */
    public static void bodyRecognition(COSClient client) {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("group-photo.jpg");
        request.setCiProcess("AIBodyRecognition");

        CreateAIObjectDetectJobResponse response = client.createAIObjectDetectJob(request);
        System.out.println("Body Recognition Response: " + Jackson.toJsonString(response));
    }
}
