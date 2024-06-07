package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.region.Region;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PutDelMazBucketTest {
    private static String secretId_ = System.getenv("secretId");
    private static String secretKey_ = System.getenv("secretKey");
    private static String appid_ = System.getenv("appid");
    @Test
    public void testCreateDeleteMazBucket() {
        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        COSClient cosClient = new COSClient(cred, clientConfig);

        String bucketName = System.getenv("bucket") + (int) (System.currentTimeMillis()) + "-" + appid_;
        try {
            CreateBucketRequest request = new CreateBucketRequest(bucketName);
            request.setCannedAcl(CannedAccessControlList.Private);
            Bucket bucket = cosClient.createMAZBucket(request);

            assertEquals(bucketName, bucket.getName());

            Thread.sleep(5 * 1000);
            cosClient.deleteBucket(bucketName);
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
    }
}
