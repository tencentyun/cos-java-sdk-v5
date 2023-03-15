package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.bucket.DocBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.DocBucketResponse;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.DocHtmlRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListResponse;
import com.qcloud.cos.model.ciModel.job.DocJobObject;
import com.qcloud.cos.model.ciModel.job.DocJobRequest;
import com.qcloud.cos.model.ciModel.job.DocJobResponse;
import com.qcloud.cos.model.ciModel.job.DocProcessObject;
import com.qcloud.cos.model.ciModel.queue.DocListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.DocQueueRequest;
import com.qcloud.cos.model.ciModel.queue.MediaQueueObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DocProcessTest extends AbstractCOSClientCITest {
    private String queueId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void testCreateDocProcessBucket() {
        // Setup
        DocBucketRequest docBucketRequest = new DocBucketRequest();
        docBucketRequest.setBucketName(bucket);
        Boolean result = cosclient.createDocProcessBucket(docBucketRequest);
        assertTrue(result);
    }

    @Before
    public void testCreateDocProcessJobs() throws InterruptedException {
        //1.获取队列id,需要先开启文档预览功能。
        DocQueueRequest queueRequest = new DocQueueRequest();
        queueRequest.setBucketName(bucket);
        DocListQueueResponse response = cosclient.describeDocProcessQueues(queueRequest);
        List<MediaQueueObject> queueList = response.getQueueList();
        if (queueList.size() != 0) {
            MediaQueueObject mediaQueueObject = queueList.get(0);
            queueId = mediaQueueObject.getQueueId();
        } else {
            return;
        }
        //2.发送文档预览任务
        //2.1添加请求参数 参数详情请见api接口文档
        DocJobRequest request = new DocJobRequest();
        request.setBucketName(bucket);
        DocJobObject docJobObject = request.getDocJobObject();
        docJobObject.setTag("DocProcess");
        docJobObject.getInput().setObject("1.pdf");
        docJobObject.setQueueId(queueId);
        DocProcessObject docProcessObject = docJobObject.getOperation().getDocProcessObject();
        docProcessObject.setQuality("100");
        docProcessObject.setZoom("100");
        docProcessObject.setEndPage("-1");
        docProcessObject.setTgtType("jpg");
        MediaOutputObject output = docJobObject.getOperation().getOutput();
        output.setRegion(cosclient.getClientConfig().getRegion().getRegionName());
        output.setBucket(bucket);
        output.setObject("demo/pic-${Number}.jpg");
        //2.2发送预览请求
        DocJobResponse docProcessJobs = cosclient.createDocProcessJobs(request);

        //3.轮询查询任务结果（也可以配置队列的回调url,使用回调接口获取任务结果）
        DocJobRequest docJobRequest = new DocJobRequest();
        docJobRequest.setBucketName(bucket);
        String jobId = docProcessJobs.getJobsDetail().getJobId();
        docJobRequest.setJobId(jobId);
        while (true) {
            DocJobResponse docJobResponse = cosclient.describeDocProcessJob(docJobRequest);
            String state = docJobResponse.getJobsDetail().getState();
            //判断任务状态
            if ("Success".equalsIgnoreCase(state) || "Failed".equalsIgnoreCase(state)) {
                //处理业务逻辑
                System.out.println(docJobResponse);
                break;
            } else {
                Thread.sleep(500);
            }
        }
    }


    @Test
    public void testDescribeDocProcessJobs() {
        // Setup
        DocJobListRequest request = new DocJobListRequest();
        request.setBucketName(bucket);
        request.setQueueId(queueId);
        request.setTag("DocProcess");
        DocJobListResponse result = cosclient.describeDocProcessJobs(request);
    }

    @Test
    public void testUpdateDocProcessQueue() {
        DocQueueRequest request = new DocQueueRequest();
        request.setBucketName(bucket);
        request.setQueueId(queueId);
        request.getNotifyConfig().setUrl("http://cloud.tencent.com");
        request.getNotifyConfig().setState("On");
        request.getNotifyConfig().setEvent("TransCodingFinish");
        request.getNotifyConfig().setType("Url");
        request.setState("Active");
        request.setName("mark");
        boolean result = cosclient.updateDocProcessQueue(request);
        assertTrue(result);
    }

    @Test
    public void testDescribeDocProcessBuckets() {
        // Setup
        DocBucketRequest docRequest = new DocBucketRequest();
        docRequest.setBucketName(bucket);
        // Run the test
        DocBucketResponse result = cosclient.describeDocProcessBuckets(docRequest);
    }

    @Test
    public void testGenerateDocPreviewUrl() throws URISyntaxException {
        // Setup
        DocHtmlRequest docJobRequest = new DocHtmlRequest();
        docJobRequest.setImageDpi("imageDpi");
        docJobRequest.setObjectKey("destinationKey");
        docJobRequest.setSrcType("srcType");
        docJobRequest.setPage("page");
        docJobRequest.setImageParams("imageParams");
        docJobRequest.setSheet("sheet");
        docJobRequest.setPassword("password");
        docJobRequest.setComment("comment");
        docJobRequest.setExcelPaperDirection("excelPaperDirection");
        docJobRequest.setQuality("quality");
        docJobRequest.setScale("scale");
        docJobRequest.setBucketName(bucket);
        docJobRequest.setDstType(DocHtmlRequest.DocType.html);
        docJobRequest.setExcelPaperSize("excelPaperSize");
        // Run the test
        String result = cosclient.GenerateDocPreviewUrl(docJobRequest);
    }

    @Test
    public void testGenerateDocPreviewUrl_ThrowsURISyntaxException() {
        // Setup
        DocHtmlRequest docJobRequest = new DocHtmlRequest();
        docJobRequest.setImageDpi("imageDpi");
        docJobRequest.setObjectKey("destinationKey");
        docJobRequest.setSrcType("srcType");
        docJobRequest.setPage("page");
        docJobRequest.setImageParams("imageParams");
        docJobRequest.setSheet("sheet");
        docJobRequest.setPassword("password");
        docJobRequest.setComment("comment");
        docJobRequest.setExcelPaperDirection("excelPaperDirection");
        docJobRequest.setQuality("quality");
        docJobRequest.setScale("scale");
        docJobRequest.setBucketName(bucket);
        docJobRequest.setDstType(DocHtmlRequest.DocType.html);
        docJobRequest.setExcelPaperSize("excelPaperSize");
        String s = null;
        try {
             cosclient.GenerateDocPreviewUrl(docJobRequest);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
