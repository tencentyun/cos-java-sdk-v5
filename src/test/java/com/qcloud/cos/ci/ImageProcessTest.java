package com.qcloud.cos.ci;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ciModel.common.CImageProcessRequest;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.image.DetectFaceRequest;
import com.qcloud.cos.model.ciModel.image.DetectFaceResponse;
import com.qcloud.cos.model.ciModel.image.GenerateQrcodeRequest;
import com.qcloud.cos.model.ciModel.image.ImageInspectRequest;
import com.qcloud.cos.model.ciModel.image.ImageInspectResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelRequest;
import com.qcloud.cos.model.ciModel.image.ImageLabelResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Request;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Response;
import com.qcloud.cos.model.ciModel.image.ImageSearchRequest;
import com.qcloud.cos.model.ciModel.image.ImageSearchResponse;
import com.qcloud.cos.model.ciModel.image.ImageStyleRequest;
import com.qcloud.cos.model.ciModel.image.ImageStyleResponse;
import com.qcloud.cos.model.ciModel.image.OpenImageSearchRequest;
import com.qcloud.cos.model.ciModel.image.StyleRule;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.persistence.CIObject;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.AIRecRequest;
import com.qcloud.cos.model.ciModel.persistence.DetectCarResponse;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
import com.qcloud.cos.model.ciModel.queue.MediaListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaQueueObject;
import com.qcloud.cos.model.ciModel.queue.MediaQueueRequest;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ImageProcessTest extends AbstractCOSClientCITest {
    private String queueId;
    private String styleName;
    private String jobId;
    private String entityId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void testGetImageLabel()  {
        try {
            ImageLabelRequest request = new ImageLabelRequest();
            request.setBucketName(bucket);
            request.setObjectKey("1.png");
            ImageLabelResponse response = cosclient.getImageLabel(request);
            String jsonStr = responseToJsonStr(response);
            System.out.println(jsonStr);
        } catch (Exception e) {

        }

    }

    @Test
    public void testGetImageLabelV2() {
        try {
            ImageLabelRequest request = new ImageLabelRequest();
            request.setBucketName(bucket);
            request.setObjectKey("1.png");
            ImageLabelResponse response = cosclient.getImageLabel(request);
        } catch (Exception e) {

        }

    }

    public static String responseToJsonStr(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @Test
    public void processImageTest() {
        try {
            String bucketName = bucket;
            String key = "1.jpg";
            ImageProcessRequest imageReq = new ImageProcessRequest(bucketName, key);

            PicOperations picOperations = new PicOperations();
            picOperations.setIsPicInfo(1);
            List<PicOperations.Rule> ruleList = new LinkedList<>();
            PicOperations.Rule rule1 = new PicOperations.Rule();
            rule1.setBucket(bucketName);
            rule1.setFileId("test-1.jpg");
            rule1.setRule("imageMogr2/rotate/90");
            ruleList.add(rule1);
            PicOperations.Rule rule2 = new PicOperations.Rule();
            rule2.setBucket(bucketName);
            rule2.setFileId("test-2.jpg");
            rule2.setRule("imageMogr2/rotate/180");
            ruleList.add(rule2);
            picOperations.setRules(ruleList);

            imageReq.setPicOperations(picOperations);

            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            System.out.println(ciUploadResult.getOriginalInfo().getEtag());
            for (CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println(ciObject.getLocation());
                System.out.println(ciObject.getEtag());
            }
        } catch (Exception e) {

        }

    }

    @Test
    public void processImageTest2() {
        try {
            ImageProcessRequest imageReq = null;

            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            System.out.println(ciUploadResult.getOriginalInfo().getEtag());
            for (CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println(ciObject.getLocation());
                System.out.println(ciObject.getEtag());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void processImageTest3() {
        try {
            String bucketName = bucket;
            String key = "1.jpg";
            ImageProcessRequest imageReq = new ImageProcessRequest(bucketName, key);

            PicOperations picOperations = new PicOperations();
            picOperations.setIsPicInfo(1);
            List<PicOperations.Rule> ruleList = new LinkedList<>();
            PicOperations.Rule rule1 = new PicOperations.Rule();
            rule1.setBucket(bucketName);
            rule1.setFileId("test-1.jpg");
            rule1.setRule("imageMogr2/rotate/90");
            ruleList.add(rule1);
            PicOperations.Rule rule2 = new PicOperations.Rule();
            rule2.setBucket(bucketName);
            rule2.setFileId("test-2.jpg");
            rule2.setRule("imageMogr2/rotate/180");
            ruleList.add(rule2);
            picOperations.setRules(ruleList);

            imageReq.setPicOperations(picOperations);

            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            System.out.println(ciUploadResult.getOriginalInfo().getEtag());
            for (CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println(ciObject.getLocation());
                System.out.println(ciObject.getEtag());
            }
        } catch (Exception e) {

        }

    }

    @Test
    public void processImageTest4() {
        try {
            String bucketName = bucket;
            ImageProcessRequest imageReq = new ImageProcessRequest(bucketName, null);

            PicOperations picOperations = new PicOperations();
            picOperations.setIsPicInfo(1);
            List<PicOperations.Rule> ruleList = new LinkedList<>();
            PicOperations.Rule rule1 = new PicOperations.Rule();
            rule1.setBucket(bucketName);
            rule1.setFileId("test-1.jpg");
            rule1.setRule("imageMogr2/rotate/90");
            ruleList.add(rule1);
            PicOperations.Rule rule2 = new PicOperations.Rule();
            rule2.setBucket(bucketName);
            rule2.setFileId("test-2.jpg");
            rule2.setRule("imageMogr2/rotate/180");
            ruleList.add(rule2);
            picOperations.setRules(ruleList);

            imageReq.setPicOperations(picOperations);

            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            System.out.println(ciUploadResult.getOriginalInfo().getEtag());
            for (CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println(ciObject.getLocation());
                System.out.println(ciObject.getEtag());
            }
        } catch (Exception e) {
        }

    }

    @Test
    public void getImageLabelV2Test() {
        try {
            ImageLabelV2Request request = new ImageLabelV2Request();
            request.setBucketName(bucket);
            request.setObjectKey("test/1.jpg");
            ImageLabelV2Response response = cosclient.getImageLabelV2(request);
        } catch (Exception e) {

        }

    }


    @Test
    public void getImageLabelV2Test2() {
        try {
            ImageLabelV2Request request = null;
            ImageLabelV2Response response = cosclient.getImageLabelV2(request);
        } catch (Exception e) {

        }
    }

    @Test
    public void getImageLabelV2Test3() {
        try {
            ImageLabelV2Request request = new ImageLabelV2Request();
            ImageLabelV2Response response = cosclient.getImageLabelV2(request);
        } catch (Exception e) {

        }

    }

    @Test
    public void detectCarTest() {
        try {
            AIRecRequest request = new AIRecRequest();
            request.setBucketName(bucket);
            request.setDetectUrl("https://" + bucket + ".cos.ap-chongqing.myqcloud.com/cars.jpeg");
            DetectCarResponse response = cosclient.detectCar(request);
            System.out.println(response.getRequestId());
        } catch (Exception e) {

        }
    }

    @Test
    public void detectCarTest2() {
        try {
            AIRecRequest request = new AIRecRequest();
            request.setDetectUrl("https://" + bucket + ".cos.ap-chongqing.myqcloud.com/car.jpg");
            DetectCarResponse response = cosclient.detectCar(request);
        } catch (Exception e) {

        }

    }

    @Test
    public void openImageSearchTest() {
        try { //1.创建任务请求对象
            OpenImageSearchRequest request = new OpenImageSearchRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName("shanghaitest-1251704708");
            request.setMaxCapacity("100");
            request.setMaxQps("10");
            //3.调用接口,获取任务响应对象
            boolean response = cosclient.openImageSearch(request);
        } catch (Exception e) {

        }
    }

    @Test
    public void openImageSearchTest2() {
        try { //1.创建任务请求对象
            OpenImageSearchRequest request = new OpenImageSearchRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setMaxCapacity("100");
            request.setMaxQps("10");
            //3.调用接口,获取任务响应对象
            boolean response = cosclient.openImageSearch(request);
        } catch (Exception e) {

        }

    }

    @Test
    public void GalleryImagesTest2() {
        try {
            ImageSearchRequest request = new ImageSearchRequest();
            request.setBucketName(bucket);
            request.setObjectKey("cars.jpeg");
            String s = UUID.randomUUID().toString();
            String s1 = s.replaceAll("-", "");
            entityId = s1.substring(10);
            request.setEntityId(entityId);

            boolean response = cosclient.addGalleryImages(request);
            cosclient.deleteGalleryImages(request);
            ImageSearchResponse imageSearchResponse = cosclient.searchGalleryImages(request);
        } catch (Exception e) {

        }
    }

    @Test
    public void addImageStyleTest() {
        try {
            //1.创建二维码生成请求对象
            ImageStyleRequest request = new ImageStyleRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName(bucket);
            String s = UUID.randomUUID().toString();
            String s1 = s.replaceAll("-", "");
            styleName = s1.substring(10);
            request.setStyleName(styleName);
            //设置样式规则，demo此处处理规则含义为：缩放图片宽高为原图50%
            request.setStyleBody("imageMogr2/thumbnail/!50p");
            Boolean response = cosclient.addImageStyle(request);
        } catch (Exception e) {
        }
    }

    @Test
    public void getImageStyleTest() {
        try {
            ImageStyleRequest request = new ImageStyleRequest();
            request.setBucketName(bucket);
            request.setStyleName(styleName);
            ImageStyleResponse response = cosclient.getImageStyle(request);
            List<StyleRule> styleRule = response.getStyleRule();
//        for (StyleRule rule : styleRule) {
//            request.setStyleName(rule.getStyleName());
//            Boolean aBoolean = cosclient.deleteImageStyle(request);
//        }
        } catch (Exception e) {

        }

    }

    @Test
    public void deleteImageStyleTest() {
        try {
            ImageStyleRequest request = new ImageStyleRequest();
            request.setBucketName(bucket);
            request.setStyleName(styleName);
            Boolean aBoolean = cosclient.deleteImageStyle(request);
            Assert.assertTrue(aBoolean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void generateQrcodeTest() {
        try {
            GenerateQrcodeRequest request = new GenerateQrcodeRequest();
            request.setBucketName(bucket);
            request.setQrcodeContent("数据万象");
            request.setWidth("400");
            request.setMode("0");
            String imageBase64 = cosclient.generateQrcode(request);
        } catch (Exception e) {

        }

    }

    @Before
    public void createPicProcessJobTest() {
        try {//1.创建任务请求对象
            MediaJobsRequest request = new MediaJobsRequest();
            request.setBucketName(bucket);
            request.setTag("PicProcess");
            request.getInput().setObject("1.png");
            request.getOperation().getOutput().setBucket(bucket);
            request.getOperation().getOutput().setRegion(region);
            request.getOperation().getOutput().setObject("2.png");
            request.getOperation().getPicProcess().setProcessRule("imageMogr2/rotate/90");
            request.getOperation().getPicProcess().setIsPicInfo("true");
            request.setQueueId("p86ede0188f844ac99d50f5fa63005237");
            request.setCallBack("https://cloud.tencent.com/xxx");
            MediaJobResponse response = cosclient.createPicProcessJob(request);
            jobId = response.getJobsDetail().getJobId();
        } catch (Exception e) {
        }

    }

    @Test
    public void describePicProcessQueuesTest() {
        MediaQueueRequest request = new MediaQueueRequest();
        request.setBucketName(bucket);
        //3.调用接口,获取队列响应对象
        MediaListQueueResponse response = cosclient.describePicProcessQueues(request);
        MediaQueueObject mediaQueueObject = response.getQueueList().get(0);
    }

    @Test
    public void processImage2Test() {
        try {
            String bucketName = bucket;
            String key = "2.png";
            CImageProcessRequest request = new CImageProcessRequest(bucketName, key);
            PicOperations picOperations = new PicOperations();
            picOperations.setIsPicInfo(1);
            List<PicOperations.Rule> ruleList = new LinkedList<>();
            PicOperations.Rule rule1 = new PicOperations.Rule();
            rule1.setBucket(bucketName);
            rule1.setFileId("test-1.jpg");
            rule1.setRule("imageMogr2/rotate/90");
            ruleList.add(rule1);
            PicOperations.Rule rule2 = new PicOperations.Rule();
            rule2.setBucket(bucketName);
            rule2.setFileId("test-2.jpg");
            rule2.setRule("imageMogr2/rotate/180");
            ruleList.add(rule2);
            picOperations.setRules(ruleList);
            picOperations.setIsPicInfo(1);
            request.setPicOperations(picOperations);
            boolean ciUploadResult = cosclient.processImage2(request);
        } catch (Exception e) {
        }
    }

    @Test
    public void processImage3Test() {
        try {
            String key = "2.png";
            GetObjectRequest getObj = new GetObjectRequest(bucket, key);
            String rule = "imageInfo";
            getObj.putCustomQueryParameter(rule, null);
            COSObject object = cosclient.getObject(getObj);
            System.out.println(object.getObjectMetadata().getRequestId());
            COSObjectInputStream objectContent = object.getObjectContent();
            String result = inputStreamToString(objectContent);
            System.out.println(result);
        } catch (Exception e) {
        }
    }

    @Test
    public void getImageInspectTest() {
        try {
            //1.创建任务请求对象
            ImageInspectRequest request = new ImageInspectRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName(bucket);
            //2.1图片位置
            request.setObjectKey("test/1.jpg");
            //3.调用接口,获取任务响应对象
            ImageInspectResponse response = cosclient.getImageInspect(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void detectFaceTest() {
        try {
            DetectFaceRequest request = new DetectFaceRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName(bucket);
            request.setObjectKey("1.jpg");
            DetectFaceResponse response = cosclient.detectFace(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        return stringBuilder.toString();
    }

}
