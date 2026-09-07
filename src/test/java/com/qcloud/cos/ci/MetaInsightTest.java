package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ciModel.metaInsight.CancelDatasetExportJobRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CancelDatasetExportJobResponse;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetBindingRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetBindingResponse;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetExportJobRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetExportJobResponse;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetResponse;
import com.qcloud.cos.model.ciModel.metaInsight.CreateFileMetaIndexRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateFileMetaIndexResponse;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetExportOutput;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetFaceSearchRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetFaceSearchResponse;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetHybridSearchRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetHybridSearchResponse;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetExportJobRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetExportJobResponse;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetExportJobsRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetExportJobsResponse;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeFileMetaIndexRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeFileMetaIndexResponse;
import com.qcloud.cos.model.ciModel.metaInsight.File;
import com.qcloud.cos.model.ciModel.metaInsight.SearchImageRequest;
import com.qcloud.cos.model.ciModel.metaInsight.SearchImageResponse;
import com.qcloud.cos.model.ciModel.metaInsight.UpdateFileMetaIndexRequest;
import com.qcloud.cos.model.ciModel.metaInsight.UpdateFileMetaIndexResponse;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.CIJackson;
import com.qcloud.cos.utils.Jackson;
import org.junit.BeforeClass;
import org.junit.Test;


