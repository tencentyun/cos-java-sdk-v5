package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;

import java.util.List;


public class MediaFaceClipSearchResponse extends CiServiceResult {

    /**
     *匹配到的媒资片段集合，按人物聚合，每个元素对应一个匹配到的人物。
     */
    private List<MediaClip> mediaClipList;

    public List<MediaClip> getMediaClipList() { return mediaClipList; }

    public void setMediaClipList(List<MediaClip> mediaClipList) { this.mediaClipList = mediaClipList; }


    public static class MediaClip {
        /**
         *匹配得分，取值范围 [0, 100]，数值越高表示相关性越强。
         */
        private Double score;

        /**
         *实体/人物名称。
         */
        private String labelName;

        /**
         *人物类型，可选值：celebrity（名人）、sensitive（敏感人物）、politician（政治人物）、custom（自定义人物）、unknown（未知）。
         */
        private String category;

        /**
         *人物片段聚类信息（仅视频媒资返回），描述该人物在媒资文件中出现的各个时间片段。图片媒资的 From/To/Timestamp 均为 0。
         */
        private List<OccurrencesInfo> occurrencesInfos;

        public Double getScore() { return score; }

        public void setScore(Double score) { this.score = score; }

        public String getLabelName() { return labelName; }

        public void setLabelName(String labelName) { this.labelName = labelName; }

        public String getCategory() { return category; }

        public void setCategory(String category) { this.category = category; }

        public List<OccurrencesInfo> getOccurrencesInfos() { return occurrencesInfos; }

        public void setOccurrencesInfos(List<OccurrencesInfo> occurrencesInfos) { this.occurrencesInfos = occurrencesInfos; }

    }

    public static class OccurrencesInfo {
        /**
         *片段起始时间，单位为秒。
         */
        private Double from;

        /**
         *片段结束时间，单位为秒。
         */
        private Double to;

        /**
         *人脸单帧详细信息列表，逐帧给出该人脸在片段内的时间戳与坐标框。
         */
        private List<TrackData> trackData;

        public Double getFrom() { return from; }

        public void setFrom(Double from) { this.from = from; }

        public Double getTo() { return to; }

        public void setTo(Double to) { this.to = to; }

        public List<TrackData> getTrackData() { return trackData; }

        public void setTrackData(List<TrackData> trackData) { this.trackData = trackData; }

    }

    public static class TrackData {
        /**
         *人脸出现的时间戳，单位为秒（图片媒资无此字段）。
         */
        private Double timestamp;

        /**
         *人脸坐标框，标识该帧中人脸在画面中的位置。
         */
        private BoxPosition boxPosition;

        public Double getTimestamp() { return timestamp; }

        public void setTimestamp(Double timestamp) { this.timestamp = timestamp; }

        public BoxPosition getBoxPosition() { return boxPosition; }

        public void setBoxPosition(BoxPosition boxPosition) { this.boxPosition = boxPosition; }

    }

    public static class BoxPosition {
        /**
         *人脸框左上角横坐标，单位为像素。
         */
        private Integer left;

        /**
         *人脸框左上角纵坐标，单位为像素。
         */
        private Integer top;

        /**
         *人脸框宽度，单位为像素。
         */
        private Integer width;

        /**
         *人脸框高度，单位为像素。
         */
        private Integer height;

        public Integer getLeft() { return left; }

        public void setLeft(Integer left) { this.left = left; }

        public Integer getTop() { return top; }

        public void setTop(Integer top) { this.top = top; }

        public Integer getWidth() { return width; }

        public void setWidth(Integer width) { this.width = width; }

        public Integer getHeight() { return height; }

        public void setHeight(Integer height) { this.height = height; }

    }

}
