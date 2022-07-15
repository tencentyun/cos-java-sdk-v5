package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.GetObjectRequest;

import java.io.File;

/**
 * 基础图片处理使用Demo https://cloud.tencent.com/document/product/460/36540
 * Demo均为下载时处理，如需上传时处理或云上处理则使用对应方法，图片处理规则相同。
 * 此处为演示参数使用，完整处理规则请参考API文档
 */
public class BasicImageProcessing {

    public static void main(String[] args) throws Exception {
        COSClient cosClient = ClientUtils.getTestClient();
        imageRotateDemo(cosClient);
        cosClient.shutdown();
    }

    public static void imageProcessing(COSClient cosClient, String rule) {
        //图片所在bucket名称
        String bucketName = "examplebucket-1250000000";
        //图片在bucket中的相对位置，比如根目录下file文件夹中的demo.png路径为file/demo.png
        String key = "image.png";
        GetObjectRequest getObj = new GetObjectRequest(bucketName, key);
        getObj.putCustomQueryParameter(rule, null);
        cosClient.getObject(getObj, new File("demo.png"));
    }

    /**
     * 缩放图片宽高为原图50%
     * https://cloud.tencent.com/document/product/460/36540
     */
    public static void imageZoomDemo(COSClient cosClient) {
        String rule = "imageMogr2/thumbnail/!50p";
        imageProcessing(cosClient, rule);
    }

    /**
     * 内切圆裁剪功能，radius是内切圆的半径150
     * https://cloud.tencent.com/document/product/460/36541
     */
    public static void imageCroppingDemo(COSClient cosClient) {
        String rule = "imageMogr2/iradius/150";
        imageProcessing(cosClient, rule);
    }

    /**
     * 图片顺时针旋转角度90  取值范围0 - 360
     * https://cloud.tencent.com/document/product/460/36542
     */
    public static void imageRotateDemo(COSClient cosClient) {
        String rule = "imageMogr2/rotate/90";
        imageProcessing(cosClient, rule);
    }

    /**
     * 将图片转换为 png 格式
     * https://cloud.tencent.com/document/product/460/36543
     */
    public static void imageConvertingFormatDemo(COSClient cosClient) {
        String rule = "imageMogr2/format/png";
        imageProcessing(cosClient, rule);
    }

    /**
     * 设置图片的绝对质量为60
     * https://cloud.tencent.com/document/product/460/36544
     */
    public static void imageQualityChangeDemo(COSClient cosClient) {
        String rule = "imageMogr2/quality/60";
        imageProcessing(cosClient, rule);
    }

    /**
     * 模糊半径取8，sigma 值取5，进行高斯模糊处理
     * https://cloud.tencent.com/document/product/460/36545
     */
    public static void imageGaussianBlurringDemo(COSClient cosClient) {
        String rule = "imageMogr2/blur/8x5";
        imageProcessing(cosClient, rule);
    }

    /**
     * 将图片亮度提高70
     * https://cloud.tencent.com/document/product/460/51808
     */
    public static void imageAdjustingBrightnessDemo(COSClient cosClient) {
        String rule = "imageMogr2/bright/70";
        imageProcessing(cosClient, rule);
    }

    /**
     * 将图片对比度降低50
     * https://cloud.tencent.com/document/product/460/51809
     */
    public static void imageAdjustingContrastDemo(COSClient cosClient) {
        String rule = "imageMogr2/contrast/-50";
        imageProcessing(cosClient, rule);
    }

    /**
     * 设置锐化参数为70
     * https://cloud.tencent.com/document/product/460/51809
     */
    public static void imageSharpeningDemo(COSClient cosClient) {
        String rule = "imageMogr2/sharpen/70";
        imageProcessing(cosClient, rule);
    }

    /**
     * 设图片变为灰度图
     * https://cloud.tencent.com/document/product/460/66519
     */
    public static void grayscaleImageDemo(COSClient cosClient) {
        String rule = "imageMogr2/grayscale/1";
        imageProcessing(cosClient, rule);
    }

    /**
     * 设置水印图片,并指定水印位置
     * https://cloud.tencent.com/document/product/460/51809
     */
    public static void imageWatermarkingDemo(COSClient cosClient) {
        String rule = "watermark/1/image/aHR0cDovL2V4YW1wbGVzLTEyNTEwMDAwMDQucGljc2gubXlxY2xvdWQuY29tL3NodWl5aW4uanBn/gravity/southeast";
        imageProcessing(cosClient, rule);
    }

    /**
     * 设置水印文字,并指定水印位置
     * https://cloud.tencent.com/document/product/460/6951
     */
    public static void textWatermarkingDemo(COSClient cosClient) {
        String rule = "watermark/2/text/6IW-6K6v5LqRwrfkuIfosaHkvJjlm74/fill/IzNEM0QzRA/fontsize/20/dissolve/50/gravity/northeast/dx/20/dy/20/batch/1/degree/45";
        imageProcessing(cosClient, rule);
    }

    /**
     * 去除图片元信息
     * https://cloud.tencent.com/document/product/460/36547
     */
    public static void removingImageMetadata(COSClient cosClient) {
        String rule = "imageMogr2/strip";
        imageProcessing(cosClient, rule);
    }

    /**
     * 选用样式1，并限定缩略图的宽高最小值为400 × 600 绝对质量为85
     * https://cloud.tencent.com/document/product/460/6929
     */
    public static void quickThumbnailTemplate(COSClient cosClient) {
        String rule = "imageView2/1/w/400/h/600/q/85";
        imageProcessing(cosClient, rule);
    }

    /**
     * 将 JPG 图片转换为 PNG 格式，并限制图片大小为15KB
     * https://cloud.tencent.com/document/product/460/56732
     */
    public static void limitingOutputImageSize(COSClient cosClient) {
        String rule = "imageMogr2/strip/format/png/size-limit/15k!";
        imageProcessing(cosClient, rule);
    }

    /**
     * 管道操作符|能够实现对图片按顺序进行多种处理。
     * 用户可以通过管道操作符将多个处理参数分隔开，从而实现在一次访问中按顺序对图片进行不同处理
     * https://cloud.tencent.com/document/product/460/15293
     */
    public static void pipelineOperatorsDemo(COSClient cosClient) {
        //对缩放后的图片进行文字水印操作
        String rule = "imageMogr2/thumbnail/!50p|watermark/2/text/5pWw5o2u5LiH6LGh/fill/I0ZGRkZGRg==/fontsize/30/dx/20/dy/20";
        imageProcessing(cosClient, rule);
    }


    public static void obtainingImageAverageHueDemo(COSClient cosClient) {
        String rule = "imageMogr2/contrast/-50";
        imageProcessing(cosClient, rule);
    }

    public static void obtainingBasicImageInformationDemo(COSClient cosClient) {
        String rule = "imageMogr2/contrast/-50";
        imageProcessing(cosClient, rule);
    }

    public static void obtainingImageEXIFDemo(COSClient cosClient) {
        String rule = "imageMogr2/contrast/-50";
        imageProcessing(cosClient, rule);
    }

}
