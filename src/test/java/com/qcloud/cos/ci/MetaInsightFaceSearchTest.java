package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ciModel.metaInsight.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.CIJackson;
import com.qcloud.cos.utils.Jackson;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class MetaInsightFaceSearchTest extends AbstractCOSClientCITest {

    private static final String datasetName = "mark-face-search";
    private static final String bucketName = "beijingtest-1251704708";
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-beijing");
        ClientConfig clientConfig = new ClientConfig(region);
//        clientConfig.setHttpProtocol(HttpProtocol.http);
//        clientConfig.setCiSpecialRequest(true);
        cosclient = new COSClient(cred, clientConfig);
    }

//    @Before
//    public void testCreateDataset() {
//        try {
//            CreateDatasetRequest request = new CreateDatasetRequest();
//            request.setAppId(appid);
//            request.setDatasetName(datasetName);
//            request.setDescription("demo");
//            request.setTemplateId("Official:FaceSearch");
//            CreateDatasetResponse response = cosclient.createDataset(request);
//            System.out.println(Jackson.toJsonString(response));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @After
//    public void deleteDatasetTest() {
//        try {
//            DeleteDatasetRequest request = new DeleteDatasetRequest();
//            request.setAppId(appid);
//            request.setDatasetName(datasetName);
//            System.out.println(CIJackson.toJsonString(request));
//            DeleteDatasetResponse response = cosclient.deleteDataset(request);
//            System.out.println(Jackson.toJsonString(response));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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
    public void datasetFaceSearchTest() {
        try {
            DatasetFaceSearchRequest request = new DatasetFaceSearchRequest();
            request.setAppId(appid);
            request.setDatasetName(datasetName);
            request.setURI("cos://" + bucketName + "/face/huge_base.jpg");
            request.setMaxFaceNum(1);
            request.setLimit(10);
            request.setMatchThreshold(10);
            DatasetFaceSearchResponse response = cosclient.datasetFaceSearch(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
