package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
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
import com.qcloud.cos.utils.Jackson;

import java.util.ArrayList;
import java.util.List;

public class PersonFaceDemo {

    public static void main(String[] args) {
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        deletePersonFace(client);
    }

    /**
     * faceSearchBucket 开通人脸检索功能
     * 该接口属于 POST 请求。
     */
    public static void faceSearchBucket(COSClient client) {
        FaceSearchBucketRequest request = new FaceSearchBucketRequest();
        request.setBucketName("demo-1234567890");
        request.setGroupName("mark");
        //成功时返回true  失败时抛出异常
        boolean b = client.faceSearchBucket(request);
    }

    /**
     * createPerson 该接口用于在人脸库中创建新的人员，添加人脸、姓名、性别及其他相关信息。
     * 该接口属于 POST 请求。
     */
    public static void createPerson(COSClient client) {
        CreatePersonRequest request = new CreatePersonRequest();
        request.setBucketName("demo-1234567890");
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
        CreatePersonResponse person = client.createPerson(request);
        System.out.println(Jackson.toJsonString(person));
    }

    /**
     * addPersonFace 该接口用于将新的人脸图片添加到一个人员下。
     * 一个人员最多允许包含 5 张图片。
     * 该接口属于 POST 请求。
     */
    public static void addPersonFace(COSClient client) {
        AddPersonFaceRequest request = new AddPersonFaceRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("face/lyf2.jpeg");
        request.setPersonId("lyf");
        request.setPersonName("PersonName");
        AddPersonFaceResponse addPersonFaceResponse = client.addPersonFace(request);
        System.out.println(Jackson.toJsonString(addPersonFaceResponse));
    }

    /**
     * searchPersonFace
     * 该接口用于对一张待识别的人脸图片，在一人员库中识别出最相似的人员，识别结果按照相似度从大到小排序
     * 该接口属于 GET 请求。
     */
    public static void searchPersonFace(COSClient client) {
        SearchPersonFaceRequest request = new SearchPersonFaceRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("face/lyf.jpeg");
        request.setFaceMatchThreshold(0);// 设置出参 Score 中，只有超过 FaceMatchThreshold值的结果才会返回。默认为50
        request.setNeedPersonInfo(0);// 设置是否返回人员具体信息。0 为关闭，1 为开启。默认为 0。其他非0非1值默认为0
        request.setQualityControl(1);
        request.setNeedRotateDetection(0);
        SearchPersonFaceResponse response = client.searchPersonFace(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * deletePersonFace
     * 该接口用于删除一个人员下的人脸图片。如果该人员只有一张人脸图片，则返回错误。
     * 该接口属于 POST 请求。
     */
    public static void deletePersonFace(COSClient client) {
        DeletePersonFaceRequest request = new DeletePersonFaceRequest();
        request.setBucketName("demo-1234567890");
        // 设置对象文件名，例如：folder/document.jpg。;是否必传：是
        request.setObjectKey("face/lyf2.jpeg");
        // 设置人员ID，取值为创建人员接口中的PersonId;是否必传：是
        request.setPersonId("lyf");
        // 设置待删除的人脸ID;是否必传：是
        List<String> faceId = new ArrayList<>();
        faceId.add("54698102722668*****");
        faceId.add("54698102722668*****");

        FaceIds faceIds = new FaceIds();
        faceIds.setFaceId(faceId);
        request.setFaceIds(faceIds);
        client.deletePersonFace(request);
    }


}
