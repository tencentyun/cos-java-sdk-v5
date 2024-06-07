package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketRequest;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaListJobResponse;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoRequest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoResponse;
import com.qcloud.cos.model.ciModel.queue.MediaListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaQueueRequest;
import com.qcloud.cos.model.ciModel.snapshot.CosSnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Request;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Response;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotResponse;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MediaJobTest extends AbstractCOSClientCITest {

    private String jobId;
    public static final String TAG = "Transcode";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            AbstractCOSClientCITest.initCosClient();
        }catch (Exception e){

        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Before
    public void createMediaJobTest() {
        try {
            MediaJobsRequest request = new MediaJobsRequest();
            request.setBucketName(bucket);
            request.setTag("Transcode");
            request.getInput().setObject("1.mp4");
            request.getOperation().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
            request.getOperation().getOutput().setBucket(bucket);
            request.getOperation().getOutput().setRegion("ap-chongqing");
            request.getOperation().getOutput().setObject("2.mp4");
            request.setQueueId("p9900025e4ec44b5e8225e70a52170834");
            MediaJobResponse response = cosclient.createMediaJobs(request);
            jobId = response.getJobsDetail().getJobId();
        } catch (Exception e) {

        }

    }

    @Test(expected = NullPointerException.class)
    public void createMediaJobTest2() {
        //1.创建任务请求对象
        MediaJobsRequest request = null;
        MediaJobResponse response = cosclient.createMediaJobs(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMediaJobTest3() {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        MediaJobResponse response = cosclient.createMediaJobs(request);
    }

    @Test
    public void cancelMediaJobTest() {
        try {
            MediaJobsRequest request = new MediaJobsRequest();
            request.setBucketName(bucket);
            request.setJobId(jobId);
            Boolean response = cosclient.cancelMediaJob(request);
            assertTrue(response);
        } catch (Exception e) {

        }

    }

    @Test
    public void createMediaBucketTest() {
        try {
            MediaBucketRequest request = new MediaBucketRequest();
            request.setBucketName(bucket);
            Boolean response = cosclient.createMediaProcessBucket(request);
            assertTrue(response);
        } catch (Exception e) {

        }
    }

    @Test
    public void describeMediaJobTest() {
        try {
            if (!judgeUserInfoValid()) {
                return;
            }
            MediaJobsRequest request = new MediaJobsRequest();
            request.setBucketName(bucket);
            request.setJobId(jobId);
            MediaJobResponse mediaJobResponse = cosclient.describeMediaJob(request);
        } catch (Exception e) {

        }

    }

    @Test
    public void describeMediaJobsTest() {
        try {
            if (!judgeUserInfoValid()) {
                return;
            }
            MediaQueueRequest queueRequest = new MediaQueueRequest();
            queueRequest.setBucketName(bucket);
            MediaListQueueResponse queueResponse = cosclient.describeMediaQueues(queueRequest);
            if (queueResponse != null && queueResponse.getQueueList().size() != 0) {
                MediaJobsRequest request = new MediaJobsRequest();
                request.setBucketName(bucket);
                String queueId = queueResponse.getQueueList().get(0).getQueueId();
                request.setQueueId(queueId);
                request.setTag(TAG);
                MediaListJobResponse response = cosclient.describeMediaJobs(request);
                List<MediaJobObject> jobsDetail = response.getJobsDetailList();
                for (MediaJobObject mediaJobObject : jobsDetail) {
                    assertEquals(TAG, mediaJobObject.getTag());
                    assertEquals(queueId, mediaJobObject.getQueueId());
                }
            }
        } catch (Exception e) {

        }

    }

    @Test
    public void generateSnapshotTest() {
        try {
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
            System.out.println(response);
        } catch (Exception e) {
        }
    }

    @Test
    public void generateSnapshotTest2() {
        try {
            SnapshotRequest request = new SnapshotRequest();
            request.setBucketName(bucket);
            request.getInput().setObject("1.mp4");
            request.getOutput().setBucket(bucket);
            request.getOutput().setRegion(region);
            request.getOutput().setObject("test/1.jpg");
            SnapshotResponse response = cosclient.generateSnapshot(request);
            System.out.println(response);
        }catch (Exception e){

        }

    }

    @Test
    public void generateSnapshotTest3() {
        try {
            SnapshotRequest request = new SnapshotRequest();
            request.setBucketName(bucket);
            request.getOutput().setBucket(bucket);
            request.getOutput().setRegion(region);
            request.getOutput().setObject("test/1.jpg");
            request.setTime("15");
            SnapshotResponse response = cosclient.generateSnapshot(request);
            System.out.println(response);
        }catch (Exception e){

        }

    }

    @Test
    public void generateMediainfoTest() {
        try {
            MediaInfoRequest request = new MediaInfoRequest();
            request.setBucketName(bucket);
            request.getInput().setObject("1.mp3");
            MediaInfoResponse response = cosclient.generateMediainfo(request);
        }catch (Exception e){

        }

    }


    @Test(expected = IllegalArgumentException.class)
    public void generateMediainfoTest2() {
        //1.创建媒体信息请求对象
        MediaInfoRequest request = new MediaInfoRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        //3.调用接口,获取媒体信息响应对象
        MediaInfoResponse response = cosclient.generateMediainfo(request);
        System.out.println(response.getRequestId());
    }

    @Test
    public void getPrivateM3U8Test() {
        //1.创建截图请求对象
        PrivateM3U8Request request = new PrivateM3U8Request();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        //私有 ts 资源 url 下载凭证的相对有效期，单位为秒，范围为[3600, 43200]
        request.setExpires("3600");
        request.setObject("1.m3u8");
        //3.调用接口,获取截图响应对象
        PrivateM3U8Response privateM3U8 = cosclient.getPrivateM3U8(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPrivateM3U8Test2() {
        //1.创建截图请求对象
        PrivateM3U8Request request = new PrivateM3U8Request();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        //私有 ts 资源 url 下载凭证的相对有效期，单位为秒，范围为[3600, 43200]
        request.setObject("1.m3u8");
        //3.调用接口,获取截图响应对象
        PrivateM3U8Response privateM3U8 = cosclient.getPrivateM3U8(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPrivateM3U8Test3() {
        //1.创建截图请求对象
        PrivateM3U8Request request = new PrivateM3U8Request();
        //2.添加请求参数 参数详情请见api接口文档
        //私有 ts 资源 url 下载凭证的相对有效期，单位为秒，范围为[3600, 43200]
        request.setObject("1.m3u8");
        request.setExpires("3600");
        //3.调用接口,获取截图响应对象
        PrivateM3U8Response privateM3U8 = cosclient.getPrivateM3U8(request);
    }

    @Test
    public void getSnapshot() {
        try {
            CosSnapshotRequest request = new CosSnapshotRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName(bucket);
            request.setObjectKey("2.mp4");
            request.setTime("15");
            request.setFormat("jpg");
            //3.调用接口,获取截图响应对象
            InputStream snapshot = cosclient.getSnapshot(request);
        } catch (Exception e) {

        }
    }

    @Test
    public void testCreateMediaProcessBucket() {
        try {
            MediaBucketRequest request = new MediaBucketRequest();
            request.setBucketName(bucket);
            Boolean response = cosclient.createMediaProcessBucket(request);
            assertTrue(response);
        } catch (Exception e) {
        }
    }

}
