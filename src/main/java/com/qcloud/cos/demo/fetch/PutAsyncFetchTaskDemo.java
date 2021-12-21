package com.qcloud.cos.demo.fetch;

import java.util.Map.Entry;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.endpoint.EndpointBuilder;
import com.qcloud.cos.endpoint.UserSpecifiedEndpointBuilder;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.fetch.PutAsyncFetchTaskRequest;
import com.qcloud.cos.model.fetch.PutAsyncFetchTaskResult;
import com.qcloud.cos.region.Region;

public class PutAsyncFetchTaskDemo {
    static String ak = System.getProperty("COS_AK");
    static String sk = System.getProperty("COS_SK");

    static String region = "ap-shanghai";
    static String bucketName = "test-1250000000";

    public static COSClient createCosClient() {

        COSCredentials cred = new BasicCOSCredentials(ak, sk);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.http);
        clientConfig.setSocketTimeout(60*1000);

        return new COSClient(cred, clientConfig);
    }

    public static void putAsyncFetchTask() {
        EndpointBuilder builder = new UserSpecifiedEndpointBuilder(
                String.format("%s.migration.myqcloud.com", region), "service.cos.myqcloud.com");

        COSClient cosClient = createCosClient();
        cosClient.getClientConfig().setEndpointBuilder(builder);

        PutAsyncFetchTaskRequest request = new PutAsyncFetchTaskRequest();

        request.setBucketName(bucketName);
        request.setUrl("https://pic1.zhimg.com/v2-63fc555d77019ae08ac2281a2418dcc7_720w.jpg");
        request.setIgnoreSameKey(false);
        request.setKey("test_fetch");
        request.setOnKeyExist("override");

        // 可选
        // reqeust.setSuccessCallbackUrl("");
        // request.setFailureCallbackUrl("");
        // request.setMd5("");

        PutAsyncFetchTaskResult result = cosClient.putAsyncFetchTask(request);
        System.out.println(result.getCosRequestId());
        System.out.println(result.getCode());
        System.out.println(result.getMessage());
        System.out.println(result.getRequestId());
        for (Entry<String, String> entry : result.getData().entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    public static void main(String [] args) {
        putAsyncFetchTask();
    }
}
