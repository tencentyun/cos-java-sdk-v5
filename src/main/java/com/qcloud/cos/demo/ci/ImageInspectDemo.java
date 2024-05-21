package com.qcloud.cos.demo.ci;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.image.ImageInspectRequest;
import com.qcloud.cos.model.ciModel.image.ImageInspectResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelRequest;
import com.qcloud.cos.model.ciModel.image.ImageLabelResponse;
import com.qcloud.cos.utils.Jackson;

import java.io.UnsupportedEncodingException;

/**
 * 异常图片检测demo https://cloud.tencent.com/document/product/460/75997
 */
public class ImageInspectDemo {

    public static void main(String[] args) throws JsonProcessingException, UnsupportedEncodingException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        getImageInspect(client);
    }

    /**
     * getImageInspect 检测图片中是否隐含其他类型的可疑文件。
     */
    public static void getImageInspect(COSClient client)  {
        //1.创建任务请求对象
        ImageInspectRequest request = new ImageInspectRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        //2.1图片位置
        request.setObjectKey("test/1.jpg");
        //3.调用接口,获取任务响应对象
        ImageInspectResponse response = client.getImageInspect(request);
        System.out.println(Jackson.toJsonString(response));
    }

}
