package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.image.AutoTranslationBlockRequest;
import com.qcloud.cos.model.ciModel.image.AutoTranslationBlockResponse;


public class AutoTranslationBlockDemo {

    /**
     * autoTranslationBlock
     * 实时文字翻译 https://cloud.tencent.com/document/product/460/83547
     * 中文（代码zh）与下列语言之间的双向互译：
     * 英语 en 阿拉伯语 ar 德语 de 西班牙语 es 法语 fr 印尼语 id 意大利语 it / 日语 ja
     * 葡萄牙语 pt 俄语 ru 韩语 ko 高棉语 km 老挝语 lo
     * <p>
     * 英文（代码en）与下列语言之间的双向互译：
     * 中文 zh 阿拉伯语 ar 德语 de 西班牙语 es 法语 fr 印尼语 id 意大利语 it / 日语 ja
     * 葡萄牙语 pt 俄语 ru 韩语 ko 高棉语 km 老挝语 lo
     * 其中，中文除了常规的简体中文（zh）外，还有繁体中文（zh-tr）、中国香港繁体中文（zh-hk）、中国台湾繁体中文（zh-tw）。
     * <p>
     * 通过text_domain和text_style参数获得相应能力：
     * (1) 电商：textDomain=ecommerce
     * <p>
     * (2) 电商标题: textDomain=ecommerce；text_style=title
     * <p>
     * (3) 网络文学：textDomain=net-literature
     */
    public static void autoTranslationBlock(COSClient cosClient) {
        //1.创建二维码生成请求对象
        AutoTranslationBlockRequest request = new AutoTranslationBlockRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567891");
        //2.1 待翻译的文本 必填
        request.setInputText("数据万象");
        //2.2 输入语言 必填
        request.setSourceLang("zh");
        //2.3 输出语言 必填
        request.setTargetLang("en");
        AutoTranslationBlockResponse response = cosClient.autoTranslationBlock(request);
        String translationResult = response.getTranslationResult();
        System.out.println(translationResult);
        System.out.println(response.getRequestId());
    }

    public static void main(String[] args) throws Exception {
        COSClient cosClient = ClientUtils.getTestClient();
        autoTranslationBlock(cosClient);
        cosClient.shutdown();
    }

}
