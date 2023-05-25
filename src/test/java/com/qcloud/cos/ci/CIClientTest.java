package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class CIClientTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }



//    @Test
//    public void testProcessImage() {
//        // Setup
//        ImageProcessRequest imageProcessRequest = new ImageProcessRequest(bucket, "destinationKey");
//        // Run the test
//        CIUploadResult result = cosclient.processImage(imageProcessRequest);
//    }
//
//    @Test
//    public void testCreateWebpageAuditingJob() {
//        // Setup
//        WebpageAuditingRequest webpageAuditingRequest = new WebpageAuditingRequest();
//        webpageAuditingRequest.setBucketName(bucket);
//        webpageAuditingRequest.setJobId("jobId");
//        AuditingInputObject input = new AuditingInputObject();
//        input.setObject("object");
//        input.setContent("content");
//        input.setUrl("url");
//        input.setDataId("dataId");
//        UserInfo userInfo = new UserInfo();
//        userInfo.setTokenId("tokenId");
//        userInfo.setNickname("nickname");
//        userInfo.setDeviceId("deviceId");
//        userInfo.setAppId("appId");
//        userInfo.setRoom("room");
//        userInfo.setIp("ip");
//        userInfo.setType("type");
//        userInfo.setReceiveTokenId("receiveTokenId");
//        userInfo.setGender("gender");
//        userInfo.setLevel("level");
//        userInfo.setRole("role");
//        input.setUserInfo(userInfo);
//        Encryption encryption = new Encryption();
//        encryption.setAlgorithm("algorithm");
//        encryption.setKey("key");
//        encryption.setIV("iV");
//        encryption.setKeyId("keyId");
//        encryption.setKeyType("keyType");
//        input.setEncryption(encryption);
//        webpageAuditingRequest.setInput(input);
//        Conf conf = new Conf();
//        conf.setBizType("bizType");
//        conf.setDetectContent("detectContent");
//        conf.setDetectType("detectType");
//        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
//        snapshot.setMode("mode");
//        snapshot.setCount("count");
//        snapshot.setTimeInterval("timeInterval");
//        conf.setSnapshot(snapshot);
//        conf.setCallback("callback");
//        conf.setCallbackVersion(CallbackVersion.Simple);
//        conf.setAsync("async");
//        conf.setCallbackType("callbackType");
//        conf.setReturnHighlightHtml("returnHighlightHtml");
//        Freeze freeze = new Freeze();
//        freeze.setPornScore("pornScore");
//        freeze.setPoliticsScore("politicsScore");
//        freeze.setTerrorismScore("terrorismScore");
//        freeze.setAdsScore("adsScore");
//        conf.setFreeze(freeze);
//        webpageAuditingRequest.setConf(conf);
//
//
//        // Run the test
//        WebpageAuditingResponse result = cosclient.createWebpageAuditingJob(webpageAuditingRequest);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testDescribeWebpageAuditingJob() {
//        // Setup
//        WebpageAuditingRequest webpageAuditingRequest = new WebpageAuditingRequest();
//        webpageAuditingRequest.setBucketName(bucket);
//        webpageAuditingRequest.setJobId("jobId");
//        AuditingInputObject input = new AuditingInputObject();
//        input.setObject("object");
//        input.setContent("content");
//        input.setUrl("url");
//        input.setDataId("dataId");
//        UserInfo userInfo = new UserInfo();
//        userInfo.setTokenId("tokenId");
//        userInfo.setNickname("nickname");
//        userInfo.setDeviceId("deviceId");
//        userInfo.setAppId("appId");
//        userInfo.setRoom("room");
//        userInfo.setIp("ip");
//        userInfo.setType("type");
//        userInfo.setReceiveTokenId("receiveTokenId");
//        userInfo.setGender("gender");
//        userInfo.setLevel("level");
//        userInfo.setRole("role");
//        input.setUserInfo(userInfo);
//        Encryption encryption = new Encryption();
//        encryption.setAlgorithm("algorithm");
//        encryption.setKey("key");
//        encryption.setIV("iV");
//        encryption.setKeyId("keyId");
//        encryption.setKeyType("keyType");
//        input.setEncryption(encryption);
//        webpageAuditingRequest.setInput(input);
//        Conf conf = new Conf();
//        conf.setBizType("bizType");
//        conf.setDetectContent("detectContent");
//        conf.setDetectType("detectType");
//        AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
//        snapshot.setMode("mode");
//        snapshot.setCount("count");
//        snapshot.setTimeInterval("timeInterval");
//        conf.setSnapshot(snapshot);
//        conf.setCallback("callback");
//        conf.setCallbackVersion(CallbackVersion.Simple);
//        conf.setAsync("async");
//        conf.setCallbackType("callbackType");
//        conf.setReturnHighlightHtml("returnHighlightHtml");
//        Freeze freeze = new Freeze();
//        freeze.setPornScore("pornScore");
//        freeze.setPoliticsScore("politicsScore");
//        freeze.setTerrorismScore("terrorismScore");
//        freeze.setAdsScore("adsScore");
//        conf.setFreeze(freeze);
//        webpageAuditingRequest.setConf(conf);
//
//
//        // Run the test
//        WebpageAuditingResponse result = cosclient.describeWebpageAuditingJob(webpageAuditingRequest);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testReportBadCase() {
//        // Setup
//        ReportBadCaseRequest reportBadCaseRequest = new ReportBadCaseRequest();
//        reportBadCaseRequest.setBucketName(bucket);
//        reportBadCaseRequest.setContentType("contentType");
//        reportBadCaseRequest.setText("text");
//        reportBadCaseRequest.setLabel("label");
//        reportBadCaseRequest.setSuggestedLabel(SuggestedLabel.Normal);
//        reportBadCaseRequest.setJobId("jobId");
//        reportBadCaseRequest.setModerationTime("moderationTime");
//
//
//        // Run the test
//        String result = cosclient.reportBadCase(reportBadCaseRequest);
//
//        // Verify the results
//        assertEquals("policyText", result);
//    }
//
//    @Test
//    public void testGetPrivateM3U8() {
//        // Setup
//        PrivateM3U8Request privateM3U8Request = new PrivateM3U8Request();
//        privateM3U8Request.setBucketName(bucket);
//        privateM3U8Request.setExpires("expires");
//        privateM3U8Request.setObject("destinationKey");
//
//        // Run the test
//        PrivateM3U8Response result = cosclient.getPrivateM3U8(privateM3U8Request);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testDetectCar() {
//        // Setup
//        DetectCarRequest detectCarRequest = new DetectCarRequest();
//        detectCarRequest.setBucketName(bucket);
//        detectCarRequest.setObjectKey("destinationKey");
//        detectCarRequest.setDetectUrl("detectUrl");
//
//
//        // Run the test
//        DetectCarResponse result = cosclient.detectCar(detectCarRequest);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testOpenImageSearch() {
//        // Setup
//        OpenImageSearchRequest imageSearchRequest = new OpenImageSearchRequest();
//        imageSearchRequest.setBucketName(bucket);
//        imageSearchRequest.setMaxCapacity("maxCapacity");
//        imageSearchRequest.setMaxQps("maxQps");
//
//
//        // Run the test
//        boolean result = cosclient.openImageSearch(imageSearchRequest);
//
//        // Verify the results
//        assertFalse(result);
//    }
//
//    @Test
//    public void testAddGalleryImages() {
//        // Setup
//        ImageSearchRequest imageSearchRequest = new ImageSearchRequest();
//        imageSearchRequest.setObjectKey("destinationKey");
//        imageSearchRequest.setBucketName(bucket);
//        imageSearchRequest.setEntityId("entityId");
//        imageSearchRequest.setCustomContent("customContent");
//        imageSearchRequest.setTags("tags");
//        imageSearchRequest.setMatchThreshold("matchThreshold");
//        imageSearchRequest.setOffset("offset");
//        imageSearchRequest.setLimit("limit");
//        imageSearchRequest.setFilter("filter");
//
//
//        // Run the test
//        boolean result = cosclient.addGalleryImages(imageSearchRequest);
//
//        // Verify the results
//        assertFalse(result);
//    }
//
//    @Test
//    public void testDeleteGalleryImages() {
//        // Setup
//        ImageSearchRequest imageSearchRequest = new ImageSearchRequest();
//        imageSearchRequest.setObjectKey("destinationKey");
//        imageSearchRequest.setBucketName(bucket);
//        imageSearchRequest.setEntityId("entityId");
//        imageSearchRequest.setCustomContent("customContent");
//        imageSearchRequest.setTags("tags");
//        imageSearchRequest.setMatchThreshold("matchThreshold");
//        imageSearchRequest.setOffset("offset");
//        imageSearchRequest.setLimit("limit");
//        imageSearchRequest.setFilter("filter");
//
//
//        // Run the test
//        boolean result = cosclient.deleteGalleryImages(imageSearchRequest);
//
//        // Verify the results
//        assertFalse(result);
//    }
//
//    @Test
//    public void testSearchGalleryImages() {
//        // Setup
//        ImageSearchRequest imageSearchRequest = new ImageSearchRequest();
//        imageSearchRequest.setObjectKey("destinationKey");
//        imageSearchRequest.setBucketName(bucket);
//        imageSearchRequest.setEntityId("entityId");
//        imageSearchRequest.setCustomContent("customContent");
//        imageSearchRequest.setTags("tags");
//        imageSearchRequest.setMatchThreshold("matchThreshold");
//        imageSearchRequest.setOffset("offset");
//        imageSearchRequest.setLimit("limit");
//        imageSearchRequest.setFilter("filter");
//
//
//        // Run the test
//        ImageSearchResponse result = cosclient.searchGalleryImages(imageSearchRequest);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testTriggerWorkflowList() {
//        // Setup
//        MediaWorkflowListRequest mediaWorkflowListRequest = new MediaWorkflowListRequest();
//        mediaWorkflowListRequest.setBucketName(bucket);
//        mediaWorkflowListRequest.setRunId("runId");
//        mediaWorkflowListRequest.setIds("ids");
//        mediaWorkflowListRequest.setName("name");
//        mediaWorkflowListRequest.setPageNumber("pageNumber");
//        mediaWorkflowListRequest.setPageSize("pageSize");
//        mediaWorkflowListRequest.setWorkflowId("workflowId");
//        mediaWorkflowListRequest.setOrderByTime("orderByTime");
//        mediaWorkflowListRequest.setSize("size");
//        mediaWorkflowListRequest.setStates("states");
//        mediaWorkflowListRequest.setStartCreationTime("startCreationTime");
//        mediaWorkflowListRequest.setEndCreationTime("endCreationTime");
//        mediaWorkflowListRequest.setNextToken("nextToken");
//        mediaWorkflowListRequest.setObject("object");
//
//
//        // Run the test
//        MediaWorkflowListResponse result = cosclient.triggerWorkflowList(mediaWorkflowListRequest);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testGetSnapshot() {
//        // Setup
//        CosSnapshotRequest snapshotRequest = new CosSnapshotRequest();
//        snapshotRequest.setTime("time");
//        snapshotRequest.setWidth("width");
//        snapshotRequest.setHeight("height");
//        snapshotRequest.setFormat("format");
//        snapshotRequest.setMode("mode");
//        snapshotRequest.setRotate("rotate");
//        snapshotRequest.setBucketName(bucket);
//        snapshotRequest.setObjectKey("destinationKey");
//
//
//        // Run the test
//        InputStream result = cosclient.getSnapshot(snapshotRequest);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testGenerateQrcode() {
//        // Setup
//        GenerateQrcodeRequest generateQrcodeRequest = new GenerateQrcodeRequest();
//        generateQrcodeRequest.setQrcodeContent("qrcodeContent");
//        generateQrcodeRequest.setMode("mode");
//        generateQrcodeRequest.setWidth("width");
//        generateQrcodeRequest.setBucketName(bucket);
//
//
//        // Run the test
//        String result = cosclient.generateQrcode(generateQrcodeRequest);
//
//        // Verify the results
//        assertEquals("policyText", result);
//    }
//
//    @Test
//    public void testAddImageStyle() {
//        // Setup
//        ImageStyleRequest imageStyleRequest = new ImageStyleRequest();
//        imageStyleRequest.setBucketName(bucket);
//        imageStyleRequest.setStyleName("styleName");
//        imageStyleRequest.setStyleBody("styleBody");
//
//
//        // Run the test
//        Boolean result = cosclient.addImageStyle(imageStyleRequest);
//
//        // Verify the results
//        assertFalse(result);
//    }
//
//    @Test
//    public void testGetImageStyle() {
//        // Setup
//        ImageStyleRequest imageStyleRequest = new ImageStyleRequest();
//        imageStyleRequest.setBucketName(bucket);
//        imageStyleRequest.setStyleName("styleName");
//        imageStyleRequest.setStyleBody("styleBody");
//
//
//        // Run the test
//        ImageStyleResponse result = cosclient.getImageStyle(imageStyleRequest);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testDeleteImageStyle() {
//        // Setup
//        ImageStyleRequest imageStyleRequest = new ImageStyleRequest();
//        imageStyleRequest.setBucketName(bucket);
//        imageStyleRequest.setStyleName("styleName");
//        imageStyleRequest.setStyleBody("styleBody");
//
//
//        // Run the test
//        Boolean result = cosclient.deleteImageStyle(imageStyleRequest);
//
//        // Verify the results
//        assertFalse(result);
//    }
//
//    @Test
//    public void testGetObjectDecompressionStatus1() {
//        // Setup
//
//
//        // Run the test
//        String result = cosclient.getObjectDecompressionStatus(bucket, "destinationKey");
//
//        // Verify the results
//        assertEquals("policyText", result);
//    }
//
//    @Test
//    public void testPostObjectDecompression() {
//        // Setup
//        DecompressionRequest decompressionRequest = new DecompressionRequest();
//        decompressionRequest.setTargetBucketName("targetBucketName");
//        decompressionRequest.setPrefixReplaced(false);
//        decompressionRequest.setResourcesPrefix("resourcesPrefix");
//        decompressionRequest.setTargetKeyPrefix("targetKeyPrefix");
//        decompressionRequest.setObjectKey("destinationKey");
//        decompressionRequest.setSourceBucketName("sourceBucketName");
//
//
//        // Run the test
//        DecompressionResult result = cosclient.postObjectDecompression(decompressionRequest);
//
//        // Verify the results
//    }
//
//
//    @Test
//    public void testCreatePicProcessJob() {
//        // Setup
//        MediaJobsRequest req = new MediaJobsRequest();
//
//
//        req.setBucketName(bucket);
//        req.setQueueId("queueId");
//        req.setTag("tag");
//        req.setOrderByTime("orderByTime");
//        req.setNextToken("nextToken");
//        req.setSize(0);
//        req.setStates("states");
//        req.setStartCreationTime("startCreationTime");
//        req.setEndCreationTime("endCreationTime");
//        req.setJobId("jobId");
//        MediaInputObject input = new MediaInputObject();
//        input.setObject("object");
//        req.setInput(input);
//        req.setCallBack("callBack");
//
//
//        // Run the test
//        MediaJobResponse result = cosclient.createPicProcessJob(req);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testDescribePicProcessQueues() {
//        // Setup
//        MediaQueueRequest req = new MediaQueueRequest();
//
//
//        req.setBucketName(bucket);
//        req.setQueueId("queueId");
//        req.setState("state");
//        req.setPageNumber("pageNumber");
//        req.setPageSize("pageSize");
//        MediaNotifyConfig notifyConfig = new MediaNotifyConfig();
//        notifyConfig.setUrl("url");
//        notifyConfig.setType("type");
//        notifyConfig.setEvent("event");
//        notifyConfig.setState("state");
//        req.setNotifyConfig(notifyConfig);
//        req.setName("name");
//
//
//        // Run the test
//        MediaListQueueResponse result = cosclient.describePicProcessQueues(req);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testProcessImage2() {
//        // Setup
//        CImageProcessRequest imageProcessRequest = new CImageProcessRequest(bucket, "destinationKey");
//
//        // Run the test
//        boolean result = cosclient.processImage2(imageProcessRequest);
//
//        // Verify the results
//        assertFalse(result);
//    }
//
//    @Test
//    public void testCreateFileProcessJob() {
//        // Setup
//        FileProcessRequest req = new FileProcessRequest();
//
//        req.setBucketName(bucket);
//        req.setTag(FileProcessJobType.FileCompress);
//        req.setQueueId("queueId");
//        req.setCallBackFormat("callBackFormat");
//        req.setCallBackType("callBackType");
//        req.setCallBack("callBack");
//        req.setCallBackMqConfig("callBackMqConfig");
//        FileProcessOperation operation = new FileProcessOperation();
//        FileCompressConfig fileCompressConfig = new FileCompressConfig();
//        fileCompressConfig.setFlatten("flatten");
//        fileCompressConfig.setFormat("format");
//        fileCompressConfig.setUrlList("urlList");
//        operation.setFileCompressConfig(fileCompressConfig);
//        req.setOperation(operation);
//        req.setJobId("jobId");
//
//        // Run the test
//        FileProcessJobResponse result = cosclient.createFileProcessJob(req);
//        // Verify the results
//    }
//
//    @Test
//    public void testDescribeFileProcessJob() {
//        // Setup
//        FileProcessRequest request = new FileProcessRequest();
//        request.setBucketName(bucket);
//        request.setTag(FileProcessJobType.FileCompress);
//        request.setQueueId("queueId");
//        request.setCallBackFormat("callBackFormat");
//        request.setCallBackType("callBackType");
//        request.setCallBack("callBack");
//        request.setCallBackMqConfig("callBackMqConfig");
//        FileProcessOperation operation = new FileProcessOperation();
//        FileCompressConfig fileCompressConfig = new FileCompressConfig();
//        fileCompressConfig.setFlatten("flatten");
//        fileCompressConfig.setFormat("format");
//        fileCompressConfig.setUrlList("urlList");
//        operation.setFileCompressConfig(fileCompressConfig);
//        request.setOperation(operation);
//        request.setJobId("jobId");
//
//        // Run the test
//        FileProcessJobResponse result = cosclient.describeFileProcessJob(request);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testCreateInventoryTriggerJob() {
//        // Setup
//        BatchJobRequest req = new BatchJobRequest();
//
//
//        req.setBucketName(bucket);
//        BatchJobOperation operation = new BatchJobOperation();
//        MediaTimeIntervalObject timeInterval = new MediaTimeIntervalObject();
//        timeInterval.setStart("start");
//        timeInterval.setDuration("duration");
//        timeInterval.setEnd("end");
//        operation.setTimeInterval(timeInterval);
//        operation.setTag("tag");
//        MediaOutputObject output = new MediaOutputObject();
//        output.setRegion(region);
//        output.setBucket(bucket);
//        output.setObject("object");
//        output.setSpriteObject("spriteObject");
//        operation.setOutput(output);
//        req.setOperation(operation);
//        req.setJobId("jobId");
//
//
//        // Run the test
//        BatchJobResponse result = cosclient.createInventoryTriggerJob(req);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testDescribeInventoryTriggerJob() {
//        // Setup
//        BatchJobRequest request = new BatchJobRequest();
//        request.setBucketName(bucket);
//        BatchJobOperation operation = new BatchJobOperation();
//        MediaTimeIntervalObject timeInterval = new MediaTimeIntervalObject();
//        timeInterval.setStart("start");
//        timeInterval.setDuration("duration");
//        timeInterval.setEnd("end");
//        operation.setTimeInterval(timeInterval);
//        operation.setTag("tag");
//        MediaOutputObject output = new MediaOutputObject();
//        output.setRegion(region);
//        output.setBucket(bucket);
//        output.setObject("object");
//        output.setSpriteObject("spriteObject");
//        operation.setOutput(output);
//        request.setOperation(operation);
//        request.setJobId("jobId");
//
//        // Run the test
//        BatchJobResponse result = cosclient.describeInventoryTriggerJob(request);
//        // Verify the results
//    }


}
