package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetHybridSearchRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetHybridSearchResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.Arrays;

/**
 * 多模态混合检索 Demo（V2.7.0 新增）。
 * <p>详情见 <a href="https://write.woa.com/document/200892020456558592">官方文档</a>。</p>
 *
 * <p>本 Demo 演示 3 种典型场景：</p>
 * <ol>
 *   <li>{@link #hybridSearchPicMode(COSClient)}：以图搜图，{@code Templates=ImageSearch} + {@code Mode=pic}</li>
 *   <li>{@link #hybridSearchTextMode(COSClient)}：文本搜文档，{@code Templates=DocSearch} + {@code Mode=text}</li>
 *   <li>{@link #hybridSearchWithFilter(COSClient)}：带 Filter 表达式的精细化检索</li>
 * </ol>
 */
public class HybridSearchDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法（默认演示 pic 模式，可切换为下方注释中的其他场景）。
        hybridSearchPicMode(client);
        // hybridSearchTextMode(client);
        // hybridSearchWithFilter(client);
    }

    /**
     * 场景一：以图搜图（Templates=ImageSearch + Mode=pic）。
     * <p>从数据集中检索与指定图片最相似的前 N 张图片。</p>
     */
    public static void hybridSearchPicMode(COSClient client) {
        DatasetHybridSearchRequest request = new DatasetHybridSearchRequest();
        request.setAppId("1250000000");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test");
        // 设置检索模板：ImageSearch 表示返回图像级结果。;是否必传：是
        request.setTemplates("ImageSearch");
        // 设置检索模式：pic 表示以图搜图。;是否必传：否（默认 pic）
        request.setMode("pic");
        // 设置待检索的图片 URI 列表。;是否必传：Mode=pic 时必传
        request.setSearchURIs(Arrays.asList("cos://<BucketName>/<ObjectKey>"));
        // 设置返回结果数量上限，默认 10，范围 (0,100]。;是否必传：否
        request.setLimit(10);
        // 设置相关度阈值，0-100，推荐 80。;是否必传：否
        request.setMatchThreshold(80);

        DatasetHybridSearchResponse response = client.hybridsearch(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * 场景二：以文搜文档（Templates=DocSearch + Mode=text）。
     * <p>使用一段文本检索数据集中相关的文档片段，返回 DocResult 列表。</p>
     */
    public static void hybridSearchTextMode(COSClient client) {
        DatasetHybridSearchRequest request = new DatasetHybridSearchRequest();
        request.setAppId("1250000000");
        request.setDatasetName("test");
        // DocSearch 模板用于返回文档级结果（DocResult[]）
        request.setTemplates("DocSearch");
        // 文本模式
        request.setMode("text");
        // 检索文本，长度 ≤ 60 UTF-8 字符
        request.setSearchText("2025年商品营销数据");
        request.setLimit(20);
        request.setMatchThreshold(70);

        DatasetHybridSearchResponse response = client.hybridsearch(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * 场景三：带 Filter 表达式的精细化检索。
     * <p>通过 Filter 嵌套 JSON 表达式，对元数据字段进行二次过滤，例如限定文件类型为 PDF 且大小 &gt; 1KB。</p>
     */
    public static void hybridSearchWithFilter(COSClient client) {
        DatasetHybridSearchRequest request = new DatasetHybridSearchRequest();
        request.setAppId("1250000000");
        request.setDatasetName("test");
        request.setTemplates("DocSearch");
        request.setMode("text");
        request.setSearchText("年度财务报告");
        request.setLimit(10);
        request.setFilter("{\"$and\":[{\"ContentType\":{\"$eq\":\"application/pdf\"}},{\"Size\":{\"$gt\":1024}}]}");

        DatasetHybridSearchResponse response = client.hybridsearch(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
