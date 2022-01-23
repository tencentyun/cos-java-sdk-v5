package com.qcloud.cos.demo;

import java.io.File;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;

public class TemporyTokenDemo {
	// 该例子介绍使用临时秘钥来访问COS上的资源
	// 临时秘钥通过云API向腾讯云权限管理系统CAM申请，java云api可以在此获取：https://github.com/QcloudApi/qcloudapi-sdk-java

	public static BasicSessionCredentials getSessionCredential() {
		// 实际应用中，这里通过云api请求得到临时秘钥后，构造BasicSessionCredential
		BasicSessionCredentials cred =
				new BasicSessionCredentials("111111111111122222",
						"333333334afafafaa", "efd2f92e6b35562d387971ec7e78cfa051d058ad3");
		return cred;
	}

	// 使用临时秘钥进行上传和下载
	public static void UseTemporyTokenUploadAndDownload() {
		// 使用云api秘钥，可以获取一个临时secret id，secret key和session token,
		BasicSessionCredentials cred = getSessionCredential();
        ClientConfig clientConfig = new ClientConfig();

        // 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        // 生成cos客户端对象
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 上传的bucket名字
        String bucketName = "rabbitliutj-1000000";
        // 上传object, 建议20M以下的文件使用该接口
        File localFile = new File("src/test/resources/len5M.txt");
        String key = "upload_single_demo5M.txt";

        // 上传
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println(putObjectResult.getMetadata());

        // 下载
        File downFile = new File("src/test/resources/len5M_down.txt");
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();
	}
	
	public static void main(String[] args) throws Exception {
	    UseTemporyTokenUploadAndDownload();
	}

}
