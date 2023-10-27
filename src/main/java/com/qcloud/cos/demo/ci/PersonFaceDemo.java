package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.ai.AddPersonFaceRequest;
import com.qcloud.cos.model.ciModel.ai.CreatePersonRequest;
import com.qcloud.cos.model.ciModel.ai.CreatePersonResponse;
import com.qcloud.cos.model.ciModel.ai.DeletePersonFaceRequest;
import com.qcloud.cos.model.ciModel.ai.FaceSearchBucketRequest;
import com.qcloud.cos.model.ciModel.ai.PersonExDescriptionInfo;
import com.qcloud.cos.model.ciModel.ai.SearchPersonFaceRequest;
import com.qcloud.cos.model.ciModel.ai.SearchPersonFaceResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.ArrayList;

/**
 * 详情见
 */
public class PersonFaceDemo {

    public static void main(String[] args) {
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createPerson(client);
    }
    
    /**
     * faceSearchBucket 开通人脸检索功能
     * 该接口属于 POST 请求。
     */
    public static void faceSearchBucket(COSClient client) {
        FaceSearchBucketRequest request = new FaceSearchBucketRequest();
        request.setBucketName("xjp-1251704708");
        request.setGroupName("mark");
        client.faceSearchBucket(request);
    }

    /**
     * createPerson
     * 该接口属于 POST 请求。
     */
    public static void createPerson(COSClient client) {
        CreatePersonRequest request = new CreatePersonRequest();
        request.setBucketName("xjp-1251704708");
        request.setObjectKey("face/huge.jpg");
        request.setPersonId("huge3");
        request.setPersonName("胡歌");
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
        CreatePersonResponse person = client.createPerson(request);
        System.out.println(Jackson.toJsonString(person));
    }

    /**
     * addPersonFace
     * 该接口属于 POST 请求。
     */
    public static void addPersonFace(COSClient client) {
        AddPersonFaceRequest request = new AddPersonFaceRequest();
        request.setBucketName("xjp-1251704708");
        request.setPersonId("mark");
        request.setPersonName("MarkName");
        client.addPersonFace(request);
    }

    /**
     * searchPersonFace
     * 该接口属于 GET 请求。
     */
    public static void searchPersonFace(COSClient client) {
        SearchPersonFaceRequest request = new SearchPersonFaceRequest();
        request.setBucketName("xjp-1251704708");
        request.setObjectKey("ObjectKey");
        request.setObjectKey("");// 设置对象文件名，例如：folder/document.jpg。
        request.setFaceMatchThreshold(0);// 设置出参 Score 中，只有超过 FaceMatchThreshold值的结果才会返回。默认为50
        request.setNeedPersonInfo(0);// 设置是否返回人员具体信息。0 为关闭，1 为开启。默认为 0。其他非0非1值默认为0
        request.setQualityControl(1);
        request.setNeedRotateDetection(0);
        SearchPersonFaceResponse response = client.searchPersonFace(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * deletePersonFace
     * 该接口属于 POST 请求。
     */
    public static void deletePersonFace(COSClient client) {
        DeletePersonFaceRequest request = new DeletePersonFaceRequest();
        request.setBucketName("xjp-1251704708");
        // 设置对象文件名，例如：folder/document.jpg。;是否必传：是
        request.setObjectKey("");
        // 设置人员ID，取值为创建人员接口中的PersonId;是否必传：是
        request.setPersonId("");
        // 设置待删除的人脸ID;是否必传：是
        request.getFaceIds().setFaceId("");
        client.deletePersonFace(request);
    }


}