public class MetaInsightTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-beijing");
        ClientConfig clientConfig = new ClientConfig(region);
        cosclient = new COSClient(cred, clientConfig);
    }

    @Test
    public void testCreateDataset() {
        try {
            CreateDatasetBindingRequest request = new CreateDatasetBindingRequest();
            request.setAppId(appid);
            request.setDatasetName("test");
            request.setURI("cos://" + bucket);
            CreateDatasetBindingResponse response = cosclient.createDatasetBinding(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createDataset() {
        try {
            CreateDatasetRequest request = new CreateDatasetRequest();
            request.setAppId("1251704708");
            request.setDatasetName("mark");
            request.setDescription("test");
            request.setTemplateId("Official:COSBasicMeta");
            CreateDatasetResponse response = cosclient.createDataset(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
        }
    }

    @Test
    public void datasetFaceSearchTest() {
        try {
            DatasetFaceSearchRequest request = new DatasetFaceSearchRequest();
            request.setAppId(appid);
            request.setDatasetName("test");
            request.setURI("cos://" + bucket + "/2.jpg");
            request.setMaxFaceNum(1);
            request.setLimit(10);
            request.setMatchThreshold(10);
            DatasetFaceSearchResponse response = cosclient.datasetFaceSearch(request);
        } catch (Exception e) {

        }
    }

    @Test
    public void createFileMetaIndex() {
        try {
            CreateFileMetaIndexRequest request = new CreateFileMetaIndexRequest();
            request.setAppId(appid);
            // 设置数据集名称，同一个账户下唯一。;是否必传：是
            request.setDatasetName("test");
            File file = new File();
            file.setURI("cos://" + bucket + "/2.jpg");
            request.setFile(file);
            System.out.println(CIJackson.toJsonString(request));
            CreateFileMetaIndexResponse response = cosclient.createFileMetaIndex(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void describeFileMetaIndexTest() {
        try {
            DescribeFileMetaIndexRequest request = new DescribeFileMetaIndexRequest();
            request.setAppId(appid);
            request.setDatasetname("test");
            request.setUri("cos://" + bucket + "/2.jpg");
            DescribeFileMetaIndexResponse response = cosclient.describeFileMetaIndex(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateFileMetaIndexTest() {
        try {
            UpdateFileMetaIndexRequest request = new UpdateFileMetaIndexRequest();
            request.setAppId(appid);
            // 设置数据集名称，同一个账户下唯一。;是否必传：是
            request.setDatasetName("test");
            File file = new File();
            file.setURI("cos://" + bucket + "/2.jpg");
            request.setFile(file);
            UpdateFileMetaIndexResponse response = cosclient.updateFileMetaIndex(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchImageTest() {
        try {
            SearchImageRequest request = new SearchImageRequest();
            request.setAppId(appid);
            request.setDatasetName("test");
            request.setMode("pic");
            request.setURI("cos://" + bucket + "/2.jpg");
            request.setLimit(10);
            request.setMatchThreshold(1);
            SearchImageResponse response = cosclient.searchImage(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========================================================================
    // V2.7.0 文档检索能力迭代：hybridsearch 多模态混合检索
    // ========================================================================

    /**
     * 多模态混合检索 hybridsearch 接口集成测试。
     * 场景：pic 模式（以图搜图 + 含 DocSearch=true 推动文档检索分支）。
     * 需要环境变量 ciSecretId/ciSecretKey/ciBucket/ciRegion。
     */
    @Test
    public void datasetHybridSearchTest() {
        try {
            DatasetHybridSearchRequest request = new DatasetHybridSearchRequest();
            request.setAppId(appid);
            request.setDatasetName("test");
            request.setMode("pic");
            request.setSearchURIs(java.util.Arrays.asList("cos://" + bucket + "/2.jpg"));
            request.setTemplates("ImageSearch");
            request.setLimit(10);
            request.setMatchThreshold(1);
            DatasetHybridSearchResponse response = cosclient.hybridsearch(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hybridsearch 带 Filter 标量过滤的集成测试（text 模式 + DocSearch 模板）。
     * 验证 @JsonRawValue 修复后 Filter 以 JSON 对象形式传输、后端能正确解析过滤条件。
     * 需要环境变量 ciSecretId/ciSecretKey/ciBucket/ciRegion。
     */
    @Test
    public void datasetHybridSearchWithFilterTest() {
        try {
            DatasetHybridSearchRequest request = new DatasetHybridSearchRequest();
            request.setAppId(appid);
            request.setDatasetName("test");
            request.setMode("text");
            request.setTemplates("DocSearch");
            request.setSearchText("2025年商品营销数据");
            request.setLimit(10);
            request.setMatchThreshold(1);
            request.setFilter("{\"$and\":[{\"MediaType\":{\"$in\":[\"image\",\"document\"]}},{\"Size\":{\"$gt\":123}}]}");
            // 打印实际请求体，确认 Filter 是 JSON 对象而非转义字符串
            System.out.println(CIJackson.toJsonString(request));
            DatasetHybridSearchResponse response = cosclient.hybridsearch(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========================================================================
    // V2.7.0 数据集异步导出：CreateDatasetExportJob / DescribeDatasetExportJob
    //         / DescribeDatasetExportJobs / CancelDatasetExportJob
    // ========================================================================

    /**
     * 创建数据集导出任务集成测试。ExportJobType=1（数据导出）+ output 输出目标。
     */
    @Test
    public void createDatasetExportJobTest() {
        try {
            CreateDatasetExportJobRequest request = new CreateDatasetExportJobRequest();
            request.setAppId(appid);
            request.setDatasetName("test");
            request.setName("export-job-it");
            request.setExportJobType(1);
            DatasetExportOutput output = new DatasetExportOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("export/result_it.json");
            request.setOutput(output);
            CreateDatasetExportJobResponse response = cosclient.createDatasetExportJob(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询单个数据集导出任务集成测试。实际 jobId 需要从上一步应答中获取，
     * 这里仅占位验证接口可调。
     */
    @Test
    public void describeDatasetExportJobTest() {
        try {
            DescribeDatasetExportJobRequest request = new DescribeDatasetExportJobRequest();
            request.setAppId(appid);
            request.setDatasetname("test");
            request.setJobid("job-id-placeholder");
            DescribeDatasetExportJobResponse response = cosclient.describeDatasetExportJob(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询数据集导出任务列表集成测试。验证分页参数 maxresults / nexttoken 可传。
     */
    @Test
    public void describeDatasetExportJobsTest() {
        try {
            DescribeDatasetExportJobsRequest request = new DescribeDatasetExportJobsRequest();
            request.setAppId(appid);
            request.setDatasetname("test");
            request.setMaxresults(20);
            DescribeDatasetExportJobsResponse response = cosclient.describeDatasetExportJobs(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消数据集导出任务集成测试。实际 jobId 需要从 createDatasetExportJobTest 返回中获取。
     */
    @Test
    public void cancelDatasetExportJobTest() {
        try {
            CancelDatasetExportJobRequest request = new CancelDatasetExportJobRequest();
            request.setAppId(appid);
            request.setDatasetName("test");
            request.setJobId("job-id-placeholder");
            CancelDatasetExportJobResponse response = cosclient.cancelDatasetExportJob(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
