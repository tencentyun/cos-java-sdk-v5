package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetBindingRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetBindingResponse;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetResponse;
import com.qcloud.cos.model.ciModel.metaInsight.CreateFileMetaIndexRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateFileMetaIndexResponse;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetFaceSearchRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetFaceSearchResponse;
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
}
