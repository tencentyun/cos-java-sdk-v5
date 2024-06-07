package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.FileCompressConfig;
import com.qcloud.cos.model.ciModel.job.FileProcessJobResponse;
import com.qcloud.cos.model.ciModel.job.FileProcessJobType;
import com.qcloud.cos.model.ciModel.job.FileProcessRequest;
import com.qcloud.cos.model.ciModel.job.ZipPreviewRequest;
import com.qcloud.cos.model.ciModel.job.ZipPreviewResponse;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class FileProcessTest extends AbstractCOSClientCITest {
    private String jobId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-shanghai");
        ClientConfig clientConfig = new ClientConfig(region);
        cosclient = new COSClient(cred, clientConfig);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Before
    public void createFileProcessJobTest() {
        try {
            //1.创建任务请求对象
            FileProcessRequest request = new FileProcessRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName("shanghaitest-1251704708");
            request.setTag(FileProcessJobType.FileCompress);
            FileCompressConfig fileCompressConfig = request.getOperation().getFileCompressConfig();
            fileCompressConfig.setFormat("zip");
            fileCompressConfig.setFlatten("0");
            List<String> keyList = fileCompressConfig.getKey();
            keyList.add("mark/pic-1.jpg");
            keyList.add("mark/pic-1.pdf");

            request.setQueueId("p1ff062b35a494cf0ac4b572df22a5650");

            MediaOutputObject output = request.getOperation().getOutput();
            output.setBucket("shanghaitest-1251704708");
            output.setRegion("ap-shanghai");
            output.setObject("output/demo.zip");

            //3.调用接口,获取任务响应对象
            FileProcessJobResponse response = cosclient.createFileProcessJob(request);
            jobId = response.getJobDetail().getJobId();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void describeFileProcessJobTest() {
        try {
            //1.创建任务请求对象
            FileProcessRequest request = new FileProcessRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName("shanghaitest-1251704708");
            request.setJobId(jobId);
            //3.调用接口,获取任务响应对象
            FileProcessJobResponse response = cosclient.describeFileProcessJob(request);
        } catch (Exception e) {

        }

    }

    @Test
    public void zipPreviewTest() {
        try {
            ZipPreviewRequest request = new ZipPreviewRequest();
            request.setBucketName("demo-1234567890");
            request.setObjectKey("filelist_encrypt.rar");
            ZipPreviewResponse response = cosclient.zipPreview(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {

        }

    }
}
