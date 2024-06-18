package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetBindingRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetBindingResponse;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetResponse;
import com.qcloud.cos.utils.Jackson;
import org.junit.Test;


public class MetaInsightTest extends AbstractCOSClientCITest {

    @Test
    public void testCreateDataset() {
        try {
            CreateDatasetBindingRequest request = new CreateDatasetBindingRequest();
            request.setBucketName(bucket);
            request.setDatasetName("test");
            request.setURI("cos://" + bucket);
            CreateDatasetBindingResponse response = cosclient.createDatasetBinding(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
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
}
