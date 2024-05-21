package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.bucket.DocBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.DocBucketResponse;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.DocHtmlRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListResponse;
import com.qcloud.cos.model.ciModel.job.DocJobObject;
import com.qcloud.cos.model.ciModel.job.DocJobRequest;
import com.qcloud.cos.model.ciModel.job.DocJobResponse;
import com.qcloud.cos.model.ciModel.job.DocProcessObject;
import com.qcloud.cos.model.ciModel.job.PostSpeechRecognitionRequest;
import com.qcloud.cos.model.ciModel.job.PostSpeechRecognitionResponse;
import com.qcloud.cos.model.ciModel.queue.DocListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.DocQueueRequest;
import com.qcloud.cos.model.ciModel.queue.MediaQueueObject;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SpeechRecognitionTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void testCreateDocProcessJobs()  {
        try {
            PostSpeechRecognitionRequest request = new PostSpeechRecognitionRequest();
            request.setBucketName(bucket);
            // 设置创建任务的 Tag：SpeechRecognition 必传
            request.setTag("SpeechRecognition");
            PostSpeechRecognitionRequest.Input input = request.getInput();
            input.setObject("1.mp4");
            // 设置引擎模型类型，分为电话场景和非电话场景 必传
            PostSpeechRecognitionRequest.Operation operation = request.getOperation();
            PostSpeechRecognitionRequest.SpeechRecognition speechRecognition = operation.getSpeechRecognition();
            speechRecognition.setEngineModelType("8k_zh");
            // 设置存储桶的地域 必传
            operation.getOutput().setRegion("ap-chongqing");
            // 设置存储结果的存储桶 必传
            operation.getOutput().setBucket(bucket);
            // 设置结果文件的名称 必传
            operation.getOutput().setObject("1.mp4");
            speechRecognition.setChannelNum("1");
            speechRecognition.setFilterDirty("1");
            speechRecognition.setFilterModal("1");
            PostSpeechRecognitionResponse response = cosclient.postSpeechRecognition(request);
            System.out.println(Jackson.toJsonString(response));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
