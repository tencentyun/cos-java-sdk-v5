package com.qcloud.cos.demo;

import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.COSVersionSummary;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ListVersionsRequest;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.model.VersionListing;
import com.qcloud.cos.region.Region;

/**
 * ListObjectsDemo展示了如何列出object
 */
public class ListObjectsDemo {
    private static String secretId = "AKIDXXXXXXXX";
    private static String secretKey = "1A2Z3YYYYYYYYYY";
    private static String bucketName = "examplebucket-12500000000";
    private static String region = "ap-guangzhou";
    private static COSClient cosClient = createClient();

    public static void main(String[] args) {
        try {
            listObjectsVersions();
            //listObjectsDemo();
            //listAllObjects();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
    }

    private static COSClient createClient() {
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId,secretKey);
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        return cosclient;
    }

    private static void listObjectsDemo() {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置bucket名称
        listObjectsRequest.setBucketName(bucketName);
        // prefix表示列出的object的key以prefix开始
        listObjectsRequest.setPrefix("");
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        // listObjectsRequest.setDelimiter("/");
        ObjectListing objectListing = null;
        try {
            objectListing = cosClient.listObjects(listObjectsRequest);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
        List<String> commonPrefixs = objectListing.getCommonPrefixes();

        // object summary表示所有列出的object列表
        List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
        for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
            // 文件的路径key
            String key = cosObjectSummary.getKey();
            // 文件的etag
            String etag = cosObjectSummary.getETag();
            // 文件的长度
            long fileSize = cosObjectSummary.getSize();
            // 文件的存储类型
            String storageClasses = cosObjectSummary.getStorageClass();

            System.out.println("key: " + key);
        }
    }

    // 如果要获取超过maxkey数量的object或者获取所有的object, 则需要循环调用listobject, 用上一次返回的next marker作为下一次调用的marker,
    // 直到返回的truncated为false
    private static void listAllObjects() {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置bucket名称
        listObjectsRequest.setBucketName(bucketName);
        // prefix表示列出的object的key以prefix开始
        listObjectsRequest.setPrefix("");
        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter("");
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        do {

            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return;
            } catch (CosClientException e) {
                e.printStackTrace();
                return;
            }
            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
            List<String> commonPrefixs = objectListing.getCommonPrefixes();

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                // 文件的路径key
                String key = cosObjectSummary.getKey();
                // 文件的etag
                String etag = cosObjectSummary.getETag();
                // 文件的长度
                long fileSize = cosObjectSummary.getSize();
                // 文件的存储类型
                String storageClasses = cosObjectSummary.getStorageClass();
            }

            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());
    }

    private static void listObjectsVersions() {
        ListVersionsRequest listVersionsRequest = new ListVersionsRequest();
        listVersionsRequest.setBucketName(bucketName);
        listVersionsRequest.setPrefix("");

        VersionListing versionListing = null;

        do {
            try {
                versionListing = cosClient.listVersions(listVersionsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return;
            } catch (CosClientException e) {
                e.printStackTrace();
                return;
            }

            List<COSVersionSummary> cosVersionSummaries = versionListing.getVersionSummaries();
            for (COSVersionSummary cosVersionSummary : cosVersionSummaries) {
                System.out.println(cosVersionSummary.getKey() + ":" + cosVersionSummary.getVersionId());
            }

            String keyMarker = versionListing.getNextKeyMarker();
            String versionIdMarker = versionListing.getNextVersionIdMarker();

            listVersionsRequest.setKeyMarker(keyMarker);
            listVersionsRequest.setVersionIdMarker(versionIdMarker);

        } while (versionListing.isTruncated());
    }
}
