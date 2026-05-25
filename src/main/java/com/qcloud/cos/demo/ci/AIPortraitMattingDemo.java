package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.ai.AIPortraitMattingRequest;
import com.qcloud.cos.model.ciModel.ai.AIPortraitMattingResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 人像抠图 Demo
 * 详情见 https://cloud.tencent.com/document/product/460/79735
 * 人像抠图功能可以识别图片中的人像，并将人像与背景分离，生成透明背景的人像图片。
 */
public class AIPortraitMattingDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        portraitMattingWithCOSObject(client);
        portraitMattingWithExternalUrl(client);
    }

    /**
     * 使用COS中的图片进行人像抠图
     * 图片支持格式：PNG、JPG、JPEG。
     * 图片大小：所下载图片经 Base64 编码后不超过4MB。
     * 图片像素：建议大于50*50像素，否则影响识别效果。
     */
    public static void portraitMattingWithCOSObject(COSClient client) throws Exception {
        //1.创建任务请求对象
        AIPortraitMattingRequest request = new AIPortraitMattingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName("demo-1234567890");
        //2.2设置bucket中的图片位置
        request.setObjectKey("portrait.jpg");
        //2.3设置抠图主体居中显示（可选，1表示居中，0表示不处理，默认为0）
        request.setCenterLayout(1);
        //2.4设置留白（可选，格式为 widthxheight，例如 20x10 表示左右各留白20像素，上下各留白10像素）
        request.setPaddingLayout("20x10");
        
        //3.调用接口获取处理后的图片
        AIPortraitMattingResponse response = client.aiPortraitMatting(request);
        
        //4.保存处理后的图片到本地
        InputStream imageStream = response.getImageStream();
        FileOutputStream fos = new FileOutputStream(new File("portrait_matting_result.png"));
        byte[] buffer = new byte[1024];
        int len;
        while ((len = imageStream.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        imageStream.close();
        
        System.out.println("人像抠图完成，结果已保存到 portrait_matting_result.png");
        System.out.println("响应元数据: " + response.getMetadata());
    }

    /**
     * 使用外部URL进行人像抠图
     */
    public static void portraitMattingWithExternalUrl(COSClient client) throws Exception {
        //1.创建任务请求对象
        AIPortraitMattingRequest request = new AIPortraitMattingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName("demo-1234567890");
        //2.2设置图片url（SDK会自动进行URL编码，不需要手动编码）
        String externalUrl = "https://demo-1234567890.cos.ap-chongqing.myqcloud.com/portrait.jpg";
        request.setDetectUrl(externalUrl);
        //2.3设置抠图主体居中显示
        request.setCenterLayout(1);
        
        //3.调用接口获取处理后的图片
        AIPortraitMattingResponse response = client.aiPortraitMatting(request);
        
        //4.保存处理后的图片到本地
        InputStream imageStream = response.getImageStream();
        FileOutputStream fos = new FileOutputStream(new File("portrait_matting_url_result.png"));
        byte[] buffer = new byte[1024];
        int len;
        while ((len = imageStream.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        imageStream.close();
        
        System.out.println("人像抠图完成，结果已保存到 portrait_matting_url_result.png");
    }
}
