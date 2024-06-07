package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.RenameRequest;
import com.qcloud.cos.region.Region;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

public class OFSRenameObjectTest {
    private static String secretId_ = System.getenv("secretId");
    private static String secretKey_ = System.getenv("secretKey");
    private static String bucket_ = System.getenv("rename_bucket");

    @Test
    public void testRenameObject() {
        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        COSClient cosClient = new COSClient(cred, clientConfig);

        String sourceKey = "test_rename_source";
        String dstKey = "test_rename_dst";
        String content = "hello cos!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket_, sourceKey, inputStream, new ObjectMetadata());
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

            RenameRequest renameRequest = new RenameRequest(bucket_, sourceKey, dstKey);
            cosClient.rename(renameRequest);

            boolean is_exist = cosClient.doesObjectExist(bucket_, dstKey);
            assertEquals(is_exist, true);

            cosClient.deleteObject(bucket_, sourceKey);
            cosClient.deleteObject(bucket_, dstKey);
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
    }
}
