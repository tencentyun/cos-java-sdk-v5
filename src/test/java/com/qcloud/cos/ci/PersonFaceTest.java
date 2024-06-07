package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ciModel.ai.AddPersonFaceRequest;
import com.qcloud.cos.model.ciModel.ai.AddPersonFaceResponse;
import com.qcloud.cos.model.ciModel.ai.CreatePersonRequest;
import com.qcloud.cos.model.ciModel.ai.CreatePersonResponse;
import com.qcloud.cos.model.ciModel.ai.DeletePersonFaceRequest;
import com.qcloud.cos.model.ciModel.ai.FaceIds;
import com.qcloud.cos.model.ciModel.ai.FaceSearchBucketRequest;
import com.qcloud.cos.model.ciModel.ai.PersonExDescriptionInfo;
import com.qcloud.cos.model.ciModel.ai.SearchPersonFaceRequest;
import com.qcloud.cos.model.ciModel.ai.SearchPersonFaceResponse;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class PersonFaceTest extends AbstractCOSClientCITest {
    private String jobId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-shanghai");
        ClientConfig clientConfig = new ClientConfig(region);
        cosclient = new COSClient(cred, clientConfig);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void faceSearchBucket() {
        try {
            FaceSearchBucketRequest request = new FaceSearchBucketRequest();
            request.setBucketName(bucket);
            request.setGroupName("mark");
            boolean b = cosclient.faceSearchBucket(request);
            assertTrue(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void createPerson() {
        try {
            CreatePersonRequest request = new CreatePersonRequest();
            request.setBucketName(bucket);
            request.setObjectKey("face/lyf.jpeg");
            request.setPersonId("lyf2");
            request.setPersonName("PersonName");
            ArrayList<PersonExDescriptionInfo> personExDescriptionInfos = new ArrayList<>();
            PersonExDescriptionInfo personInfo = new PersonExDescriptionInfo();
            personInfo.setPersonExDescriptionIndex(0);
            personInfo.setPersonExDescription("tencent");
            personExDescriptionInfos.add(personInfo);

            personInfo = new PersonExDescriptionInfo();
            personInfo.setPersonExDescriptionIndex(1);
            personInfo.setPersonExDescription("tencent1");
            personExDescriptionInfos.add(personInfo);

            request.setPersonExDescriptionInfos(personExDescriptionInfos);
            CreatePersonResponse person = cosclient.createPerson(request);
            System.out.println(Jackson.toJsonString(person));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void addPersonFace() {
        try {
            AddPersonFaceRequest request = new AddPersonFaceRequest();
            request.setBucketName(bucket);
            request.setObjectKey("face/lyf2.jpeg");
            request.setPersonId("lyf");
            request.setPersonName("PersonName");
            AddPersonFaceResponse addPersonFaceResponse = cosclient.addPersonFace(request);
            System.out.println(Jackson.toJsonString(addPersonFaceResponse));
        } catch (Exception e) {

        }

    }

    @Test
    public void searchPersonFace() {
        try {
            SearchPersonFaceRequest request = new SearchPersonFaceRequest();
            request.setBucketName(bucket);
            request.setObjectKey("face/lyf.jpeg");
            request.setFaceMatchThreshold(0);
            request.setNeedPersonInfo(0);
            request.setQualityControl(1);
            request.setNeedRotateDetection(0);
            SearchPersonFaceResponse response = cosclient.searchPersonFace(request);
            System.out.println(Jackson.toJsonString(response));
        }catch (Exception e){

        }
    }

    @Test
    public void deletePersonFace() {
        try {
            DeletePersonFaceRequest request = new DeletePersonFaceRequest();
            request.setBucketName(bucket);
            request.setObjectKey("face/lyf2.jpeg");
            request.setPersonId("lyf");
            List<String> faceId = new ArrayList<>();
            faceId.add("54698102722668");
            faceId.add("54698102722662");

            FaceIds faceIds = new FaceIds();
            faceIds.setFaceId(faceId);
            request.setFaceIds(faceIds);
            cosclient.deletePersonFace(request);
        }catch (Exception e){

        }
    }
}
