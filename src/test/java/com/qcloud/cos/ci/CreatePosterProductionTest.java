package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.job.v2.CreatePosterProductionRequest;
import com.qcloud.cos.model.ciModel.job.v2.CreatePosterProductionResponse;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreatePosterProductionTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void testCreatePosterProduction() {
        CreatePosterProductionRequest request = new CreatePosterProductionRequest();
        request.setBucketName(bucket);
        // 设置创建任务的 Tag：PosterProduction;
        request.setTag("PosterProduction");
        request.getInput().setObject("/SDK/Images/test-2.jpg");
        
        // 设置模板 ID;
        request.getOperation().getPosterProduction().setTemplateId("6324349b569067d4d11a2c2c");
        request.getOperation().getPosterProduction().getInfo().setName("test-poster");
        request.getOperation().getPosterProduction().getInfo().setHeight("1080");
        // 设置存储桶的地域;是否必传：是
        request.getOperation().getOutput().setRegion(region);
        // 设置存储结果的存储桶;是否必传：是
        request.getOperation().getOutput().setBucket(bucket);
        // 设置输出结果的文件名;是否必传：是
        request.getOperation().getOutput().setObject("/SDK/Images/output/PosterProductionOut.jpg");
        // 设置透传用户信息, 可打印的 ASCII 码, 长度不超过1024;是否必传：否
        request.getOperation().setUserData("This is my data.");
        // 设置任务优先级，级别限制：0 、1 、2 。级别越大任务优先级越高，默认为0;是否必传：否
        request.getOperation().setJobLevel("0");
        // 设置任务回调格式，JSON 或 XML，默认 XML，优先级高于队列的回调格式;是否必传：否
        request.setCallBackFormat("JSON");
        // 设置任务回调地址，优先级高于队列的回调地址。设置为 no 时，表示队列的回调地址不产生回调;是否必传：否
//        request.setCallBack("http://callback.demo.com");

        CreatePosterProductionResponse response = cosclient.createPosterProduction(request);
        
        assert response != null;
        assert response.getJobsDetail() != null;
        assert response.getJobsDetail().getJobId() != null;
        assert "PosterProduction".equals(response.getJobsDetail().getTag());

        System.out.println(Jackson.toJsonString(response));
    }
}