package com.qcloud.cos.demo.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.AccessControlList;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.region.Region;

public class SetGetBucketAclDemo {
    public static void SetGetBucketAclDemo() {
        // 1 初始化用户身份信息(secretId, secretKey)
	COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "mybucket-1251668577";
        // public-read-write bucket acl
        // cosclient.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
        // public-read bucket acl
        // cosclient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        // private bucket acl
        cosclient.setBucketAcl(bucketName, CannedAccessControlList.Private);
        AccessControlList accessControlList = cosclient.getBucketAcl(bucketName);
        System.out.println("bucket acl:" + accessControlList.getCannedAccessControl());
    }

    public static void main(String[] args) {
        SetGetBucketAclDemo();
    }
}
