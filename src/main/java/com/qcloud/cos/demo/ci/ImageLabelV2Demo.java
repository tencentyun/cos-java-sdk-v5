package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Request;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Response;
import com.qcloud.cos.model.ciModel.image.LabelV2;
import com.qcloud.cos.model.ciModel.image.LocationLabel;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 图片标签V2接口使用demo
 * 接口文档: https://cloud.tencent.com/document/product/460/39082
 *
 * V2版本相比V1版本，支持更多场景（web/camera/album/news/nonecam/product），
 * 返回结构更丰富，包含一级分类、二级分类和置信度信息。
 */
public class ImageLabelV2Demo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        getImageLabelV2(client);
        getImageLabelV2MultiScenes(client);
    }

    /**
     * getImageLabelV2 图片标签V2（单场景）
     * 返回图片中置信度较高的主题标签，支持web/camera/album/news/nonecam/product等场景。
     *
     * @param client COSClient实例
     */
    public static void getImageLabelV2(COSClient client) {
        //1.创建任务请求对象
        ImageLabelV2Request request = new ImageLabelV2Request();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-123456789");
        //2.1设置图片在COS中的位置
        request.setObjectKey("test/1.jpg");
        //2.2设置识别场景（可选值：web/camera/album/news/nonecam/product，默认为web）
        request.setScenes("web");
        //3.调用接口,获取任务响应对象
        ImageLabelV2Response response = client.getImageLabelV2(request);
        System.out.println(Jackson.toJsonString(response));

        //4.处理响应结果
        List<LabelV2> webLabels = response.getWebLabels();
        if (webLabels != null && !webLabels.isEmpty()) {
            System.out.println("=== Web场景标签结果 ===");
            for (LabelV2 label : webLabels) {
                System.out.println("标签名: " + label.getName()
                        + ", 置信度: " + label.getConfidence()
                        + ", 一级分类: " + label.getFirstCategory()
                        + ", 二级分类: " + label.getSecondCategory());
            }
        }
    }

    /**
     * getImageLabelV2 图片标签V2（多场景同时检测）
     * 支持多场景一起检测，例如 scenes=web,camera 即对一张图片使用两个模型同时检测，输出两套识别结果。
     *
     * @param client COSClient实例
     */
    public static void getImageLabelV2MultiScenes(COSClient client) {
        //1.创建任务请求对象
        ImageLabelV2Request request = new ImageLabelV2Request();
        //2.添加请求参数
        request.setBucketName("demo-123456789");
        request.setObjectKey("test/1.jpg");
        //2.1设置多个识别场景，用逗号分隔
        request.setScenes("web,camera,album,news");
        //3.调用接口,获取任务响应对象
        ImageLabelV2Response response = client.getImageLabelV2(request);
        System.out.println(Jackson.toJsonString(response));

        //4.分别处理各场景的结果
        // Web场景
        List<LabelV2> webLabels = response.getWebLabels();
        if (webLabels != null && !webLabels.isEmpty()) {
            System.out.println("=== Web场景标签 ===");
            for (LabelV2 label : webLabels) {
                System.out.println("  " + label.getName() + " (置信度: " + label.getConfidence() + ")");
            }
        }

        // Camera场景
        List<LabelV2> cameraLabels = response.getCameraLabels();
        if (cameraLabels != null && !cameraLabels.isEmpty()) {
            System.out.println("=== Camera场景标签 ===");
            for (LabelV2 label : cameraLabels) {
                System.out.println("  " + label.getName() + " (置信度: " + label.getConfidence() + ")");
            }
        }

        // Album场景
        List<LabelV2> albumLabels = response.getAlbumLabels();
        if (albumLabels != null && !albumLabels.isEmpty()) {
            System.out.println("=== Album场景标签 ===");
            for (LabelV2 label : albumLabels) {
                System.out.println("  " + label.getName() + " (置信度: " + label.getConfidence() + ")");
            }
        }

        // News场景
        List<LabelV2> newsLabels = response.getNewsLabels();
        if (newsLabels != null && !newsLabels.isEmpty()) {
            System.out.println("=== News场景标签 ===");
            for (LabelV2 label : newsLabels) {
                System.out.println("  " + label.getName() + " (置信度: " + label.getConfidence() + ")");
            }
        }

        // Product场景（主体位置识别，返回LocationLabel）
        List<LocationLabel> productLabels = response.getProductLabels();
        if (productLabels != null && !productLabels.isEmpty()) {
            System.out.println("=== Product场景标签 ===");
            for (LocationLabel label : productLabels) {
                System.out.println("  " + label.getName() + " (置信度: " + label.getConfidence() + ")");
            }
        }
    }
}
