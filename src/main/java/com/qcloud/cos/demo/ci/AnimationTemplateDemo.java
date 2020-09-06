package com.qcloud.cos.demo.ci;

import com.qcloud.cos.CIClientConfig;
import com.qcloud.cos.COSClient;

import com.qcloud.cos.model.ciModel.template.MediaListTemplateResponse;
import com.qcloud.cos.model.ciModel.template.MediaTemplateObject;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.model.ciModel.template.MediaTemplateResponse;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 动图模板接口相关demo 请求详情参见：https://cloud.tencent.com/document/product/460/46989
 */
public class AnimationTemplateDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = "secretId";
        String secretKey = "secretKey";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, CI 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 https), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("chongqing");
        CIClientConfig clientConfig = new CIClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient client = new COSClient(cred, clientConfig);

        // 4 调用要使用的方法。
        deleteMediaTemplate(client);
    }

    /**
     * CreateMediaTemplate 用于新增动图模板。
     *
     * @param client
     */
    public static void createMediaTemplate(COSClient client) throws UnsupportedEncodingException {
        MediaTemplateRequest request = new MediaTemplateRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.setTag("Animation");
        request.setName("TestTemplate22");
        request.getContainer().setFormat("gif");
        request.getVideo().setCodec("gif");
        request.getVideo().setWidth("1280");
        request.getVideo().setFps("15");
        request.getVideo().setAnimateOnlyKeepKeyFrame("true");
        request.getTimeInterval().setStart("0");
        request.getTimeInterval().setDuration("60");
        MediaTemplateResponse response = client.createMediaTemplate(request);
        System.out.println(response);
    }

    /**
     * DeleteMediaTemplate 用于删除动图模板。
     *
     * @param client
     */
    public static void deleteMediaTemplate(COSClient client) {
        MediaTemplateRequest request = new MediaTemplateRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.setTemplateId("t19c4a60ae1a694621a01f0c7130cfeaa2");
        Boolean response = client.deleteMediaTemplate(request);
        System.out.println(response);
    }

    /**
     * DescribeMediaTemplates 用于查询动图模板。
     *
     * @param client
     */
    public static void describeMediaTemplates(COSClient client) {
        MediaTemplateRequest request = new MediaTemplateRequest();
        request.setBucketName("markjrzhang-1251704708");
        MediaListTemplateResponse response = client.describeMediaTemplates(request);
        List<MediaTemplateObject> templateList = response.getTemplateList();
        for (MediaTemplateObject mediaTemplateObject : templateList) {
            System.out.println(mediaTemplateObject);
        }
    }

    /**
     * UpdateMediaTemplate 用于更新动图模板。
     *
     * @param client
     */
    public static void updateMediaTemplate(COSClient client) throws UnsupportedEncodingException {
        MediaTemplateRequest request = new MediaTemplateRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.setTemplateId("t19c4a60ae1a694621a01f0c7130cfeaa2");
        request.setTag("Animation");
        request.setName("updateName");
        request.getContainer().setFormat("gif");
        Boolean aBoolean = client.updateMediaTemplate(request);
        System.out.println(aBoolean);
    }
}
