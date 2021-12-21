package com.qcloud.cos.demo.fetch;

import java.util.Map.Entry;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.endpoint.EndpointBuilder;
import com.qcloud.cos.endpoint.UserSpecifiedEndpointBuilder;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.fetch.GetAsyncFetchTaskRequest;
import com.qcloud.cos.model.fetch.GetAsyncFetchTaskResult;
import com.qcloud.cos.region.Region;

public class GetAsyncFetchTaskDemo {
    static String ak = System.getProperty("COS_AK");
    static String sk = System.getProperty("COS_SK");

    static String region = "ap-shanghai";
    static String bucketName = "test-1250000000";

    static String taskId = "Nj00000000000000000000000000000000000000";

    public static COSClient createCosClient() {

        COSCredentials cred = new BasicCOSCredentials(ak, sk);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.http);

        return new COSClient(cred, clientConfig);
    }

    public static void getAsyncFetchTask() {
        EndpointBuilder builder = new UserSpecifiedEndpointBuilder(
                String.format("%s.migration.myqcloud.com", region), "service.cos.myqcloud.com");

        COSClient cosClient = createCosClient();
        cosClient.getClientConfig().setEndpointBuilder(builder);

        GetAsyncFetchTaskRequest request = new GetAsyncFetchTaskRequest();
        request.setBucketName(bucketName);
        request.setTaskId(taskId);

        GetAsyncFetchTaskResult result = cosClient.getAsyncFetchTask(request);
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
        getAsyncFetchTask();
    }
}
