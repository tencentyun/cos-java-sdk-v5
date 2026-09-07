package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.GetObjectRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 人体识别demo
 * 人体识别功能为同步请求方式，您可以通过本接口检测图片中的人体，识别出人体的位置、置信度等信息。
 * 该接口属于 GET 请求。
 *
 * 请求语法：
 * GET /ObjectKey?ci-process=AIBodyRecognition&detect-url=<detect-url> HTTP/1.1
 *
 * 响应体为 application/xml，包含 RecognitionResult 节点：
 * - Status: 人体识别结果。0表示未识别到，1表示识别到
 * - PedestrianInfo: 人体识别结果，可能有多个
 *   - Name: 识别类型，人体识别默认为 person
 *   - Score: 人体的置信度，取值范围为[0-100]
 *   - Location: 图中识别到人体的坐标
 *     - Point: 人体坐标点（X坐标,Y坐标）
 */
public class AIBodyRecognitionDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        aiBodyRecognitionWithObjectKey(client);
    }

    /**
     * 通过COS中的图片ObjectKey进行人体识别
     * 下载时处理 demo
     */
    public static void aiBodyRecognitionWithObjectKey(COSClient client) {
        //图片所在bucket名称
        String bucketName = "examplebucket-1250000000";
        //图片在bucket中的相对位置，比如根目录下file文件夹中的demo.png路径为file/demo.png
        String key = "2.png";
        GetObjectRequest getObj = new GetObjectRequest(bucketName, key);
        //设置ci-process为AIBodyRecognition，表示人体识别
        getObj.putCustomQueryParameter("ci-process", "AIBodyRecognition");

        //使用try-with-resources确保COSObject和底层HTTP连接被正确关闭，避免内存/连接泄漏
        try (COSObject object = client.getObject(getObj);
             COSObjectInputStream objectContent = object.getObjectContent()) {
            //读取并打印XML响应结果
            printResponse(objectContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取响应流并打印XML结果
     * 响应示例：
     * <RecognitionResult>
     *     <Status>1</Status>
     *     <PedestrianInfo>
     *         <Name>person</Name>
     *         <Score>90</Score>
     *         <Location>
     *             <Point>162,266</Point>
     *             <Point>162,716</Point>
     *             <Point>392,716</Point>
     *             <Point>392,266</Point>
     *         </Location>
     *     </PedestrianInfo>
     * </RecognitionResult>
     */
    private static void printResponse(COSObjectInputStream objectContent) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(objectContent, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        System.out.println("人体识别结果：");
        System.out.println(sb);
    }
}
