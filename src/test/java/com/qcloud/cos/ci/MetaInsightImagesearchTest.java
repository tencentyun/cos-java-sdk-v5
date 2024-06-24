package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ciModel.metaInsight.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.CIJackson;
import com.qcloud.cos.utils.Jackson;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class MetaInsightImagesearchTest extends AbstractCOSClientCITest {

    private static final String datasetName = "mark-image-search";
    private static final String bucketName = "beijingtest-1251704708";

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

    @Before
    public void testCreateDataset() {
        try {
            CreateDatasetRequest request = new CreateDatasetRequest();
            request.setAppId(appid);
            request.setDatasetName(datasetName);
            request.setDescription("demo");
            request.setTemplateId("Official:ImageSearch");
            CreateDatasetResponse response = cosclient.createDataset(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteDatasetTest() {
        try {
            DeleteDatasetRequest request = new DeleteDatasetRequest();
            request.setAppId(appid);
            request.setDatasetName(datasetName);
            DeleteDatasetResponse response = cosclient.deleteDataset(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createDatasetBindingTest() {
        try {
            CreateDatasetBindingRequest request = new CreateDatasetBindingRequest();
            request.setAppId(appid);
            request.setDatasetName(datasetName);
            request.setURI("cos://" + bucketName);
            CreateDatasetBindingResponse response = cosclient.createDatasetBinding(request);
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
            request.setDatasetName(datasetName);
            request.setMode("pic");
            request.setURI("cos://" + bucketName + "/face/huge_base.jpg");
            // 设置返回相关图片的数量，默认值为10，最大值为100。;是否必传：否
            request.setLimit(10);
            request.setMatchThreshold(1);
            SearchImageResponse response = cosclient.searchImage(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void datasetSimpleQueryTest() {
        try {
            DatasetSimpleQueryRequest request = new DatasetSimpleQueryRequest();
            request.setAppId(appid);
            request.setDatasetName(datasetName);
            // 设置数据集名称，同一个账户下唯一。;是否必传：是
            Query query = new Query();
            query.setOperation("and");
            List<SubQueries> subQueriesList = new ArrayList<>();
            SubQueries subQueries = new SubQueries();
            subQueries.setOperation("eq");
            subQueries.setField("ContentType");
            subQueries.setValue("image/jpeg");
            subQueriesList.add(subQueries);
            query.setSubQueries(subQueriesList);
            request.setQuery(query);

//            request.setSort("CustomId");
//            request.setOrder("desc");
            request.setMaxResults(100);
            System.out.println(CIJackson.toJsonString(request));
            DatasetSimpleQueryResponse response = cosclient.datasetSimpleQuery(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
