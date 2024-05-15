//package com.qcloud.cos.demo.ci;
//
//import com.qcloud.cos.COSClient;
//import com.qcloud.cos.exception.CosServiceException;
//import com.qcloud.cos.model.ciModel.image.DetectFaceRequest;
//import com.qcloud.cos.model.ciModel.image.DetectFaceResponse;
//import com.qcloud.cos.utils.Jackson;
//
//
//public class DetectFaceDemo {
//
//    public static void main(String[] args) throws Exception {
//        COSClient cosClient = ClientUtils.getTestClient();
//        detectFace(cosClient);
//        cosClient.shutdown();
//    }
//
//    /**
//     * 人脸检测 DetectFace https://cloud.tencent.com/document/product/460/63223
//     * 图片支持格式：PNG、JPG、JPEG、BMP。
//     * 图片大小：所下载图片经 Base64 编码后不超过5MB。
//     * 图片像素：JPG 格式长边像素不可超过4000，其他格式图片长边像素不可超过2000。
//     */
//    public static void detectFace(COSClient cosClient) {
//        try {
//            //1.创建二维码生成请求对象
//            DetectFaceRequest request = new DetectFaceRequest();
//            //2.添加请求参数 参数详情请见api接口文档
//            request.setBucketName("demo-1234567890");
//            request.setObjectKey("1.jpg");
////            request.setDetectUrl("https://demo-1234567890.cos.ap-chongqing.myqcloud.com/1.jpg");
//            DetectFaceResponse response = cosClient.detectFace(request);
//            System.out.println(Jackson.toJsonString(response));
//        } catch (CosServiceException exception) {
//            if (exception.getMessage().startsWith("Image not supported")) {
//                //执行未识别人脸逻辑
//
//            } else {
//                //其他服务异常 图片大小超过5MB后会下载失败
//                System.out.println(exception.getMessage());
//            }
//        }
//
//    }
//
//
//}
