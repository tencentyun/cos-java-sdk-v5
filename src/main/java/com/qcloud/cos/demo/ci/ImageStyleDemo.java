package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.image.ImageStyleRequest;
import com.qcloud.cos.model.ciModel.image.ImageStyleResponse;
import com.qcloud.cos.model.ciModel.image.StyleRule;

import java.util.List;


/**
 * 图片样式接口 demo
 * 接口详情见 https://cloud.tencent.com/document/product/460/30118
 */
public class ImageStyleDemo {
    public static void main(String[] args) throws Exception {
        COSClient cosClient = ClientUtils.getTestClient();
        // 小于5GB文件用简单上传
        addImageStyle(cosClient);
        cosClient.shutdown();
    }

    /**
     * 添加图片处理样式
     * https://cloud.tencent.com/document/product/460/53491
     */
    public static void addImageStyle(COSClient cosClient) {
        //1.创建二维码生成请求对象
        ImageStyleRequest request = new ImageStyleRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("examplebucket-1250000000");
        request.setStyleName("DemoStyle");
        //设置样式规则，demo此处处理规则含义为：缩放图片宽高为原图50%
        request.setStyleBody("imageMogr2/thumbnail/!50p");
        Boolean response = cosClient.addImageStyle(request);
    }

    /**
     * 查询图片处理样式
     * https://cloud.tencent.com/document/product/460/30117
     */
    public static void getImageStyle(COSClient cosClient) {
        //1.创建二维码生成请求对象
        ImageStyleRequest request = new ImageStyleRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("examplebucket-1250000000");
        request.setStyleName("DemoStyle");
        ImageStyleResponse response = cosClient.getImageStyle(request);
        List<StyleRule> styleRule = response.getStyleRule();
        for (StyleRule rule : styleRule) {
            System.out.println(rule.getStyleName());
            System.out.println(rule.getStyleBody());
        }
    }

    /**
     * 查询图片处理样式
     * https://cloud.tencent.com/document/product/460/30117
     */
    public static void deleteImageStyle(COSClient cosClient) {
        //1.创建二维码生成请求对象
        ImageStyleRequest request = new ImageStyleRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("examplebucket-1250000000");
        request.setStyleName("DemoStyle");
        //设置样式规则，demo此处处理规则含义为：缩放图片宽高为原图50%
        request.setStyleBody("imageMogr2/thumbnail/!50p");
        Boolean response = cosClient.deleteImageStyle(request);
    }


}
