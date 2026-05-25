package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.BatchJobListResponse;
import com.qcloud.cos.model.ciModel.job.BatchJobOperation;
import com.qcloud.cos.model.ciModel.job.BatchJobRequest;
import com.qcloud.cos.model.ciModel.job.BatchJobResponse;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BatchJobTest extends AbstractCOSClientCITest {
    private String batchJobId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    /**
     * 在每个测试前创建一个批量任务，用于后续测试
     */
    @Before
    public void setUp() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("demo-batch-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("out/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            if (response != null && response.getJobDetail() != null) {
                batchJobId = response.getJobDetail().getJobId();
                System.out.println("Created batch job with ID: " + batchJobId);
            }
        } catch (Exception e) {
            System.err.println("Failed to create batch job in setUp: " + e.getMessage());
        }
    }

    /**
     * 测试：触发任务（独立节点）
     * 创建一个基本的批量触发任务
     */
    @Test
    public void testCreateInventoryTriggerJob() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("demo-basic-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            operation.setUserData("test user data");
            operation.setJobLevel("0");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Create Inventory Trigger Job Response: " + response);

        } catch (Exception e) {
            System.err.println("testCreateInventoryTriggerJob failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：触发任务（独立节点）- 使用倍速转码
     * 创建一个带有QueueType的批量触发任务
     */
    @Test
    public void testCreateInventoryTriggerJobWithQueueType() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("demo-speed-transcoding");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setTag("Transcode");
            // 设置QueueType为SpeedTranscoding，开启倍速转码
            operation.setQueueType("SpeedTranscoding");
            operation.setUserData("speed transcoding test");
            operation.setJobLevel("1");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/speed/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Create Job with QueueType Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Job ID: " + response.getJobDetail().getJobId());
                System.out.println("Job State: " + response.getJobDetail().getState());
                System.out.println("QueueType: SpeedTranscoding");
            }
        } catch (Exception e) {
            System.err.println("testCreateInventoryTriggerJobWithQueueType failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：触发任务（工作流类型）
     * 通过工作流ID创建批量触发任务
     */
    @Test
    public void testCreateInventoryTriggerJobWorkflow() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("test-workflow-job");
            request.setType("Workflow");  // 工作流类型
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            // 设置工作流ID（需要替换为实际的工作流ID）
            operation.setWorkflowIds("wa6695cd4ddef46e6ad7c958f537d3574");
            
            // 可选：设置时间过滤条件
            operation.getTimeInterval().setStart("2022-02-01T12:00:00+0800");
            operation.getTimeInterval().setEnd("2025-12-31T23:59:59+0800");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Create Workflow Job Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Job ID: " + response.getJobDetail().getJobId());
                System.out.println("Job Type: " + response.getJobDetail().getType());
                System.out.println("Job State: " + response.getJobDetail().getState());
                System.out.println("Workflow IDs: " + response.getJobDetail().getOperation().getWorkflowIds());
            }
        } catch (Exception e) {
            System.err.println("testCreateInventoryTriggerJobWorkflow failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：触发任务（工作流类型 + COS清单）
     * 通过工作流ID和COS清单文件创建批量触发任务
     */
    @Test
    public void testCreateInventoryTriggerJobWorkflowWithManifest() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("test-workflow-manifest-job");
            request.setType("Workflow");  // 工作流类型
            
            // 使用COS清单文件指定待处理的对象列表
            request.getInput().setManifest("manifest.json");
            
            BatchJobOperation operation = request.getOperation();
            // 设置工作流ID
            operation.setWorkflowIds("wa6695cd4ddef46e6ad7c958f537d3574");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Create Workflow Job with Manifest Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Job ID: " + response.getJobDetail().getJobId());
                System.out.println("Job Type: " + response.getJobDetail().getType());
                System.out.println("Input Manifest: " + response.getJobDetail().getInput().getManifest());
            }
        } catch (Exception e) {
            System.err.println("testCreateInventoryTriggerJobWorkflowWithManifest failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：触发任务（工作流类型 + URL文件）
     * 通过工作流ID和URL文件创建批量触发任务
     */
    @Test
    public void testCreateInventoryTriggerJobWorkflowWithUrlFile() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("test-workflow-urlfile-job");
            request.setType("Workflow");  // 工作流类型
            
            // 使用URL文件指定待处理的对象列表
            request.getInput().setUrlFile("url-list.txt");
            
            BatchJobOperation operation = request.getOperation();
            // 设置工作流ID
            operation.setWorkflowIds("wa6695cd4ddef46e6ad7c958f537d3574");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Create Workflow Job with UrlFile Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Job ID: " + response.getJobDetail().getJobId());
                System.out.println("Job Type: " + response.getJobDetail().getType());
                System.out.println("Input UrlFile: " + response.getJobDetail().getInput().getUrlFile());
            }
        } catch (Exception e) {
            System.err.println("testCreateInventoryTriggerJobWorkflowWithUrlFile failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：触发任务（工作流类型 + 单个对象）
     * 通过工作流ID处理单个对象
     */
    @Test
    public void testCreateInventoryTriggerJobWorkflowWithObject() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("test-workflow-object-job");
            request.setType("Workflow");  // 工作流类型
            
            // 指定单个对象
            request.getInput().setObject("media/test-video.mp4");
            
            BatchJobOperation operation = request.getOperation();
            // 设置工作流ID
            operation.setWorkflowIds("wa6695cd4ddef46e6ad7c958f537d3574");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Create Workflow Job with Object Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Job ID: " + response.getJobDetail().getJobId());
                System.out.println("Job Type: " + response.getJobDetail().getType());
                System.out.println("Input Object: " + response.getJobDetail().getInput().getObject());
            }
        } catch (Exception e) {
            System.err.println("testCreateInventoryTriggerJobWorkflowWithObject failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：查询单个任务
     * 根据任务ID查询批量任务的详细信息
     */
    @Test
    public void testDescribeInventoryTriggerJob() {
        try {
            if (batchJobId == null || batchJobId.isEmpty()) {
                System.out.println("No batch job ID available, skipping test");
                return;
            }
            
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setJobId(batchJobId);
            
            BatchJobResponse response = cosclient.describeInventoryTriggerJob(request);
            System.out.println("Describe Inventory Trigger Job Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Job ID: " + response.getJobDetail().getJobId());
                System.out.println("Job Name: " + response.getJobDetail().getName());
                System.out.println("Job State: " + response.getJobDetail().getState());
                System.out.println("Job Type: " + response.getJobDetail().getType());
                System.out.println("Creation Time: " + response.getJobDetail().getCreationTime());
            }
        } catch (Exception e) {
            System.err.println("testDescribeInventoryTriggerJob failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：批量拉取任务
     * 查询批量任务列表
     */
    @Test
    public void testDescribeInventoryTriggerJobs() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setOrderByTime("Desc");
            request.setSize(10);
            request.setStates("All");
            
            BatchJobListResponse response = cosclient.describeInventoryTriggerJobs(request);
            System.out.println("Describe Inventory Trigger Jobs Response: " + response);
            
            if (response != null) {
                System.out.println("Total jobs: " + response.getJobsDetailList().size());
                System.out.println("NextToken: " + response.getNextToken());
                
                // 打印每个任务的基本信息
                response.getJobsDetailList().forEach(job -> {
                    System.out.println("  - Job ID: " + job.getJobId() + 
                                     ", Name: " + job.getName() + 
                                     ", State: " + job.getState());
                });
            }
        } catch (Exception e) {
            System.err.println("testDescribeInventoryTriggerJobs failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：批量拉取任务（带分页）
     * 测试使用nextToken进行分页查询
     */
    @Test
    public void testDescribeInventoryTriggerJobsWithPagination() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setOrderByTime("Desc");
            request.setSize(5);
            request.setStates("All");

            // 第一页
            BatchJobListResponse response = cosclient.describeInventoryTriggerJobs(request);
            System.out.println(response);
            System.out.println("First page - Total jobs: " + response.getJobsDetailList().size());
            System.out.println("NextToken: " + response.getNextToken());
            
            // 如果有下一页，继续查询
            if (response.getNextToken() != null && !response.getNextToken().isEmpty()) {
                request.setNextToken(response.getNextToken());
                BatchJobListResponse nextPageResponse = cosclient.describeInventoryTriggerJobs(request);
                System.out.println("Second page - Total jobs: " + nextPageResponse.getJobsDetailList().size());
                System.out.println("NextToken: " + nextPageResponse.getNextToken());
            }
        } catch (Exception e) {
            System.err.println("testDescribeInventoryTriggerJobsWithPagination failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：取消任务
     * 取消一个正在运行的批量任务
     */
    @Test
    public void testCancelInventoryTriggerJob() {
        try {
            if (batchJobId == null || batchJobId.isEmpty()) {
                System.out.println("No batch job ID available, skipping test");
                return;
            }
            
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setJobId(batchJobId);
            
            Boolean response = cosclient.cancelInventoryTriggerJob(request);
            System.out.println("Cancel Inventory Trigger Job Result: " + response);

            if (response) {
                System.out.println("Successfully cancelled job with ID: " + batchJobId);
            }
        } catch (CosServiceException e) {
            // 如果任务已经完成或不存在，会抛出异常，这是正常的
            System.out.println("Cancel job failed (expected if job already completed): " + e.getMessage());
            System.out.println("Error Code: " + e.getErrorCode());
        } catch (Exception e) {
            System.err.println("testCancelInventoryTriggerJob failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：按状态过滤任务
     * 测试查询特定状态的任务
     */
    @Test
    public void testDescribeInventoryTriggerJobsByState() {
        try {
            // 测试查询成功的任务
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setOrderByTime("Desc");
            request.setSize(10);
            request.setStates("Success");
            
            BatchJobListResponse response = cosclient.describeInventoryTriggerJobs(request);
            System.out.println("Success jobs count: " + response.getJobsDetailList().size());
            
            // 测试查询失败的任务
            request.setStates("Failed");
            response = cosclient.describeInventoryTriggerJobs(request);
            System.out.println("Failed jobs count: " + response.getJobsDetailList().size());
            
            // 测试查询运行中的任务
            request.setStates("Running");
            response = cosclient.describeInventoryTriggerJobs(request);
            System.out.println(response);
            System.out.println("Running jobs count: " + response.getJobsDetailList().size());
        } catch (Exception e) {
            System.err.println("testDescribeInventoryTriggerJobsByState failed: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 测试：视频转动图任务 (Animation)
     * Tag: Animation
     */
    @Test
    public void testCreateBatchJobAnimation() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-animation-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Animation");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/animation/${InventoryTriggerJobId}.gif");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Animation Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobAnimation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：智能封面任务 (SmartCover)
     * Tag: SmartCover
     */
    @Test
    public void testCreateBatchJobSmartCover() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-smartcover-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("SmartCover");
            operation.getJobParam().setTemplateId("t1421fd81481d240bc83ac5e23e06bb7a3");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/cover/${InventoryTriggerJobId}_${Number}.jpg");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("SmartCover Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobSmartCover failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：视频截图任务 (Snapshot)
     * Tag: Snapshot
     */
    @Test
    public void testCreateBatchJobSnapshot() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-snapshot-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Snapshot");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/snapshot/${InventoryTriggerJobId}_${Number}.jpg");
            // 雪碧图输出
            output.setSpriteObject("output/sprite/${InventoryTriggerJobId}.jpg");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Snapshot Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobSnapshot failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：语音识别任务 (SpeechRecognition)
     * Tag: SpeechRecognition
     */
    @Test
    public void testCreateBatchJobSpeechRecognition() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-speech-recognition-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("SpeechRecognition");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/speech/${InventoryTriggerJobId}.txt");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("SpeechRecognition Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobSpeechRecognition failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：音视频拼接任务 (Concat)
     * Tag: Concat
     */
    @Test
    public void testCreateBatchJobConcat() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-concat-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Concat");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/concat/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Concat Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobConcat failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：精彩集锦任务 (VideoMontage)
     * Tag: VideoMontage
     */
    @Test
    public void testCreateBatchJobVideoMontage() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-video-montage-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("VideoMontage");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/montage/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("VideoMontage Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobVideoMontage failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：SDR to HDR 任务
     * Tag: SDRtoHDR
     */
    @Test
    public void testCreateBatchJobSDRtoHDR() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-sdr-to-hdr-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("SDRtoHDR");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/hdr/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("SDRtoHDR Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobSDRtoHDR failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：人声分离任务 (VoiceSeparate)
     * Tag: VoiceSeparate
     * 注意：人声分离需要设置 AuObject 输出人声结果
     */
    @Test
    public void testCreateBatchJobVoiceSeparate() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-voice-separate-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("VoiceSeparate");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/voice/background_${InventoryTriggerJobId}.mp3");
            output.setAuObject("output/voice/vocal_${InventoryTriggerJobId}.mp3");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("VoiceSeparate Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobVoiceSeparate failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：视频增强任务 (VideoProcess)
     * Tag: VideoProcess
     */
    @Test
    public void testCreateBatchJobVideoProcess() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-video-process-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("VideoProcess");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/enhanced/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("VideoProcess Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobVideoProcess failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：超级分辨率任务 (SuperResolution)
     * Tag: SuperResolution
     */
    @Test
    public void testCreateBatchJobSuperResolution() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-super-resolution-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("SuperResolution");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/super/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("SuperResolution Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobSuperResolution failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：音视频转封装任务 (Segment)
     * Tag: Segment
     */
    @Test
    public void testCreateBatchJobSegment() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-segment-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Segment");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/segment/${InventoryTriggerJobId}.m3u8");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Segment Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobSegment failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：视频标签任务 (VideoTag)
     * Tag: VideoTag
     * 注意：VideoTag 不需要 Output 参数
     */
    @Test
    public void testCreateBatchJobVideoTag() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-video-tag-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("VideoTag");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("VideoTag Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobVideoTag failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：数字水印任务 (DigitalWatermark)
     * Tag: DigitalWatermark
     */
    @Test
    public void testCreateBatchJobDigitalWatermark() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-digital-watermark-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("DigitalWatermark");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/watermark/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("DigitalWatermark Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobDigitalWatermark failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：提取数字水印任务 (ExtractDigitalWatermark)
     * Tag: ExtractDigitalWatermark
     * 注意：ExtractDigitalWatermark 不需要 Output 参数
     */
    @Test
    public void testCreateBatchJobExtractDigitalWatermark() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-extract-watermark-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("ExtractDigitalWatermark");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("ExtractDigitalWatermark Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobExtractDigitalWatermark failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：异步获取媒体信息任务 (MediaInfo)
     * Tag: MediaInfo
     * 注意：MediaInfo 不需要 Output 参数
     */
    @Test
    public void testCreateBatchJobMediaInfo() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-media-info-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("MediaInfo");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("MediaInfo Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobMediaInfo failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：语音合成任务 (Tts)
     * Tag: Tts
     */
    @Test
    public void testCreateBatchJobTts() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-tts-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Tts");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/tts/${InventoryTriggerJobId}.mp3");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Tts Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobTts failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：音频降噪任务 (NoiseReduction)
     * Tag: NoiseReduction
     */
    @Test
    public void testCreateBatchJobNoiseReduction() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-noise-reduction-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("NoiseReduction");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/noise/${InventoryTriggerJobId}.mp3");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("NoiseReduction Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobNoiseReduction failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：视频质量分析任务 (QualityEstimate)
     * Tag: QualityEstimate
     * 注意：QualityEstimate 不需要 Output 参数
     */
    @Test
    public void testCreateBatchJobQualityEstimate() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-quality-estimate-job");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("QualityEstimate");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("QualityEstimate Job Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobQualityEstimate failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== 高级参数测试 ====================

    /**
     * 测试：使用 HTTP 回调配置
     * 测试 CallBackFormat、CallBackType、CallBack 参数
     */
    @Test
    public void testCreateBatchJobWithHttpCallback() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-job-with-http-callback");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            // 设置回调参数
            operation.setCallBackFormat("JSON");  // 回调格式：JSON 或 XML
            operation.setCallBackType("Url");     // 回调类型：Url 或 TDMQ
            operation.setCallBack("https://example.com/callback");  // 回调地址
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/callback/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Job with HTTP Callback Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("CallBack: " + response.getJobDetail().getOperation().getCallBack());
                System.out.println("CallBackFormat: " + response.getJobDetail().getOperation().getCallBackFormat());
                System.out.println("CallBackType: " + response.getJobDetail().getOperation().getCallBackType());
            }
        } catch (Exception e) {
            System.err.println("testCreateBatchJobWithHttpCallback failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：使用 TDMQ 回调配置
     * 测试 CallBackMqConfig 参数
     */
    @Test
    public void testCreateBatchJobWithTDMQCallback() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-job-with-tdmq-callback");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            // 设置 TDMQ 回调参数
            operation.setCallBackType("TDMQ");
            operation.getCallBackMqConfig().setMqRegion("sh");      // 消息队列所属园区
            operation.getCallBackMqConfig().setMqMode("Queue");     // 使用模式：Queue 或 Topic
            operation.getCallBackMqConfig().setMqName("test-queue"); // TDMQ 主题名称
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/tdmq/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Job with TDMQ Callback Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobWithTDMQCallback failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：使用时间过滤
     * 测试 TimeInterval 参数（独立节点类型）
     */
    @Test
    public void testCreateBatchJobWithTimeInterval() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-job-with-time-filter");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            // 设置时间过滤条件：只处理指定时间范围内上传的文件
            operation.getTimeInterval().setStart("2024-01-01T00:00:00+0800");
            operation.getTimeInterval().setEnd("2024-12-31T23:59:59+0800");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/time-filter/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Job with Time Filter Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Time Start: " + response.getJobDetail().getOperation().getTimeInterval().getStart());
                System.out.println("Time End: " + response.getJobDetail().getOperation().getTimeInterval().getEnd());
            }
        } catch (Exception e) {
            System.err.println("testCreateBatchJobWithTimeInterval failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：使用 COS 清单文件
     * 测试 Input.Manifest 参数
     */
    @Test
    public void testCreateBatchJobWithManifest() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-job-with-manifest");
            request.setType("Job");
            
            // 使用 COS 清单文件指定待处理的对象列表
            request.getInput().setManifest("inventory/manifest.json");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/manifest/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Job with Manifest Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Input Manifest: " + response.getJobDetail().getInput().getManifest());
            }
        } catch (Exception e) {
            System.err.println("testCreateBatchJobWithManifest failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：使用 URL 文件
     * 测试 Input.UrlFile 参数
     */
    @Test
    public void testCreateBatchJobWithUrlFile() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-job-with-urlfile");
            request.setType("Job");
            
            // 使用 URL 文件指定待处理的对象列表
            // 文件中每行的 URL 为一个 COS 中对象的访问地址
            request.getInput().setUrlFile("url-list.txt");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/urlfile/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Job with UrlFile Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Input UrlFile: " + response.getJobDetail().getInput().getUrlFile());
            }
        } catch (Exception e) {
            System.err.println("testCreateBatchJobWithUrlFile failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：使用单个对象
     * 测试 Input.Object 参数
     */
    @Test
    public void testCreateBatchJobWithSingleObject() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-job-with-single-object");
            request.setType("Job");
            
            // 指定单个对象进行处理
            request.getInput().setObject("media/test-video.mp4");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/single/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Job with Single Object Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Input Object: " + response.getJobDetail().getInput().getObject());
            }
        } catch (Exception e) {
            System.err.println("testCreateBatchJobWithSingleObject failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：完整参数配置
     * 测试所有可选参数的组合使用
     */
    @Test
    public void testCreateBatchJobWithFullParameters() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-job-full-params");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            
            // 队列配置
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            
            // 任务参数
            operation.setUserData("custom-user-data-12345");
            operation.setJobLevel("2");  // 最高优先级
            
            // 回调配置
            operation.setCallBackFormat("JSON");
            operation.setCallBackType("Url");
            operation.setCallBack("https://example.com/batch-callback");
            
            // 时间过滤
            operation.getTimeInterval().setStart("2024-01-01T00:00:00+0800");
            operation.getTimeInterval().setEnd("2024-12-31T23:59:59+0800");
            
            // 任务模板
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            // 输出配置
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/full-params/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Job with Full Parameters Response: " + response);
            
            if (response != null && response.getJobDetail() != null) {
                System.out.println("Job ID: " + response.getJobDetail().getJobId());
                System.out.println("Job Name: " + response.getJobDetail().getName());
                System.out.println("Job Level: " + response.getJobDetail().getOperation().getJobLevel());
                System.out.println("User Data: " + response.getJobDetail().getOperation().getUserData());
                System.out.println("CallBack: " + response.getJobDetail().getOperation().getCallBack());
            }
        } catch (Exception e) {
            System.err.println("testCreateBatchJobWithFullParameters failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：禁用队列回调
     * 设置 CallBack 为 "no" 表示不使用队列的回调地址
     */
    @Test
    public void testCreateBatchJobWithNoCallback() {
        try {
            BatchJobRequest request = new BatchJobRequest();
            request.setBucketName(bucket);
            request.setName("batch-job-no-callback");
            request.setType("Job");
            request.getInput().setPrefix("media/");
            
            BatchJobOperation operation = request.getOperation();
            operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
            operation.setTag("Transcode");
            operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            
            // 设置为 "no" 表示不使用队列的回调地址
            operation.setCallBack("no");
            
            MediaOutputObject output = operation.getOutput();
            output.setRegion(region);
            output.setBucket(bucket);
            output.setObject("output/no-callback/${InventoryTriggerJobId}.mp4");
            
            BatchJobResponse response = cosclient.createInventoryTriggerJob(request);
            System.out.println("Job with No Callback Response: " + response);
        } catch (Exception e) {
            System.err.println("testCreateBatchJobWithNoCallback failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
