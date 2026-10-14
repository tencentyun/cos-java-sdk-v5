package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;

import java.util.List;


public class MediaFaceSearchResponse extends CiServiceResult {

    /**
     *人脸匹配信息列表，按人脸聚合，最多返回20个人脸 ID（FaceId）。
     */
    private List<MediaInfo> mediaInfoList;

    public List<MediaInfo> getMediaInfoList() { return mediaInfoList; }

    public void setMediaInfoList(List<MediaInfo> mediaInfoList) { this.mediaInfoList = mediaInfoList; }


    public static class MediaInfo {
        /**
         *人脸 ID，系统为每张匹配到的人脸分配的唯一标识。
         */
        private String faceId;

        /**
         *包含该人脸的媒资文件 URI 列表，表示该人脸出现在哪些媒资文件中。每个 FaceId 对应的 UriList 最多包含500个 URI。
         */
        private List<String> uriList;

        public String getFaceId() { return faceId; }

        public void setFaceId(String faceId) { this.faceId = faceId; }

        public List<String> getUriList() { return uriList; }

        public void setUriList(List<String> uriList) { this.uriList = uriList; }

    }

}
