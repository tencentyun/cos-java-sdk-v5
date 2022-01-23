package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.AccessControlList;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.Grant;
import com.qcloud.cos.model.GroupGrantee;
import com.qcloud.cos.model.Owner;
import com.qcloud.cos.model.Permission;
import com.qcloud.cos.model.UinGrantee;

public class SetGetBucketAclDemo {
    public static void setGetBucketAclDemo() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");

        ClientConfig clientConfig = new ClientConfig();

        // 2 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "mybucket-1251668577";
        AccessControlList aclGet = cosclient.getBucketAcl(bucketName);
        System.out.println("bucket acl:" + aclGet.getOwner());
        System.out.println("bucket acl:" + aclGet.getCannedAccessControl());
        for (Grant grant : aclGet.getGrantsAsList()) {
            System.out.println(grant.getGrantee().getIdentifier());
            System.out.println(grant.getGrantee().getTypeIdentifier());
            System.out.println(grant.getPermission());
        }
        // public-read-write bucket acl
        // cosclient.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
        // public-read bucket acl
        // cosclient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        // private bucket acl
        AccessControlList aclSet = new AccessControlList();
        Owner owner = new Owner();
        owner.setId("qcs::cam::uin/2000000000:uin/2000000000");
        aclSet.setOwner(owner);
        UinGrantee uinGrantee = new UinGrantee("qcs::cam::uin/100000000000:uin/100000000000");
        aclSet.grantPermission(uinGrantee, Permission.Read);
        // 设置公有读
        aclSet.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        cosclient.setBucketAcl(bucketName, aclSet);
        cosclient.setBucketAcl(bucketName, CannedAccessControlList.Private);
        System.out.println("===========================");
        aclGet = cosclient.getBucketAcl(bucketName);
        System.out.println("bucket acl:" + aclGet.getOwner());
        System.out.println("bucket acl:" + aclGet.getCannedAccessControl());
        for (Grant grant : aclGet.getGrantsAsList()) {
            System.out.println(grant.getGrantee().getIdentifier());
            System.out.println(grant.getGrantee().getTypeIdentifier());
            System.out.println(grant.getPermission());
        }
    }

    public static void main(String[] args) {
        setGetBucketAclDemo();
    }
}
