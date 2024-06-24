package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;


public class DatasetFaceSearchResponse extends CiServiceResult {

    /**
     *人脸检索识别结果信息列表。
     */
    @JsonProperty(value = "FaceResult")
    private List<FaceResult> faceResult;

    /**
     *请求 ID。
     */
    private String requestId;

    public List<FaceResult> getFaceResult() { return faceResult; }

    public void setFaceResult(List<FaceResult> faceResult) { this.faceResult = faceResult; }

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    
    public static class FaceResult {
        /**
         *相关人脸信息列表。
         */
        @JsonProperty("FaceInfos")
        private List<FaceInfos> faceInfos;

        /**
         *输入图片的人脸框位置。
         */
        @JsonProperty("InputFaceBoundary")
        private FaceBoundary inputFaceBoundary;

        public List<FaceInfos> getFaceInfos() { return faceInfos; }

        public void setFaceInfos(List<FaceInfos> faceInfos) { this.faceInfos = faceInfos; }

        public FaceBoundary getInputFaceBoundary() { return inputFaceBoundary; }

        public void setInputFaceBoundary(FaceBoundary inputFaceBoundary) { this.inputFaceBoundary = inputFaceBoundary; }

    }

    public static class FaceBoundary {
        /**
         *人脸高度。
         */
        private Integer height;

        /**
         *人脸宽度。
         */
        private Integer width;

        /**
         *人脸框左上角横坐标。
         */
        private Integer left;

        /**
         *人脸框左上角纵坐标。
         */
        private Integer top;

        public Integer getHeight() { return height; }

        public void setHeight(Integer height) { this.height = height; }

        public Integer getWidth() { return width; }

        public void setWidth(Integer width) { this.width = width; }

        public Integer getLeft() { return left; }

        public void setLeft(Integer left) { this.left = left; }

        public Integer getTop() { return top; }

        public void setTop(Integer top) { this.top = top; }

    }

    public static class FaceInfos {
        /**
         *自定义人物ID。
         */
        private String personId;

        /**
         *相关人脸框位置。
         */
        private FaceBoundary faceBoundary;

        /**
         *人脸ID。
         */
        private String faceId;

        /**
         *相关人脸匹配得分。
         */
        private Integer score;

        /**
         *资源标识字段，表示需要建立索引的文件地址。
         */
        @JsonProperty("URI")
        private String uRI;

        public String getPersonId() { return personId; }

        public void setPersonId(String personId) { this.personId = personId; }

        public FaceBoundary getFaceBoundary() { return faceBoundary; }

        public void setFaceBoundary(FaceBoundary faceBoundary) { this.faceBoundary = faceBoundary; }

        public String getFaceId() { return faceId; }

        public void setFaceId(String faceId) { this.faceId = faceId; }

        public Integer getScore() { return score; }

        public void setScore(Integer score) { this.score = score; }

        public String getURI() { return uRI; }

        public void setURI(String uRI) { this.uRI = uRI; }

    }

}
