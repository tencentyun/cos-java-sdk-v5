package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.DecompressionRequest;
import com.qcloud.cos.model.DecompressionResult;
import com.qcloud.cos.model.ListJobsResult;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingInputObject;
import com.qcloud.cos.model.ciModel.auditing.AuditingSnapshotObject;
import com.qcloud.cos.model.ciModel.auditing.BatchImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.BatchImageAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.CallbackVersion;
import com.qcloud.cos.model.ciModel.auditing.Conf;
import com.qcloud.cos.model.ciModel.auditing.DescribeImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.DocumentAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.DocumentAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.DocumentInputObject;
import com.qcloud.cos.model.ciModel.auditing.Encryption;
import com.qcloud.cos.model.ciModel.auditing.Freeze;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.ReportBadCaseRequest;
import com.qcloud.cos.model.ciModel.auditing.SuggestedLabel;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.UserInfo;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.WebpageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.WebpageAuditingResponse;
import com.qcloud.cos.model.ciModel.bucket.DocBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.DocBucketResponse;
import com.qcloud.cos.model.ciModel.common.CImageProcessRequest;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.image.GenerateQrcodeRequest;
import com.qcloud.cos.model.ciModel.image.ImageLabelRequest;
import com.qcloud.cos.model.ciModel.image.ImageLabelResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Request;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Response;
import com.qcloud.cos.model.ciModel.image.ImageSearchRequest;
import com.qcloud.cos.model.ciModel.image.ImageSearchResponse;
import com.qcloud.cos.model.ciModel.image.ImageStyleRequest;
import com.qcloud.cos.model.ciModel.image.ImageStyleResponse;
import com.qcloud.cos.model.ciModel.image.OpenImageSearchRequest;
import com.qcloud.cos.model.ciModel.job.BatchJobOperation;
import com.qcloud.cos.model.ciModel.job.BatchJobRequest;
import com.qcloud.cos.model.ciModel.job.BatchJobResponse;
import com.qcloud.cos.model.ciModel.job.DocHtmlRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListResponse;
import com.qcloud.cos.model.ciModel.job.DocJobObject;
import com.qcloud.cos.model.ciModel.job.DocJobRequest;
import com.qcloud.cos.model.ciModel.job.DocJobResponse;
import com.qcloud.cos.model.ciModel.job.DocOperationObject;
import com.qcloud.cos.model.ciModel.job.DocProcessObject;
import com.qcloud.cos.model.ciModel.job.FileCompressConfig;
import com.qcloud.cos.model.ciModel.job.FileProcessJobResponse;
import com.qcloud.cos.model.ciModel.job.FileProcessJobType;
import com.qcloud.cos.model.ciModel.job.FileProcessOperation;
import com.qcloud.cos.model.ciModel.job.FileProcessRequest;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoRequest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoResponse;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.DetectCarRequest;
import com.qcloud.cos.model.ciModel.persistence.DetectCarResponse;
import com.qcloud.cos.model.ciModel.queue.DocListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.DocQueueRequest;
import com.qcloud.cos.model.ciModel.queue.MediaListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaNotifyConfig;
import com.qcloud.cos.model.ciModel.queue.MediaQueueRequest;
import com.qcloud.cos.model.ciModel.snapshot.CosSnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Request;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Response;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionsResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListRequest;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class CIClientTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void testGenerateSnapshot() {
        //1.创建截图请求对象
        SnapshotRequest request = new SnapshotRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        request.getInput().setObject("1.mp4");
        request.getOutput().setBucket(bucket);
        request.getOutput().setRegion(region);
        request.getOutput().setObject("test/1.jpg");
        request.setTime("15");
        //3.调用接口,获取截图响应对象
        SnapshotResponse response = cosclient.generateSnapshot(request);
    }

    @Test
    public void testGenerateMediainfo() {
        MediaInfoRequest request = new MediaInfoRequest();
        request.setBucketName(bucket);
        request.getInput().setObject("1.mp3");
        MediaInfoResponse result = cosclient.generateMediainfo(request);
    }

    @Test
    public void testDescribeWorkflowExecution() {
        MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        request.setBucketName(bucket);
        request.setRunId(workflowId);
        MediaWorkflowExecutionResponse result = cosclient.describeWorkflowExecution(request);

        // Verify the results
    }

    @Test
    public void testDescribeWorkflowExecutions() {
        MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        request.setBucketName(bucket);
        request.setWorkflowId(workflowId);
        MediaWorkflowExecutionsResponse result = cosclient.describeWorkflowExecutions(request);
    }

    @Test
    public void testCreateDocProcessJobs() {
        // Setup
        DocJobRequest request = new DocJobRequest();
        request.setBucketName(bucket);
        DocJobObject docJobObject = new DocJobObject();
        MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        docJobObject.setInput(input);
        docJobObject.setTag("tag");
        docJobObject.setQueueId("queueId");
        DocOperationObject operation = new DocOperationObject();
        MediaOutputObject output = new MediaOutputObject();
        output.setRegion(region);
        output.setBucket(bucket);
        output.setObject("object");
        operation.setOutput(output);
        DocProcessObject docProcessObject = new DocProcessObject();
        docProcessObject.setPicPagination("picPagination");
        docProcessObject.setImageDpi("imageDpi");
        docProcessObject.setSrcType("srcType");
        docProcessObject.setTgtType("tgtType");
        docProcessObject.setSheetId("sheetId");
        docProcessObject.setStartPage("startPage");
        docProcessObject.setEndPage("endPage");
        docProcessObject.setImageParams("imageParams");
        docProcessObject.setDocPassword("docPassword");
        docProcessObject.setComments("comments");
        docProcessObject.setPaperDirection("paperDirection");
        docProcessObject.setQuality("quality");
        docProcessObject.setZoom("zoom");
        operation.setDocProcessObject(docProcessObject);
        docJobObject.setOperation(operation);
        request.setDocJobObject(docJobObject);
        request.setJobId("jobId");


        // Run the test
        DocJobResponse result = cosclient.createDocProcessJobs(request);

        // Verify the results
    }

    @Test
    public void testDescribeDocProcessJob() {
        // Setup
        DocJobRequest request = new DocJobRequest();


        request.setBucketName(bucket);
        DocJobObject docJobObject = new DocJobObject();
        MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        docJobObject.setInput(input);
        docJobObject.setTag("tag");
        docJobObject.setQueueId("queueId");
        DocOperationObject operation = new DocOperationObject();
        MediaOutputObject output = new MediaOutputObject();
        output.setRegion(region);
        output.setBucket(bucket);
        output.setObject("object");
        operation.setOutput(output);
        DocProcessObject docProcessObject = new DocProcessObject();
        docProcessObject.setPicPagination("picPagination");
        docProcessObject.setImageDpi("imageDpi");
        docProcessObject.setSrcType("srcType");
        docProcessObject.setTgtType("tgtType");
        docProcessObject.setSheetId("sheetId");
        docProcessObject.setStartPage("startPage");
        docProcessObject.setEndPage("endPage");
        docProcessObject.setImageParams("imageParams");
        docProcessObject.setDocPassword("docPassword");
        docProcessObject.setComments("comments");
        docProcessObject.setPaperDirection("paperDirection");
        docProcessObject.setQuality("quality");
        docProcessObject.setZoom("zoom");
        operation.setDocProcessObject(docProcessObject);
        docJobObject.setOperation(operation);
        request.setDocJobObject(docJobObject);
        request.setJobId("jobId");


        // Run the test
        DocJobResponse result = cosclient.describeDocProcessJob(request);

        // Verify the results
    }

    @Test
    public void testDescribeDocProcessJobs() {
        // Setup
        DocJobListRequest request = new DocJobListRequest();


        request.setBucketName(bucket);
        request.setQueueId("queueId");
        request.setTag("tag");
        request.setOrderByTime("orderByTime");
        request.setNextToken("nextToken");
        request.setSize(0);
        request.setStates("states");
        request.setStartCreationTime("startCreationTime");
        request.setEndCreationTime("endCreationTime");


        // Run the test
        DocJobListResponse result = cosclient.describeDocProcessJobs(request);

        // Verify the results
    }

    @Test
    public void testDescribeDocProcessQueues() {
        // Setup
        DocQueueRequest docRequest = new DocQueueRequest();
        docRequest.setBucketName(bucket);
        docRequest.setQueueId("queueId");
        docRequest.setState("state");
        docRequest.setPageNumber("pageNumber");
        docRequest.setPageSize("pageSize");
        MediaNotifyConfig notifyConfig = new MediaNotifyConfig();
        notifyConfig.setUrl("url");
        notifyConfig.setType("type");
        notifyConfig.setEvent("event");
        notifyConfig.setState("state");
        docRequest.setNotifyConfig(notifyConfig);
        docRequest.setName("name");


        // Run the test
        DocListQueueResponse result = cosclient.describeDocProcessQueues(docRequest);

        // Verify the results
    }

    @Test
    public void testUpdateDocProcessQueue() {
        // Setup
        DocQueueRequest docRequest = new DocQueueRequest();
        docRequest.setBucketName(bucket);
        docRequest.setQueueId("queueId");
        docRequest.setState("state");
        docRequest.setPageNumber("pageNumber");
        docRequest.setPageSize("pageSize");
        MediaNotifyConfig notifyConfig = new MediaNotifyConfig();
        notifyConfig.setUrl("url");
        notifyConfig.setType("type");
        notifyConfig.setEvent("event");
        notifyConfig.setState("state");
        docRequest.setNotifyConfig(notifyConfig);
        docRequest.setName("name");


        // Run the test
        boolean result = cosclient.updateDocProcessQueue(docRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testDescribeDocProcessBuckets() {
        // Setup
        DocBucketRequest docRequest = new DocBucketRequest();


        // Run the test
        DocBucketResponse result = cosclient.describeDocProcessBuckets(docRequest);

        // Verify the results
    }

    @Test
    public void testProcessImage() {
        // Setup
        ImageProcessRequest imageProcessRequest = new ImageProcessRequest(bucket, "destinationKey");
        // Run the test
        CIUploadResult result = cosclient.processImage(imageProcessRequest);
    }

    @Test
    public void testImageAuditing() {
        // Setup
        ImageAuditingRequest imageAuditingRequest = new ImageAuditingRequest();
        imageAuditingRequest.setDetectType("detectType");
        imageAuditingRequest.setBucketName(bucket);
        imageAuditingRequest.setObjectKey("destinationKey");
        imageAuditingRequest.setInterval(0);
        imageAuditingRequest.setMaxFrames(0);
        imageAuditingRequest.setBizType("bizType");
        imageAuditingRequest.setDetectUrl("detectUrl");
        imageAuditingRequest.setLargeImageDetect("largeImageDetect");
        imageAuditingRequest.setJobId("jobId");
        imageAuditingRequest.setDataId("dataId");
        imageAuditingRequest.setAsync("async");
        imageAuditingRequest.setCallback("callback");


        // Run the test
        ImageAuditingResponse result = cosclient.imageAuditing(imageAuditingRequest);

        // Verify the results
    }

    @Test
    public void testCreateVideoAuditingJob() {
        // Setup
        VideoAuditingRequest videoAuditingRequest = new VideoAuditingRequest();
        videoAuditingRequest.setBucketName(bucket);
        videoAuditingRequest.setJobId("jobId");
        Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        videoAuditingRequest.setConf(conf);


        // Run the test
        VideoAuditingResponse result = cosclient.createVideoAuditingJob(videoAuditingRequest);

        // Verify the results
    }

    @Test
    public void testDescribeAuditingJob() {
        // Setup
        VideoAuditingRequest videoAuditingRequest = new VideoAuditingRequest();
        videoAuditingRequest.setBucketName(bucket);
        videoAuditingRequest.setJobId("jobId");
        Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        videoAuditingRequest.setConf(conf);


        // Run the test
        VideoAuditingResponse result = cosclient.describeAuditingJob(videoAuditingRequest);

        // Verify the results
    }

    @Test
    public void testCreateAudioAuditingJobs() {
        // Setup
        AudioAuditingRequest audioAuditingRequest = new AudioAuditingRequest();
        audioAuditingRequest.setBucketName(bucket);
        audioAuditingRequest.setJobId("jobId");
        AuditingInputObject input = new AuditingInputObject();
        input.setObject("object");
        input.setContent("content");
        input.setUrl("url");
        input.setDataId("dataId");
        UserInfo userInfo = new UserInfo();
        userInfo.setTokenId("tokenId");
        userInfo.setNickname("nickname");
        userInfo.setDeviceId("deviceId");
        userInfo.setAppId("appId");
        userInfo.setRoom("room");
        userInfo.setIp("ip");
        userInfo.setType("type");
        userInfo.setReceiveTokenId("receiveTokenId");
        userInfo.setGender("gender");
        userInfo.setLevel("level");
        userInfo.setRole("role");
        input.setUserInfo(userInfo);
        Encryption encryption = new Encryption();
        encryption.setAlgorithm("algorithm");
        encryption.setKey("key");
        encryption.setIV("iV");
        encryption.setKeyId("keyId");
        encryption.setKeyType("keyType");
        input.setEncryption(encryption);
        audioAuditingRequest.setInput(input);
        Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        audioAuditingRequest.setConf(conf);


        // Run the test
        AudioAuditingResponse result = cosclient.createAudioAuditingJobs(audioAuditingRequest);

        // Verify the results
    }

    @Test
    public void testDescribeAudioAuditingJob() {
        // Setup
        AudioAuditingRequest audioAuditingRequest = new AudioAuditingRequest();
        audioAuditingRequest.setBucketName(bucket);
        audioAuditingRequest.setJobId("jobId");
        AuditingInputObject input = new AuditingInputObject();
        input.setObject("object");
        input.setContent("content");
        input.setUrl("url");
        input.setDataId("dataId");
        UserInfo userInfo = new UserInfo();
        userInfo.setTokenId("tokenId");
        userInfo.setNickname("nickname");
        userInfo.setDeviceId("deviceId");
        userInfo.setAppId("appId");
        userInfo.setRoom("room");
        userInfo.setIp("ip");
        userInfo.setType("type");
        userInfo.setReceiveTokenId("receiveTokenId");
        userInfo.setGender("gender");
        userInfo.setLevel("level");
        userInfo.setRole("role");
        input.setUserInfo(userInfo);
        Encryption encryption = new Encryption();
        encryption.setAlgorithm("algorithm");
        encryption.setKey("key");
        encryption.setIV("iV");
        encryption.setKeyId("keyId");
        encryption.setKeyType("keyType");
        input.setEncryption(encryption);
        audioAuditingRequest.setInput(input);
        Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        audioAuditingRequest.setConf(conf);


        // Run the test
        AudioAuditingResponse result = cosclient.describeAudioAuditingJob(audioAuditingRequest);

        // Verify the results
    }

    @Test
    public void testGetImageLabel() {
        // Setup
        ImageLabelRequest imageLabelRequest = new ImageLabelRequest();
        imageLabelRequest.setObjectKey("destinationKey");
        imageLabelRequest.setBucketName(bucket);
        imageLabelRequest.setDetectUrl("detectUrl");


        // Run the test
        ImageLabelResponse result = cosclient.getImageLabel(imageLabelRequest);

        // Verify the results
    }

    @Test
    public void testGetImageLabelV2() {
        // Setup
        ImageLabelV2Request imageLabelV2Request = new ImageLabelV2Request();
        imageLabelV2Request.setBucketName(bucket);
        imageLabelV2Request.setObjectKey("destinationKey");
        imageLabelV2Request.setScenes("scenes");


        // Run the test
        ImageLabelV2Response result = cosclient.getImageLabelV2(imageLabelV2Request);

        // Verify the results
    }

    @Test
    public void testCreateAuditingTextJobs() {
        // Setup
        TextAuditingRequest textAuditingRequest = new TextAuditingRequest();
        textAuditingRequest.setBucketName(bucket);
        textAuditingRequest.setJobId("jobId");
        Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        textAuditingRequest.setConf(conf);


        // Run the test
        TextAuditingResponse result = cosclient.createAuditingTextJobs(textAuditingRequest);

        // Verify the results
    }

    @Test
    public void testDescribeAuditingTextJob() {
        // Setup
        TextAuditingRequest textAuditingRequest = new TextAuditingRequest();
        textAuditingRequest.setBucketName(bucket);
        textAuditingRequest.setJobId("jobId");
        Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        textAuditingRequest.setConf(conf);


        // Run the test
        TextAuditingResponse result = cosclient.describeAuditingTextJob(textAuditingRequest);

        // Verify the results
    }

    @Test
    public void testCreateAuditingDocumentJobs() {
        // Setup
        DocumentAuditingRequest documentAuditingRequest = new DocumentAuditingRequest();
        documentAuditingRequest.setBucketName(bucket);
        DocumentInputObject input = new DocumentInputObject();
        input.setDataId("dataId");
        input.setUrl("url");
        input.setType("type");
        input.setObject("object");
        UserInfo userInfo = new UserInfo();
        userInfo.setTokenId("tokenId");
        userInfo.setNickname("nickname");
        userInfo.setDeviceId("deviceId");
        userInfo.setAppId("appId");
        userInfo.setRoom("room");
        userInfo.setIp("ip");
        userInfo.setType("type");
        userInfo.setReceiveTokenId("receiveTokenId");
        userInfo.setGender("gender");
        userInfo.setLevel("level");
        userInfo.setRole("role");
        input.setUserInfo(userInfo);
        documentAuditingRequest.setInput(input);
        Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        documentAuditingRequest.setConf(conf);
        documentAuditingRequest.setJobId("jobId");


        // Run the test
        DocumentAuditingResponse result = cosclient.createAuditingDocumentJobs(documentAuditingRequest);

        // Verify the results
    }

    @Test
    public void testDescribeAuditingDocumentJob() {
        // Setup
        DocumentAuditingRequest documentAuditingRequest = new DocumentAuditingRequest();
        documentAuditingRequest.setBucketName(bucket);
        DocumentInputObject input = new DocumentInputObject();
        input.setDataId("dataId");
        input.setUrl("url");
        input.setType("type");
        input.setObject("object");
        UserInfo userInfo = new UserInfo();
        userInfo.setTokenId("tokenId");
        userInfo.setNickname("nickname");
        userInfo.setDeviceId("deviceId");
        userInfo.setAppId("appId");
        userInfo.setRoom("room");
        userInfo.setIp("ip");
        userInfo.setType("type");
        userInfo.setReceiveTokenId("receiveTokenId");
        userInfo.setGender("gender");
        userInfo.setLevel("level");
        userInfo.setRole("role");
        input.setUserInfo(userInfo);
        documentAuditingRequest.setInput(input);
        Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        documentAuditingRequest.setConf(conf);
        documentAuditingRequest.setJobId("jobId");


        // Run the test
        DocumentAuditingResponse result = cosclient.describeAuditingDocumentJob(documentAuditingRequest);

        // Verify the results
    }

    @Test
    public void testBatchImageAuditing() {
        // Setup
        BatchImageAuditingRequest batchImageAuditingRequest = new BatchImageAuditingRequest();
        batchImageAuditingRequest.setBucketName(bucket);
        Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        batchImageAuditingRequest.setConf(conf);


        // Run the test
        BatchImageAuditingResponse result = cosclient.batchImageAuditing(batchImageAuditingRequest);

        // Verify the results
    }

    @Test
    public void testCreateDocProcessBucket() {
        // Setup
        DocBucketRequest docBucketRequest = new DocBucketRequest();


        // Run the test
        Boolean result = cosclient.createDocProcessBucket(docBucketRequest);

        // Verify the results
        assertFalse(result);
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

        // Verify the results
        assertEquals("result", result);
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


        // Run the test
        assertThrows(URISyntaxException.class, () -> cosclient.GenerateDocPreviewUrl(docJobRequest));
    }

    @Test
    public void testCreateWebpageAuditingJob() {
        // Setup
        WebpageAuditingRequest webpageAuditingRequest = new WebpageAuditingRequest();
        webpageAuditingRequest.setBucketName(bucket);
        webpageAuditingRequest.setJobId("jobId");
        AuditingInputObject input = new AuditingInputObject();
        input.setObject("object");
        input.setContent("content");
        input.setUrl("url");
        input.setDataId("dataId");
        UserInfo userInfo = new UserInfo();
        userInfo.setTokenId("tokenId");
        userInfo.setNickname("nickname");
        userInfo.setDeviceId("deviceId");
        userInfo.setAppId("appId");
        userInfo.setRoom("room");
        userInfo.setIp("ip");
        userInfo.setType("type");
        userInfo.setReceiveTokenId("receiveTokenId");
        userInfo.setGender("gender");
        userInfo.setLevel("level");
        userInfo.setRole("role");
        input.setUserInfo(userInfo);
        Encryption encryption = new Encryption();
        encryption.setAlgorithm("algorithm");
        encryption.setKey("key");
        encryption.setIV("iV");
        encryption.setKeyId("keyId");
        encryption.setKeyType("keyType");
        input.setEncryption(encryption);
        webpageAuditingRequest.setInput(input);
        Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        webpageAuditingRequest.setConf(conf);


        // Run the test
        WebpageAuditingResponse result = cosclient.createWebpageAuditingJob(webpageAuditingRequest);

        // Verify the results
    }

    @Test
    public void testDescribeWebpageAuditingJob() {
        // Setup
        WebpageAuditingRequest webpageAuditingRequest = new WebpageAuditingRequest();
        webpageAuditingRequest.setBucketName(bucket);
        webpageAuditingRequest.setJobId("jobId");
        AuditingInputObject input = new AuditingInputObject();
        input.setObject("object");
        input.setContent("content");
        input.setUrl("url");
        input.setDataId("dataId");
        UserInfo userInfo = new UserInfo();
        userInfo.setTokenId("tokenId");
        userInfo.setNickname("nickname");
        userInfo.setDeviceId("deviceId");
        userInfo.setAppId("appId");
        userInfo.setRoom("room");
        userInfo.setIp("ip");
        userInfo.setType("type");
        userInfo.setReceiveTokenId("receiveTokenId");
        userInfo.setGender("gender");
        userInfo.setLevel("level");
        userInfo.setRole("role");
        input.setUserInfo(userInfo);
        Encryption encryption = new Encryption();
        encryption.setAlgorithm("algorithm");
        encryption.setKey("key");
        encryption.setIV("iV");
        encryption.setKeyId("keyId");
        encryption.setKeyType("keyType");
        input.setEncryption(encryption);
        webpageAuditingRequest.setInput(input);
        Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        webpageAuditingRequest.setConf(conf);


        // Run the test
        WebpageAuditingResponse result = cosclient.describeWebpageAuditingJob(webpageAuditingRequest);

        // Verify the results
    }

    @Test
    public void testReportBadCase() {
        // Setup
        ReportBadCaseRequest reportBadCaseRequest = new ReportBadCaseRequest();
        reportBadCaseRequest.setBucketName(bucket);
        reportBadCaseRequest.setContentType("contentType");
        reportBadCaseRequest.setText("text");
        reportBadCaseRequest.setLabel("label");
        reportBadCaseRequest.setSuggestedLabel(SuggestedLabel.Normal);
        reportBadCaseRequest.setJobId("jobId");
        reportBadCaseRequest.setModerationTime("moderationTime");


        // Run the test
        String result = cosclient.reportBadCase(reportBadCaseRequest);

        // Verify the results
        assertEquals("policyText", result);
    }

    @Test
    public void testDescribeAuditingImageJob() {
        // Setup
        DescribeImageAuditingRequest imageAuditingRequest = new DescribeImageAuditingRequest();
        imageAuditingRequest.setBucketName(bucket);
        imageAuditingRequest.setJobId("jobId");

        // Run the test
        ImageAuditingResponse result = cosclient.describeAuditingImageJob(imageAuditingRequest);
    }

    @Test
    public void testGetPrivateM3U8() {
        // Setup
        PrivateM3U8Request privateM3U8Request = new PrivateM3U8Request();
        privateM3U8Request.setBucketName(bucket);
        privateM3U8Request.setExpires("expires");
        privateM3U8Request.setObject("destinationKey");

        // Run the test
        PrivateM3U8Response result = cosclient.getPrivateM3U8(privateM3U8Request);

        // Verify the results
    }

    @Test
    public void testDetectCar() {
        // Setup
        DetectCarRequest detectCarRequest = new DetectCarRequest();
        detectCarRequest.setBucketName(bucket);
        detectCarRequest.setObjectKey("destinationKey");
        detectCarRequest.setDetectUrl("detectUrl");


        // Run the test
        DetectCarResponse result = cosclient.detectCar(detectCarRequest);

        // Verify the results
    }

    @Test
    public void testOpenImageSearch() {
        // Setup
        OpenImageSearchRequest imageSearchRequest = new OpenImageSearchRequest();
        imageSearchRequest.setBucketName(bucket);
        imageSearchRequest.setMaxCapacity("maxCapacity");
        imageSearchRequest.setMaxQps("maxQps");


        // Run the test
        boolean result = cosclient.openImageSearch(imageSearchRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testAddGalleryImages() {
        // Setup
        ImageSearchRequest imageSearchRequest = new ImageSearchRequest();
        imageSearchRequest.setObjectKey("destinationKey");
        imageSearchRequest.setBucketName(bucket);
        imageSearchRequest.setEntityId("entityId");
        imageSearchRequest.setCustomContent("customContent");
        imageSearchRequest.setTags("tags");
        imageSearchRequest.setMatchThreshold("matchThreshold");
        imageSearchRequest.setOffset("offset");
        imageSearchRequest.setLimit("limit");
        imageSearchRequest.setFilter("filter");


        // Run the test
        boolean result = cosclient.addGalleryImages(imageSearchRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testDeleteGalleryImages() {
        // Setup
        ImageSearchRequest imageSearchRequest = new ImageSearchRequest();
        imageSearchRequest.setObjectKey("destinationKey");
        imageSearchRequest.setBucketName(bucket);
        imageSearchRequest.setEntityId("entityId");
        imageSearchRequest.setCustomContent("customContent");
        imageSearchRequest.setTags("tags");
        imageSearchRequest.setMatchThreshold("matchThreshold");
        imageSearchRequest.setOffset("offset");
        imageSearchRequest.setLimit("limit");
        imageSearchRequest.setFilter("filter");


        // Run the test
        boolean result = cosclient.deleteGalleryImages(imageSearchRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testSearchGalleryImages() {
        // Setup
        ImageSearchRequest imageSearchRequest = new ImageSearchRequest();
        imageSearchRequest.setObjectKey("destinationKey");
        imageSearchRequest.setBucketName(bucket);
        imageSearchRequest.setEntityId("entityId");
        imageSearchRequest.setCustomContent("customContent");
        imageSearchRequest.setTags("tags");
        imageSearchRequest.setMatchThreshold("matchThreshold");
        imageSearchRequest.setOffset("offset");
        imageSearchRequest.setLimit("limit");
        imageSearchRequest.setFilter("filter");


        // Run the test
        ImageSearchResponse result = cosclient.searchGalleryImages(imageSearchRequest);

        // Verify the results
    }

    @Test
    public void testTriggerWorkflowList() {
        // Setup
        MediaWorkflowListRequest mediaWorkflowListRequest = new MediaWorkflowListRequest();
        mediaWorkflowListRequest.setBucketName(bucket);
        mediaWorkflowListRequest.setRunId("runId");
        mediaWorkflowListRequest.setIds("ids");
        mediaWorkflowListRequest.setName("name");
        mediaWorkflowListRequest.setPageNumber("pageNumber");
        mediaWorkflowListRequest.setPageSize("pageSize");
        mediaWorkflowListRequest.setWorkflowId("workflowId");
        mediaWorkflowListRequest.setOrderByTime("orderByTime");
        mediaWorkflowListRequest.setSize("size");
        mediaWorkflowListRequest.setStates("states");
        mediaWorkflowListRequest.setStartCreationTime("startCreationTime");
        mediaWorkflowListRequest.setEndCreationTime("endCreationTime");
        mediaWorkflowListRequest.setNextToken("nextToken");
        mediaWorkflowListRequest.setObject("object");


        // Run the test
        MediaWorkflowListResponse result = cosclient.triggerWorkflowList(mediaWorkflowListRequest);

        // Verify the results
    }

    @Test
    public void testGetSnapshot() {
        // Setup
        CosSnapshotRequest snapshotRequest = new CosSnapshotRequest();
        snapshotRequest.setTime("time");
        snapshotRequest.setWidth("width");
        snapshotRequest.setHeight("height");
        snapshotRequest.setFormat("format");
        snapshotRequest.setMode("mode");
        snapshotRequest.setRotate("rotate");
        snapshotRequest.setBucketName(bucket);
        snapshotRequest.setObjectKey("destinationKey");


        // Run the test
        InputStream result = cosclient.getSnapshot(snapshotRequest);

        // Verify the results
    }

    @Test
    public void testGenerateQrcode() {
        // Setup
        GenerateQrcodeRequest generateQrcodeRequest = new GenerateQrcodeRequest();
        generateQrcodeRequest.setQrcodeContent("qrcodeContent");
        generateQrcodeRequest.setMode("mode");
        generateQrcodeRequest.setWidth("width");
        generateQrcodeRequest.setBucketName(bucket);


        // Run the test
        String result = cosclient.generateQrcode(generateQrcodeRequest);

        // Verify the results
        assertEquals("policyText", result);
    }

    @Test
    public void testAddImageStyle() {
        // Setup
        ImageStyleRequest imageStyleRequest = new ImageStyleRequest();
        imageStyleRequest.setBucketName(bucket);
        imageStyleRequest.setStyleName("styleName");
        imageStyleRequest.setStyleBody("styleBody");


        // Run the test
        Boolean result = cosclient.addImageStyle(imageStyleRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testGetImageStyle() {
        // Setup
        ImageStyleRequest imageStyleRequest = new ImageStyleRequest();
        imageStyleRequest.setBucketName(bucket);
        imageStyleRequest.setStyleName("styleName");
        imageStyleRequest.setStyleBody("styleBody");


        // Run the test
        ImageStyleResponse result = cosclient.getImageStyle(imageStyleRequest);

        // Verify the results
    }

    @Test
    public void testDeleteImageStyle() {
        // Setup
        ImageStyleRequest imageStyleRequest = new ImageStyleRequest();
        imageStyleRequest.setBucketName(bucket);
        imageStyleRequest.setStyleName("styleName");
        imageStyleRequest.setStyleBody("styleBody");


        // Run the test
        Boolean result = cosclient.deleteImageStyle(imageStyleRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testGetObjectDecompressionStatus1() {
        // Setup


        // Run the test
        String result = cosclient.getObjectDecompressionStatus(bucket, "destinationKey");

        // Verify the results
        assertEquals("policyText", result);
    }

    @Test
    public void testPostObjectDecompression() {
        // Setup
        DecompressionRequest decompressionRequest = new DecompressionRequest();
        decompressionRequest.setTargetBucketName("targetBucketName");
        decompressionRequest.setPrefixReplaced(false);
        decompressionRequest.setResourcesPrefix("resourcesPrefix");
        decompressionRequest.setTargetKeyPrefix("targetKeyPrefix");
        decompressionRequest.setObjectKey("destinationKey");
        decompressionRequest.setSourceBucketName("sourceBucketName");


        // Run the test
        DecompressionResult result = cosclient.postObjectDecompression(decompressionRequest);

        // Verify the results
    }

    @Test
    public void testGetObjectDecompressionStatus2() {
        // Setup


        // Run the test
        DecompressionResult result = cosclient.getObjectDecompressionStatus(bucket,
                "destinationKey", "jobId");

        // Verify the results
    }

    @Test
    public void testListObjectDecompressionJobs() {
        // Setup


        // Run the test
        ListJobsResult result = cosclient.listObjectDecompressionJobs(bucket, "jobStatus",
                "sortBy", "maxResults", "nextToken");

        // Verify the results
    }

    @Test
    public void testCreatePicProcessJob() {
        // Setup
        MediaJobsRequest req = new MediaJobsRequest();


        req.setBucketName(bucket);
        req.setQueueId("queueId");
        req.setTag("tag");
        req.setOrderByTime("orderByTime");
        req.setNextToken("nextToken");
        req.setSize(0);
        req.setStates("states");
        req.setStartCreationTime("startCreationTime");
        req.setEndCreationTime("endCreationTime");
        req.setJobId("jobId");
        MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        req.setInput(input);
        req.setCallBack("callBack");


        // Run the test
        MediaJobResponse result = cosclient.createPicProcessJob(req);

        // Verify the results
    }

    @Test
    public void testDescribePicProcessQueues() {
        // Setup
        MediaQueueRequest req = new MediaQueueRequest();


        req.setBucketName(bucket);
        req.setQueueId("queueId");
        req.setState("state");
        req.setPageNumber("pageNumber");
        req.setPageSize("pageSize");
        MediaNotifyConfig notifyConfig = new MediaNotifyConfig();
        notifyConfig.setUrl("url");
        notifyConfig.setType("type");
        notifyConfig.setEvent("event");
        notifyConfig.setState("state");
        req.setNotifyConfig(notifyConfig);
        req.setName("name");


        // Run the test
        MediaListQueueResponse result = cosclient.describePicProcessQueues(req);

        // Verify the results
    }

    @Test
    public void testProcessImage2() {
        // Setup
        CImageProcessRequest imageProcessRequest = new CImageProcessRequest(bucket, "destinationKey");

        // Run the test
        boolean result = cosclient.processImage2(imageProcessRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testCreateFileProcessJob() {
        // Setup
        FileProcessRequest req = new FileProcessRequest();

        req.setBucketName(bucket);
        req.setTag(FileProcessJobType.FileCompress);
        req.setQueueId("queueId");
        req.setCallBackFormat("callBackFormat");
        req.setCallBackType("callBackType");
        req.setCallBack("callBack");
        req.setCallBackMqConfig("callBackMqConfig");
        FileProcessOperation operation = new FileProcessOperation();
        FileCompressConfig fileCompressConfig = new FileCompressConfig();
        fileCompressConfig.setFlatten("flatten");
        fileCompressConfig.setFormat("format");
        fileCompressConfig.setUrlList("urlList");
        operation.setFileCompressConfig(fileCompressConfig);
        req.setOperation(operation);
        req.setJobId("jobId");

        // Run the test
        FileProcessJobResponse result = cosclient.createFileProcessJob(req);
        // Verify the results
    }

    @Test
    public void testDescribeFileProcessJob() {
        // Setup
        FileProcessRequest request = new FileProcessRequest();
        request.setBucketName(bucket);
        request.setTag(FileProcessJobType.FileCompress);
        request.setQueueId("queueId");
        request.setCallBackFormat("callBackFormat");
        request.setCallBackType("callBackType");
        request.setCallBack("callBack");
        request.setCallBackMqConfig("callBackMqConfig");
        FileProcessOperation operation = new FileProcessOperation();
        FileCompressConfig fileCompressConfig = new FileCompressConfig();
        fileCompressConfig.setFlatten("flatten");
        fileCompressConfig.setFormat("format");
        fileCompressConfig.setUrlList("urlList");
        operation.setFileCompressConfig(fileCompressConfig);
        request.setOperation(operation);
        request.setJobId("jobId");

        // Run the test
        FileProcessJobResponse result = cosclient.describeFileProcessJob(request);

        // Verify the results
    }

    @Test
    public void testCreateInventoryTriggerJob() {
        // Setup
        BatchJobRequest req = new BatchJobRequest();


        req.setBucketName(bucket);
        BatchJobOperation operation = new BatchJobOperation();
        MediaTimeIntervalObject timeInterval = new MediaTimeIntervalObject();
        timeInterval.setStart("start");
        timeInterval.setDuration("duration");
        timeInterval.setEnd("end");
        operation.setTimeInterval(timeInterval);
        operation.setTag("tag");
        MediaOutputObject output = new MediaOutputObject();
        output.setRegion(region);
        output.setBucket(bucket);
        output.setObject("object");
        output.setSpriteObject("spriteObject");
        operation.setOutput(output);
        req.setOperation(operation);
        req.setJobId("jobId");


        // Run the test
        BatchJobResponse result = cosclient.createInventoryTriggerJob(req);

        // Verify the results
    }

    @Test
    public void testDescribeInventoryTriggerJob() {
        // Setup
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName(bucket);
        BatchJobOperation operation = new BatchJobOperation();
        MediaTimeIntervalObject timeInterval = new MediaTimeIntervalObject();
        timeInterval.setStart("start");
        timeInterval.setDuration("duration");
        timeInterval.setEnd("end");
        operation.setTimeInterval(timeInterval);
        operation.setTag("tag");
        MediaOutputObject output = new MediaOutputObject();
        output.setRegion(region);
        output.setBucket(bucket);
        output.setObject("object");
        output.setSpriteObject("spriteObject");
        operation.setOutput(output);
        request.setOperation(operation);
        request.setJobId("jobId");

        // Run the test
        BatchJobResponse result = cosclient.describeInventoryTriggerJob(request);
        // Verify the results
    }


}
