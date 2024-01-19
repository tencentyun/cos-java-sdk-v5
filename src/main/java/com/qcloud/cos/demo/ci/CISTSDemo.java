package com.qcloud.cos.demo.ci;

import com.qcloud.cos.utils.Jackson;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Policy;
import com.tencent.cloud.Response;
import com.tencent.cloud.Statement;

import java.util.TreeMap;

public class CISTSDemo {
    public static void getCredential() {
        TreeMap<String, Object> config = new TreeMap<String, Object>();

        try {
            // 云 api 密钥 SecretId
            config.put("secretId", "aaaaa");
            // 云 api 密钥 SecretKey
            config.put("secretKey", "bbbbb");

            // 初始化 policy
            Policy policy = new Policy();
            policy.setVersion("2.0");

            // 开始构建一条 statement
            Statement statement = new Statement();
            // 声明设置的结果是允许操作
            statement.setEffect("allow");
            // 添加一批操作权限
            statement.addActions(new String[]{
                    "ci:*"
            });
            // 添加一批资源路径
            statement.addResources(new String[]{
                    "qcs::ci:ap-beijing:uid/1234567890:bucket/demo-1234567890/*",
                    "qcs::ci:ap-beijing:uid/1234567890:bucket/demo-1234567890/workflow/*",
            });

            // 把一条 statement 添加到 policy
            // 可以添加多条
            policy.addStatement(statement);

            // 临时密钥有效时长，单位是秒
            config.put("durationSeconds", 1800);
            // 换成 bucket 所在地区
            config.put("region", "ap-chengdu");

            // 将 Policy 示例转化成 String，可以使用任何 json 转化方式，这里是本 SDK 自带的推荐方式
            config.put("policy", Jackson.toJsonPrettyString(policy));

            Response response = CosStsClient.getCredential(config);
            System.out.println(Jackson.toJsonPrettyString(response));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }
}
