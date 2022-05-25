package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.image.ImageSearchRequest;
import com.qcloud.cos.model.ciModel.image.ImageSearchResponse;
import com.qcloud.cos.model.ciModel.image.OpenImageSearchRequest;

/**
 * 以图搜图接口使用demo https://cloud.tencent.com/document/product/460/63899
 */
public class ImageSearchDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        openImageSearch(client);
    }

    /**
     * openImageSearch 开通以图搜图服务
     *
     * @param client
     */
    public static void openImageSearch(COSClient client)  {
        //1.创建任务请求对象
        OpenImageSearchRequest request = new OpenImageSearchRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("chongqingtest-1251704708");
        request.setMaxCapacity("100");
        request.setMaxQps("10");
        //3.调用接口,获取任务响应对象
        boolean response = client.openImageSearch(request);
    }

    /**
     * addGalleryImages 添加图库图片
     *
     * @param client
     */
    public static void addGalleryImages(COSClient client)  {
        //1.创建任务请求对象
        ImageSearchRequest request = new ImageSearchRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("chongqingtest-1251704708");
        //3.调用接口,获取任务响应对象
        boolean response = client.addGalleryImages(request);
    }

    /**
     * deleteGalleryImages 删除图库图片
     *
     * @param client
     */
    public static void deleteGalleryImages(COSClient client)  {
        //1.创建任务请求对象
        ImageSearchRequest request = new ImageSearchRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("chongqingtest-1251704708");
        //3.调用接口,获取任务响应对象
        boolean response = client.deleteGalleryImages(request);
    }

    /**
     * searchGalleryImages 图片搜索 该接口用于检索图片
     *
     * @param client
     */
    public static void searchGalleryImages(COSClient client)  {
        //1.创建任务请求对象
        ImageSearchRequest request = new ImageSearchRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("chongqingtest-1251704708");
        //3.调用接口,获取任务响应对象
        ImageSearchResponse response = client.searchGalleryImages(request);
    }

}
