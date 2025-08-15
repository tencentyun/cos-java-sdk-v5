package com.qcloud.cos.demo.ci;
import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.FileProcessJobResponse;
import com.qcloud.cos.model.ciModel.job.FileProcessRequest;
import com.qcloud.cos.model.ciModel.job.FileProcessJobType;
import com.qcloud.cos.model.ciModel.job.FileProcessOperation;
import com.qcloud.cos.model.ciModel.job.FileUnCompressConfig;
import com.qcloud.cos.region.Region;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileUnCompressTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(AbstractCOSClientCITest.region);
        ClientConfig clientConfig = new ClientConfig(region);
        cosclient = new COSClient(cred, clientConfig);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    /**
     * 测试带ListingFile参数的文件解压任务创建
     */
    @Test
    public void testCreateFileUncompressJobWithListingFile() {
        FileProcessRequest request = new FileProcessRequest();

        request.setBucketName(bucket);
        request.setTag(FileProcessJobType.FileUncompress);

        request.getInput().setObject("demo.txt.zip");

        FileProcessOperation operation = request.getOperation();
        FileUnCompressConfig unCompressConfig = operation.getFileUnCompressConfig();
        unCompressConfig.setPrefix("output/");
        unCompressConfig.setPrefixReplaced("1");
        unCompressConfig.setListingFile(true);

        MediaOutputObject output = operation.getOutput();
        output.setBucket(bucket);
        output.setRegion(AbstractCOSClientCITest.region);

        FileProcessJobResponse response = null;
        try {
            response = cosclient.createFileProcessJob(request);
        } catch (CosServiceException e) {
            e.printStackTrace();
        }

        assertNotNull(response);
        assertNotNull(response.getJobDetail());
        System.out.println(response.getJobDetail().getJobId());
    }
}