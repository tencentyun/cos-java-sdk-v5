package com.qcloud.cos;

import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.fetch.GetAsyncFetchTaskRequest;
import com.qcloud.cos.model.fetch.GetAsyncFetchTaskResult;
import com.qcloud.cos.model.fetch.PutAsyncFetchTaskRequest;
import com.qcloud.cos.model.fetch.PutAsyncFetchTaskResult;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AsyncFetchTaskTest extends AbstractCOSClientTest{
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void testPutGetAsyncFetchTaskWithException() {
        PutAsyncFetchTaskRequest request = new PutAsyncFetchTaskRequest();

        request.setBucketName(bucket);
        request.setUrl("https://pic1.zhimg.com/v2-63fc555d77019ae08ac2281a2418dcc7_720w.jpg");
        request.setIgnoreSameKey(false);
        request.setKey("test_fetch");
        request.setOnKeyExist("override");

        try {
            PutAsyncFetchTaskResult result = cosclient.putAsyncFetchTask(request);
        } catch (CosServiceException cse) {
            assertEquals(405, cse.getStatusCode());
        }

        String taskId = "Nj00000000000000000000000000000000000000";
        GetAsyncFetchTaskRequest getAsyncFetchTaskRequest = new GetAsyncFetchTaskRequest();
        getAsyncFetchTaskRequest.setBucketName(bucket);
        getAsyncFetchTaskRequest.setTaskId(taskId);

        try {
            GetAsyncFetchTaskResult result = cosclient.getAsyncFetchTask(getAsyncFetchTaskRequest);
        } catch (CosServiceException cse) {
            assertEquals(404, cse.getStatusCode());
        }
    }
}
