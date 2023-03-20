package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.DefaultCosHttpClient;
import com.qcloud.cos.internal.DeleteObjectsResponse;
import com.qcloud.cos.model.DeleteObjectsRequest;
import com.qcloud.cos.region.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest({COSClient.class, ClientConfig.class})
public class DelWithErrorTest {

    private String appid_ = System.getenv("appid");
    private String secretId_ = System.getenv("secretId");
    private String secretKey_ = System.getenv("secretKey");
    private String region_ = System.getenv("region");
    private String bucket_ = System.getenv("bucket") + (int) (Math.random() * 1000) + "-" + appid_;

    @Test
    public void testDelWithError() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucket_);
        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<>();
        // 传入要删除的文件名
        keyList.add(new DeleteObjectsRequest.KeyVersion("test1"));
        keyList.add(new DeleteObjectsRequest.KeyVersion("test2"));
        deleteObjectsRequest.setKeys(keyList);

        CosHttpRequest<DeleteObjectsRequest> request = new CosHttpRequest<DeleteObjectsRequest>(deleteObjectsRequest);

        DeleteObjectsResponse response = new DeleteObjectsResponse();

        List<MultiObjectDeleteException.DeleteError> errorList = new ArrayList<>();
        MultiObjectDeleteException.DeleteError error = new MultiObjectDeleteException.DeleteError();
        error.setKey("test1");
        errorList.add(error);

        response.setErrors(errorList);

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(response);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient client = new COSClient(cred, clientConfig);

        try {
            client.deleteObjects(deleteObjectsRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            client.shutdown();
        }
    }
}
