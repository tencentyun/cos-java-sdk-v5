package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.endpoint.UserSpecifiedEndpointBuilder;
import com.qcloud.cos.http.HttpProtocol;

public class GetObjectURLDemo {
    public static void main(String[] args) {
        getObjectUrlWithEndpoint();
    }

    public static void getObjectUrl() {
        // getObjectUrl 不需要验证身份信息
        COSCredentials cred = new AnonymousCOSCredentials();

        ClientConfig clientConfig = new ClientConfig();

        // 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        // 设置生成的 url 的协议名
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        String key = "test/my_test中文.json";
        String bucketName = "mybucket-1251668577";

        System.out.println(cosclient.getObjectUrl(bucketName, key));
    }

    public static void getObjectUrlWithVersionId() {
        // getObjectUrl 不需要验证身份信息
        COSCredentials cred = new AnonymousCOSCredentials();

        ClientConfig clientConfig = new ClientConfig();

        // 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        // 设置生成的 url 的协议名
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        String key = "test/my_test中文.json";
        String bucketName = "mybucket-1251668577";
        String versionId = "xxxyyyzzz111222333";

        System.out.println(cosclient.getObjectUrl(bucketName, key, versionId));
    }

    public static void getObjectUrlWithEndpoint() {
        // getObjectUrl 不需要验证身份信息
        COSCredentials cred = new AnonymousCOSCredentials();
        ClientConfig clientConfig = new ClientConfig();

        // 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        // 设置生成的 url 的协议名
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 设置自定义的域名
        UserSpecifiedEndpointBuilder endpointBuilder = new UserSpecifiedEndpointBuilder("test.endpoint.com", "service.cos.myqcloud.com");
        clientConfig.setEndpointBuilder(endpointBuilder);
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        String key = "test/my_test中文.json";
        String bucketName = "mybucket-1251668577";
        String versionId = "xxxyyyzzz111222333";

        System.out.println(cosclient.getObjectUrl(bucketName, key, versionId));
    }
}
