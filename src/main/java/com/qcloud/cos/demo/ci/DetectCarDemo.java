package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.persistence.AIRecRequest;
import com.qcloud.cos.model.ciModel.persistence.DetectCarResponse;
import com.qcloud.cos.utils.Jackson;

/**
 * 车辆识别demo https://cloud.tencent.com/document/product/460/63225
 * 车辆车牌检测功能为同步请求方式，您可以通过本接口检测图片中的车辆，识别出车辆的品牌、颜色、位置、车牌位置等信息。该接口属于 GET 请求。
 */
public class DetectCarDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        detectCar(client);
    }

    /**
     * 车辆识别接口demo
     * 图片支持格式：PNG、JPG、JPEG。
     * 图片大小：所下载图片经 Base64 编码后不超过4MB。
     * 图片像素：建议大于50*50像素，否则影响识别效果。
     * 长宽比：建议长边与短边的比例小于5：1。
     * ObjectKey 对象位置或DetectUrl 图片url 需二选一
     */
    public static void detectCar(COSClient client) {
        //1.创建任务请求对象
        AIRecRequest request = new AIRecRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName("demo-1234567890");
        //2.2设置bucket中的图片位置
//        request.setObjectKey("car.jpg");
        //2.3或设置图片url
        request.setDetectUrl("https://demo-1234567890.cos.ap-chongqing.myqcloud.com/car.jpg");
        DetectCarResponse response = client.detectCar(request);
        System.out.println(Jackson.toJsonString(response));
    }

}
